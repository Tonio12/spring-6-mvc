package com.tonio.spring6restmvc.service;

import com.tonio.spring6restmvc.model.CustomerDTO;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;


@Service
public class CustomerServiceImpl implements CustomerService {
    private Map<UUID, CustomerDTO> customers;
    public CustomerServiceImpl() {
        this.customers = new HashMap<>();

        CustomerDTO customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("ToniStark")
                .version("v1")
                .createdDate(LocalDate.now())
                .lastDateModified(LocalDate.now())
                .build();

        CustomerDTO customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("PK")
                .version("v1")
                .createdDate(LocalDate.now())
                .lastDateModified(LocalDate.now())
                .build();

        CustomerDTO customer3 = CustomerDTO.builder()
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
    public List<CustomerDTO> listCustomers() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.of(customers.get(id));
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customer) {
        CustomerDTO savedCustomer = CustomerDTO.builder()
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
    public Optional<CustomerDTO> updateById(UUID id , CustomerDTO customer) {
        CustomerDTO existing = customers.get(id);
        existing.setCustomerName(customer.getCustomerName());

        customers.put(existing.getId(), existing);
        return Optional.of(existing);
    }

    @Override
    public Boolean deleteById(UUID customerId) {
        customers.remove(customerId);
        return true;
    }

    @Override
    public Optional<CustomerDTO> patchById(UUID id, CustomerDTO customer) {
        CustomerDTO existing = customers.get(id);

        if(customer.getCustomerName() != null){
            existing.setCustomerName(customer.getCustomerName());
        }
        return Optional.of(existing);
    }
}
