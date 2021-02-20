package com.andrei.accela.codingexercise.service;

import com.andrei.accela.codingexercise.dto.AddressDto;
import com.andrei.accela.codingexercise.dto.PersonDto;
import com.andrei.accela.codingexercise.model.Address;
import com.andrei.accela.codingexercise.model.Person;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface AddressService {

    List<AddressDto> getAllAddresses();
    PersonDto save(UUID personId, AddressDto addressDto);
    void saveAddressList(Person person, Set<AddressDto> addressDto);
    Address getAddressById(UUID id);
    AddressDto update(UUID addressId, AddressDto addressDto);
    void delete(UUID personId, UUID addressId);

    Set<Address>getAllPersonAddresses(Person person);
}
