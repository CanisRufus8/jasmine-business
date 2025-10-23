package vn.jasmine.service.human;

import vn.jasmine.dto.request.human.CustomerDto;
import vn.jasmine.entity.human.CustomerEntity;
import vn.jasmine.repository.human.ICustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomerServiceImpl implements ICustomerService {

    private final ICustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public CustomerDto createCustomer(CustomerDto customerDTO) {
        log.info(customerDTO.toString());
        CustomerEntity customerEntity = modelMapper.map(customerDTO, CustomerEntity.class);
        customerEntity = customerRepository.save(customerEntity);
        return modelMapper.map(customerEntity, CustomerDto.class);
    }

}
