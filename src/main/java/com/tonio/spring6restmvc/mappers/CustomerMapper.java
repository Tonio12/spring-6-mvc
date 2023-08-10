package com.tonio.spring6restmvc.mappers;

import com.tonio.spring6restmvc.entities.Customer;
import com.tonio.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    CustomerDTO customerToCustomerDto(Customer customer);
    CustomerDTO customerDtoToCustomer(CustomerDTO dto);
}
