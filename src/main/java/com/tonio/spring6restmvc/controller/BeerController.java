package com.tonio.spring6restmvc.controller;

import com.tonio.spring6restmvc.model.Beer;
import com.tonio.spring6restmvc.service.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
public class BeerController {
    private final BeerService beerService;
    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    @GetMapping(BEER_PATH)
    public List<Beer> listBeers(){
        return beerService.listBeers();
    }

    @PostMapping(BEER_PATH)
    public ResponseEntity<String> handlePost(@RequestBody Beer beer){
        Beer savedBeer = beerService.saveBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    @GetMapping(BEER_PATH_ID)
    public Beer getBeerById(@PathVariable("beerId") UUID beerId){

        log.debug("Get Beer by Id - in controller");

        return beerService.getBeerById(beerId);
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity<String> updateById(@PathVariable("beerId")UUID beerId, @RequestBody Beer beer){

        beerService.updateBeerById(beerId, beer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity<String> updateBeerPatchById(@PathVariable("beerId")UUID beerId, @RequestBody Beer beer){

        beerService.patchBeerById(beerId, beer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity<String> deleteById(@PathVariable("beerId") UUID beerId){

        beerService.deleteById(beerId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(){
        return ResponseEntity.notFound().build();
    }

}
