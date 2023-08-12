package com.tonio.spring6restmvc.controller;

import com.tonio.spring6restmvc.entities.Customer;
import com.tonio.spring6restmvc.mappers.CustomerMapper;
import com.tonio.spring6restmvc.model.CustomerDTO;
import com.tonio.spring6restmvc.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class CustomerControllerIT {
    @Autowired
    CustomerController customerController;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerMapper customerMapper;

    @Test
    void testListCustomers(){
        List<CustomerDTO> customerDTOS = customerController.listCustomers();

        assertThat(customerDTOS.size()).isEqualTo(3);
    }

    @Test
    void testGetCustomerById(){
        Customer customer = customerRepository.findAll().get(0);

        CustomerDTO dto = customerController.getCustomerById(customer.getId());

        assertThat(customer.getId()).isEqualTo(dto.getId());
    }

    @Test
    void testGetCustomerByIdNotFound(){
        assertThrows(NotFoundException.class, ()->customerController.getCustomerById(UUID.randomUUID()));
    }

    @Test
    @Transactional
    @Rollback
    void testUpdateCustomer(){
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        customerDTO.setId(null);
        customerDTO.setCustomerName("UPDATED");

        var responseEntity = customerController.updateById(customer.getId(), customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(204));

        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();

        assertThat(updatedCustomer.getCustomerName()).isEqualTo(customerDTO.getCustomerName());
    }

    @Test
    void updateNotFound(){
        assertThrows(NotFoundException.class, () ->
                customerController.updateById(UUID.randomUUID(), CustomerDTO.builder().build()));
    }

    @Test
    @Transactional
    @Rollback
    void testDeleteCustomer(){
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO dto = customerMapper.customerToCustomerDto(customer);

        var responseEntity = customerController.deleteCustomerById(dto.getId());

        assertThrows(NotFoundException.class, ()->customerController.getCustomerById(customer.getId()));
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(204));
    }

    @Test
    void testNotFoundDelete(){
        assertThrows(NotFoundException.class, ()->customerController.deleteCustomerById(UUID.randomUUID()));
    }

    @Test
    void testPatchById(){
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO dto = customerMapper.customerToCustomerDto(customer);
        dto.setCustomerName("PATCHED");
        dto.setId(null);
        dto.setVersion(null);

        ResponseEntity<String> responseEntity = customerController.updateCustomerByPatch(customer.getId(),dto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(204));
    }
}