package com.tonio.spring6restmvc.repositories;

import com.tonio.spring6restmvc.entities.Beer;
import com.tonio.spring6restmvc.entities.BeerOrder;
import com.tonio.spring6restmvc.entities.Customer;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BeerOrderRepositoryTest {

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerRepository beerRepository;

    Customer testCustomer;
    Beer testBeer;

    @BeforeEach
    @Transactional
    void setUp() {
        testCustomer = customerRepository.findAll().get(0);
        testBeer = beerRepository.findAll().get(0);
    }

    @Test
    @Transactional
    void TestOrderBeer() {
        BeerOrder beerOrder = BeerOrder.builder()
                .customerRef("Test Customer")
                .customer(testCustomer)
                .build();

        BeerOrder savedBeerOrder = beerOrderRepository.save(beerOrder);

        System.out.println(savedBeerOrder);
    }
}