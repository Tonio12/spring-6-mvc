package com.tonio.spring6restmvc.service;

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

    @Override
    public void updateById(UUID id ,Customer customer) {
        Customer existing = customers.get(id);
        existing.setCustomerName(customer.getCustomerName());

        customers.put(existing.getId(), existing);
    }

    @Override
    public void deleteById(UUID customerId) {
        customers.remove(customerId);
    }

    @Override
    public void patchById(UUID id, Customer customer) {
        Customer existing = customers.get(id);

        if(customer.getCustomerName() != null){
            existing.setCustomerName(customer.getCustomerName());
        }
    }
}
