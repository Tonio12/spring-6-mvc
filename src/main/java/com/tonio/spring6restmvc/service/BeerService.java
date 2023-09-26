package com.tonio.spring6restmvc.service;

import com.tonio.spring6restmvc.model.BeerDTO;
import com.tonio.spring6restmvc.model.BeerStyle;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<BeerDTO> getBeerById(UUID id);

    Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize);

    BeerDTO saveBeer(BeerDTO beer);

    Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer);

    Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer);

    boolean deleteById(UUID beerId);
}
