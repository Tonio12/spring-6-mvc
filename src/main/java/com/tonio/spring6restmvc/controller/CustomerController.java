package com.tonio.spring6restmvc.controller;

import com.tonio.spring6restmvc.model.CustomerDTO;
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
    public List<CustomerDTO> listCustomers(){
        return customerService.listCustomers();
    }
    @GetMapping(Customer_Path_Id)
    public CustomerDTO getCustomerById(@PathVariable UUID customerId){
        return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity<String> saveCustomer(@RequestBody CustomerDTO customer){
        CustomerDTO savedCustomerCustomer = customerService.saveCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + savedCustomerCustomer.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(Customer_Path_Id)
    public ResponseEntity<String> updateById(@PathVariable("customerId") UUID id,@RequestBody CustomerDTO customer){

        if(customerService.updateById(id, customer).isEmpty()){
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(Customer_Path_Id)
    public  ResponseEntity<String> deleteCustomerById(@PathVariable("customerId")UUID customerId){
        if(!customerService.deleteById(customerId)){
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(Customer_Path_Id)
    public ResponseEntity<String> updateCustomerByPatch(@PathVariable("customerId") UUID id,@RequestBody CustomerDTO customer){
        if(customerService.patchById(id, customer).isEmpty()){
            throw new NotFoundException();
        }

        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
