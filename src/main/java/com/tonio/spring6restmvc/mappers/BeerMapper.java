package com.tonio.spring6restmvc.mappers;

import com.tonio.spring6restmvc.entities.Beer;
import com.tonio.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {
    BeerDTO beerToBeerDTO(Beer beer);
    Beer beerDtoToBeer(BeerDTO dto);
}
