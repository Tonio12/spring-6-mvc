package com.tonio.spring6restmvc.service;

import com.tonio.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<CustomerDTO> listCustomers();
    Optional<CustomerDTO> getCustomerById(UUID id);
    CustomerDTO saveCustomer(CustomerDTO customer);

    void updateById(UUID id, CustomerDTO customer);

    void deleteById(UUID customerId);

    void patchById(UUID id, CustomerDTO customer);
}
