package com.andrei.accela.codingexercise.repository;

import com.andrei.accela.codingexercise.model.Address;
import com.andrei.accela.codingexercise.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {

    List<Person> findByAddresses(Address address);
}
