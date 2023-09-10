package com.tonio.spring6restmvc.repositories;

import com.tonio.spring6restmvc.bootstrap.BootstrapData;
import com.tonio.spring6restmvc.entities.Beer;
import com.tonio.spring6restmvc.model.BeerStyle;
import com.tonio.spring6restmvc.service.BeerCsvServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import({BootstrapData.class, BeerCsvServiceImpl.class})
class BeerRepositoryTest {
    @Autowired
    BeerRepository beerRepository;
    @Test
    void testSaveBeer(){
        Beer savedBeer = beerRepository.save(Beer.builder()
                .beerName("New Beer")
                .beerStyle(BeerStyle.LAGER)
                .upc("UPC")
                .price(new BigDecimal("3.45"))
                .build());
        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }

    @Test
    void testBeerNameTooLong(){
        assertThrows(ConstraintViolationException.class, ()->{
            beerRepository.save(Beer.builder()
                    .beerName("New Beer".repeat(50))
                    .beerStyle(BeerStyle.LAGER)
                    .upc("UPC")
                    .price(new BigDecimal("3.45"))
                    .build());
            beerRepository.flush();
        });
    }

    @Test
    void testGetBeerListByName(){
        List<Beer> list = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%");

        assertThat(list.size()).isEqualTo(336);
    }
}