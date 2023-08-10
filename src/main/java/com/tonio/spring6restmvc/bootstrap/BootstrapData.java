package com.tonio.spring6restmvc.bootstrap;

import com.tonio.spring6restmvc.entities.Beer;
import com.tonio.spring6restmvc.entities.Customer;
import com.tonio.spring6restmvc.model.BeerDTO;
import com.tonio.spring6restmvc.model.BeerStyle;
import com.tonio.spring6restmvc.model.CustomerDTO;
import com.tonio.spring6restmvc.repositories.BeerRepository;
import com.tonio.spring6restmvc.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;
    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();
    }

    private void loadCustomerData() {
        if(customerRepository.count() == 0){
            Customer customer1 = Customer.builder()
                    .customerName("ToniStark")
                    .createdDate(LocalDate.now())
                    .lastDateModified(LocalDate.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .customerName("PK")
                    .createdDate(LocalDate.now())
                    .lastDateModified(LocalDate.now())
                    .build();

            Customer customer3 = Customer.builder()
                    .customerName("Fortu")
                    .createdDate(LocalDate.now())
                    .lastDateModified(LocalDate.now())
                    .build();

            customerRepository.saveAll(List.of(customer1,customer2,customer3));
        }
    }

    private void loadBeerData() {

        if(beerRepository.count() == 0){
            Beer beer1 = Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("12356")
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(122)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Crank")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("12356222")
                    .price(new BigDecimal("11.99"))
                    .quantityOnHand(392)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Sunshine City")
                    .beerStyle(BeerStyle.IPA)
                    .upc("12356")
                    .price(new BigDecimal("13.99"))
                    .quantityOnHand(144)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            beerRepository.save(beer1);
            beerRepository.save(beer2);
            beerRepository.save(beer3);
        }
    }
}
