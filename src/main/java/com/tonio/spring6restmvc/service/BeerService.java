package com.tonio.spring6restmvc.service;

import com.tonio.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<BeerDTO> getBeerById(UUID id);

    List<BeerDTO> listBeers();

    BeerDTO saveBeer(BeerDTO beer);

    void updateBeerById(UUID beerId, BeerDTO beer);

    void patchBeerById(UUID beerId, BeerDTO beer);

    void deleteById(UUID beerId);
}
