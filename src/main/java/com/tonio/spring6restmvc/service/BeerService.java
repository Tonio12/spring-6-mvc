package com.tonio.spring6restmvc.service;

import com.tonio.spring6restmvc.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {
    Beer getBeerById(UUID id);

    List<Beer> listBeers();

    Beer saveBeer(Beer beer);

    void updateBeerById(UUID beerId, Beer beer);

    void patchBeerById(UUID beerId, Beer beer);

    void deleteById(UUID beerId);
}
