package com.tonio.spring6restmvc.service;

import com.tonio.spring6restmvc.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> listCustomers();
    Customer getCustomerById(UUID id);
    Customer saveCustomer(Customer customer);
}
