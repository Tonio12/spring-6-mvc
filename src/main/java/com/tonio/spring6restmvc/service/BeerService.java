package com.tonio.spring6restmvc.service;

import com.tonio.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<BeerDTO> getBeerById(UUID id);

    List<BeerDTO> listBeers();

    BeerDTO saveBeer(BeerDTO beer);

    Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer);

    Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer);

    boolean deleteById(UUID beerId);
}
