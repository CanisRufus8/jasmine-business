package vn.jasmine.controller.human;

import vn.jasmine.dto.request.human.CustomerDto;
import vn.jasmine.service.human.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final ICustomerService customerService;

    //    @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    @PostMapping(value = "/create")
    public CustomerDto create(@RequestBody CustomerDto customerDTO) {
        return customerService.createCustomer(customerDTO);
    }

}
