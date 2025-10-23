package vn.jasmine.security.controller;

import vn.jasmine.dto.response.MessageResponse;
import vn.jasmine.security.CustomUserDetails;
import vn.jasmine.security.JWTProvider;
import vn.jasmine.security.dto.request.LoginRequestDto;
import vn.jasmine.security.dto.request.ResetPasswordRequestDto;
import vn.jasmine.security.dto.request.SignUpRequestDto;
import vn.jasmine.security.dto.response.SignUpResponseDto;
import vn.jasmine.security.dto.response.UserResponseDto;
import vn.jasmine.security.entity.RefreshTokenEntity;
import vn.jasmine.security.exception.instance.DuplicatedUserInfoException;
import vn.jasmine.security.exception.instance.TokenRefreshException;
import vn.jasmine.security.service.IJWTService;
import vn.jasmine.security.service.IRefreshTokenService;
import vn.jasmine.security.service.IUserService;
import vn.jasmine.service.mail.IEmailSenderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final IEmailSenderService emailSenderService;
    private final IJWTService jwtService;
    private final IRefreshTokenService refreshTokenService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequest) {
        SignUpResponseDto signUpResponseDto = userService.signUpUser(signUpRequest);

        return ResponseEntity.ok(signUpResponseDto);
    }

    @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequestDto) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String jwt = jwtProvider.generateToken(loginRequestDto.getUsername());

        ResponseCookie jwtCookie = jwtProvider.generateJwtCookie(jwt);

        RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(jwt, loginRequestDto.getUsername());

        ResponseCookie jwtRefreshCookie = jwtProvider.generateRefreshJwtCookie(refreshToken.getRefreshToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(UserResponseDto.builder().username(userDetails.getUsername()).email(userDetails.getEmail()).scope(userDetails.getAuthorities().toString()).build());
    }


    @PostMapping("/signup-email/{email}")
    public ResponseEntity<String> checkEmailSignup(@PathVariable String email) {
        if (userService.existUserWithEmail(email)) {
            throw new DuplicatedUserInfoException(String.format("%s has been used.", email));
        }
        String signUpOTP = emailSenderService.sendSignUpEmail(email);

        return ResponseEntity.ok(signUpOTP);
    }

    @PostMapping("/sign-out")
    public ResponseEntity<?> logoutUser(HttpServletRequest request) {

        String jwt = jwtProvider.getJwtFromCookies(request);
        jwtService.saveBlackListToken(jwt);
        refreshTokenService.deleteRefreshToken(jwt);

        ResponseCookie jwtCookie = jwtProvider.getCleanJwtCookie();
        ResponseCookie jwtRefreshCookie = jwtProvider.getCleanJwtRefreshCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String refreshToken = jwtProvider.getJwtRefreshFromCookies(request);

        if ((refreshToken != null) && (!refreshToken.isEmpty())) {
            return refreshTokenService.findByRefreshToken(UUID.fromString(refreshToken))
                    .map(refreshTokenService::verifyExpiration)
                    .map(RefreshTokenEntity::getUsername)
                    .map(username -> {
                        String jwt = jwtProvider.generateToken(username);
                        ResponseCookie jwtCookie = jwtProvider.generateJwtCookie(jwt);

                        return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                                .body(new MessageResponse("Token is refreshed successfully!"));
                    })
                    .orElseThrow(() -> new TokenRefreshException(refreshToken,
                            "Refresh token is not in database!"));
        }

        return ResponseEntity.badRequest().body(new MessageResponse("Refresh Token is empty!"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String response = emailSenderService.sendForgotPassEmail(email);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody ResetPasswordRequestDto resetPasswordRequest) {
        return userService.resetPassword(resetPasswordRequest);
    }

}
