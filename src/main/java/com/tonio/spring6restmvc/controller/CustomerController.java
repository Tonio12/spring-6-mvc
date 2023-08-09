package com.tonio.spring6restmvc.controller;

import com.tonio.spring6restmvc.model.Customer;
import com.tonio.spring6restmvc.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
public class CustomerController {
    private final CustomerService customerService;
    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String Customer_Path_Id = CUSTOMER_PATH + "{customerId}";
    @GetMapping(CUSTOMER_PATH)
    public List<Customer> listCustomers(){
        return customerService.listCustomers();
    }
    @GetMapping(Customer_Path_Id)
    public Customer getCustomerById(@PathVariable UUID customerId){
        return customerService.getCustomerById(customerId);
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity<String> saveCustomer(@RequestBody Customer customer){
        Customer savedCustomercustomer = customerService.saveCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + savedCustomercustomer.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(Customer_Path_Id)
    public ResponseEntity<String> updateById(@PathVariable("customerId") UUID id,@RequestBody Customer customer){
        customerService.updateById(id, customer);

        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(Customer_Path_Id)
    public  ResponseEntity<String> deleteCustomerById(@PathVariable("customerId")UUID customerId){
        customerService.deleteById(customerId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(Customer_Path_Id)
    public ResponseEntity<String> updateCustomerByPatch(@PathVariable("customerId") UUID id,@RequestBody Customer customer){
        customerService.patchById(id, customer);

        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
