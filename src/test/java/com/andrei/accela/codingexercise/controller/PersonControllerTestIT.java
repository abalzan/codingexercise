package com.andrei.accela.codingexercise.controller;

import com.andrei.accela.codingexercise.model.Address;
import com.andrei.accela.codingexercise.model.Person;
import com.andrei.accela.codingexercise.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Set;
import java.util.UUID;

@SpringBootTest
class PersonControllerTestIT extends BaseIT {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    PersonRepository personRepository;

    MockMvc mockMvc;

    @Test
    void listPersons() throws Exception {
        response = sendGetRequest
                ("/api/v1/persons");

        assertResponseStatusIsOk();
    }

    @Test
    void getPersonById() throws Exception {
        final Person person = personRepository.findAll().get(0);
        response = sendGetRequest("/api/v1/persons/" + person.getId());

        assertResponseStatusIsOk();
    }

    @Test
    void badRequestWhenInvalidPersonById() throws Exception {
        response = sendGetRequest("/api/v1/persons/" + UUID.randomUUID());

        assertResponseStatusIsBadRequest();
    }


    @Test
    void addPerson() throws Exception {
        long registersBeforeTest = personRepository.count();
        String jsonInput = getJsonAsString("json/add_person_with_address.json");

        response = sendPostRequestWithJson("/api/v1/persons/", jsonInput);

        Assertions.assertEquals(personRepository.count(), ++registersBeforeTest);
        assertResponseStatusIsOk();
    }

    @Transactional
    @Test
    void updatePerson() throws Exception {
        String jsonInput = getJsonAsString("json/update_person_with_address.json");
        final Person person = personRepository.findAll().get(0);
        response = sendPutRequestWithJson("/api/v1/persons/"+person.getId(), jsonInput);

        assertResponseStatusIsOk();

        Person personRepositoryById = personRepository.findById(person.getId()).get();
        Assertions.assertEquals("Update Fake Name", personRepositoryById.getFirstName());
        Assertions.assertEquals("Update Fake Last", personRepositoryById.getLastName());


        final Set<Address> addresses = personRepositoryById.getAddresses();
        Assertions.assertEquals(3, addresses.size());

        Address fakeAddress = addresses.stream().filter(address -> address.getStreet().equals("Update Fake Street")).findAny().get();
        Assertions.assertEquals("Update Fake City", fakeAddress.getCity());
        Assertions.assertEquals("Update Fake state", fakeAddress.getState());

    }

    @Test
    void deletePerson() throws Exception {
        long registersBeforeTest = personRepository.count();

        final Person person = personRepository.findAll().get(0);
        response = sendDeleteRequest("/api/v1/persons/"+person.getId());

        Assertions.assertEquals(personRepository.count(), --registersBeforeTest);
        assertResponseStatusIsNoContent();
    }

}