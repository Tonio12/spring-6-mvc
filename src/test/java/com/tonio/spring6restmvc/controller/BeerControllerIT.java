package com.tonio.spring6restmvc.controller;

import com.tonio.spring6restmvc.entities.Beer;
import com.tonio.spring6restmvc.mappers.BeerMapper;
import com.tonio.spring6restmvc.model.BeerDTO;
import com.tonio.spring6restmvc.repositories.BeerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BeerControllerIT {
    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;
    @Test
    void listBeers() {
        List<BeerDTO> beers= beerController.listBeers();

        assertThat(beers.size()).isEqualTo(3);
    }
    @Test
    void getBeerById(){
        Beer beer = beerRepository.findAll().get(0);

        BeerDTO dto = beerController.getBeerById(beer.getId());

        assertThat(dto.getId()).isEqualTo(beer.getId());
    }

    @Test
    void getBeerByIdNotFound(){
        assertThrows(NotFoundException.class, ()-> beerController.getBeerById(UUID.randomUUID()));
    }

    @Test
    @Transactional
    @Rollback
    void saveBeer(){
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("New Beer")
                .build();

        ResponseEntity<String> responseEntity = beerController.handlePost(beerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Optional<Beer> optionalBeer = beerRepository.findById(savedUUID);
        assertThat(optionalBeer).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    void updateExistingBeer() {
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerMapper.beerToBeerDTO(beer);
        beerDTO.setId(null);
        beerDTO.setVersion(null);
        final String beerName = "UPDATED";
        beerDTO.setBeerName(beerName);

        ResponseEntity<String> responseEntity = beerController.updateById(beer.getId(), beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        if(beerRepository.findById(beer.getId()).isPresent()){
            Beer updatedBeer = beerRepository.findById(beer.getId()).get();
            assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);
        }
    }
    @Test
    void testUpdateNotFound(){
        assertThrows(NotFoundException.class, () ->
                beerController.updateById(UUID.randomUUID(), BeerDTO.builder().build()));
    }
    @Test
    @Transactional
    @Rollback
    void testDeleteById(){
        Beer beer = beerRepository.findAll().get(0);

        ResponseEntity<String> responseEntity = beerController.deleteById(beer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(204));

        assertThrows(NotFoundException.class, ()->beerController.getBeerById(beer.getId()));
    }

    @Test
    void testPatchById(){
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerMapper.beerToBeerDTO(beer);
        beerDTO.setId(null);
        beerDTO.setVersion(null);
        beerDTO.setBeerName("Patched");
        beerDTO.setPrice(BigDecimal.valueOf(123456));

        ResponseEntity<String> responseEntity = beerController.updateBeerPatchById(beer.getId(), beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        if(beerRepository.findById(beer.getId()).isPresent()){
            Beer updatedBeer = beerRepository.findById(beer.getId()).get();
            assertThat(updatedBeer.getBeerName()).isEqualTo(beerDTO.getBeerName());
        }

    }
}