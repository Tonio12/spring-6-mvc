package com.tonio.spring6restmvc.bootstrap;

import com.tonio.spring6restmvc.repositories.BeerRepository;
import com.tonio.spring6restmvc.repositories.CustomerRepository;
import com.tonio.spring6restmvc.service.BeerCsvService;
import com.tonio.spring6restmvc.service.BeerCsvServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Import(BeerCsvServiceImpl.class)
class BootstrapDataTest {
    @Autowired
    BeerRepository  beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerCsvService csvService;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp(){
        bootstrapData = new BootstrapData(beerRepository,customerRepository, csvService);
    }


    @Test
     void run() throws Exception {
        bootstrapData.run();

        assertThat(beerRepository.count()).isEqualTo(2413);
        assertThat(customerRepository.count()).isEqualTo(3);
    }
}