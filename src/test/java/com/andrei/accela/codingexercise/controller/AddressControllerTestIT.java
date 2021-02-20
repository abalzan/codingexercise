package com.andrei.accela.codingexercise.controller;

import com.andrei.accela.codingexercise.dto.AddressDto;
import com.andrei.accela.codingexercise.model.Address;
import com.andrei.accela.codingexercise.model.Person;
import com.andrei.accela.codingexercise.repository.AddressRepository;
import com.andrei.accela.codingexercise.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class AddressControllerTestIT extends BaseIT {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    @BeforeEach
    public void setup() {
        DefaultMockMvcBuilder mockMvcBuilder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
        this.mockMvc = mockMvcBuilder.build();
    }

    @Test
    void getAddresses() throws Exception {
        response = sendGetRequest("/api/v1/addresses");

        assertResponseStatusIsOk();
    }

    @Test
    void addAddressToPerson() throws Exception {
        long registersBeforeTest = addressRepository.count();
        final Person person = personRepository.findAll().get(0);
        String jsonInput = getJsonAsString("json/add_address.json");
        response = sendPostRequestWithJson("/api/v1/persons/"+person.getId()+"/addresses/", jsonInput);

        Assertions.assertEquals(addressRepository.count(), ++registersBeforeTest);
        assertResponseStatusIsOk();

    }

    @Test
    void updateAddress() throws Exception {
        final Address address = addressRepository.findAll().get(0);
        String jsonInput = getJsonAsString("json/update_address.json");
        response = sendPutRequestWithJson("/api/v1/addresses/" +address.getId(), jsonInput);

        assertResponseStatusIsOk();

        Address updatedAddress = addressRepository.findById(address.getId()).get();

        Assertions.assertEquals("update street", updatedAddress.getStreet());
        Assertions.assertEquals("Update City", updatedAddress.getCity());
        Assertions.assertEquals("Update State", updatedAddress.getState());
    }

    @Transactional
    @Test
    void deleteAddress() throws Exception {
        final Person person = personRepository.findAll().get(0);
        final Address address = person.getAddresses().stream().findAny().get();
        response = sendDeleteRequest("/api/v1/persons/"+person.getId()+"/addresses/"+address.getId());

        Assertions.assertEquals(Optional.empty(), addressRepository.findById(address.getId()));
    }


    List<AddressDto> createAddresses() {
        final AddressDto addressDto1 = AddressDto.builder()
                .id(UUID.randomUUID())
                .city("Dublin")
                .state("Co Dublin")
                .postal("D01 VV01")
                .street("Dublin Street")
                .build();


        final AddressDto addressDto2 = AddressDto.builder()
                .id(UUID.randomUUID())
                .city("Cork")
                .state("Co Cork")
                .postal("C01 VV01")
                .street("Cork Street")
                .build();

        return List.of(addressDto1, addressDto2);
    }
}