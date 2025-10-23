package vn.jasmine.security.service;

public interface IJWTService {

    void saveBlackListToken(String jwt);

    boolean isBlackListToken(String token);

}
