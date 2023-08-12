package com.tonio.spring6restmvc.service;

import com.tonio.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<CustomerDTO> listCustomers();
    Optional<CustomerDTO> getCustomerById(UUID id);
    CustomerDTO saveCustomer(CustomerDTO customer);

    Optional<CustomerDTO> updateById(UUID id, CustomerDTO customer);

    Boolean deleteById(UUID customerId);

    Optional<CustomerDTO> patchById(UUID id, CustomerDTO customer);
}
