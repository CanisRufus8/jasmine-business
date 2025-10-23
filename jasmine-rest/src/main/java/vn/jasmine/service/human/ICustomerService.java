package vn.jasmine.service.human;

import vn.jasmine.dto.request.human.CustomerDto;

public interface ICustomerService {

    CustomerDto createCustomer(CustomerDto customerDTO);

}
