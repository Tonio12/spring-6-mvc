package com.tonio.spring6restmvc.service;

import com.tonio.spring6restmvc.model.Beer;
import com.tonio.spring6restmvc.model.Customer;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;


@Service
public class CustomerServiceImpl implements CustomerService {
    private Map<UUID, Customer> customers;
    public CustomerServiceImpl() {
        this.customers = new HashMap<>();

        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("ToniStark")
                .version("v1")
                .createdDate(LocalDate.now())
                .lastDateModified(LocalDate.now())
                .build();

        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("PK")
                .version("v1")
                .createdDate(LocalDate.now())
                .lastDateModified(LocalDate.now())
                .build();

        Customer customer3 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Fortu")
                .version("v1")
                .createdDate(LocalDate.now())
                .lastDateModified(LocalDate.now())
                .build();

        customers.put(customer1.getId(), customer1);
        customers.put(customer2.getId(), customer2);
        customers.put(customer3.getId(), customer3);
    }

    @Override
    public List<Customer> listCustomers() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public Customer getCustomerById(UUID id) {
        return customers.get(id);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        Customer savedCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDate.now())
                .lastDateModified(LocalDate.now())
                .customerName(customer.getCustomerName())
                .version("v1")
                .build();

        customers.put(savedCustomer.getId(), savedCustomer);

        return savedCustomer;
    }
}
