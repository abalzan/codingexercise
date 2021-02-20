package com.andrei.accela.codingexercise.repository;

import com.andrei.accela.codingexercise.model.Address;
import com.andrei.accela.codingexercise.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {

    Optional<Address> findByCityAndStateAndPostalAndStreet(String city, String state, String postal, String street);

    Set<Address> findByPersons(Person person);
}
