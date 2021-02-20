package com.andrei.accela.codingexercise.bootstrap;

import com.andrei.accela.codingexercise.model.Address;
import com.andrei.accela.codingexercise.model.Person;
import com.andrei.accela.codingexercise.repository.AddressRepository;
import com.andrei.accela.codingexercise.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Component
@Slf4j
public class Bootstrap implements CommandLineRunner {

    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {

        final Set<Address> addresses = new HashSet<>(addressRepository.saveAll(createAddresses()));

        personRepository.save(Person.builder()
                .firstName("Andrei")
                .lastName("Balzan")
                .addresses(addresses)
                .build());

    }

    public Set<Address> createAddresses() {

        final Address address1 = Address.builder()
                .city("Dublin 2")
                .state("Co Dublin")
                .postal("D02 DH60")
                .street("Beaux Lane House, Mercer Street Lower")
                .build();

        final Address address2 = Address.builder()
                .city("Cork")
                .state("Co Cork")
                .postal("T23 Y599")
                .street("Test")
                .build();

        return Set.of(address1, address2);

    }
}