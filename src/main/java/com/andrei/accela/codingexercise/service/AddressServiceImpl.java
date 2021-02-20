package com.andrei.accela.codingexercise.service;

import com.andrei.accela.codingexercise.dto.AddressDto;
import com.andrei.accela.codingexercise.dto.PersonDto;
import com.andrei.accela.codingexercise.exception.GeneralException;
import com.andrei.accela.codingexercise.mapper.AddressMapper;
import com.andrei.accela.codingexercise.mapper.PersonMapper;
import com.andrei.accela.codingexercise.model.Address;
import com.andrei.accela.codingexercise.model.Person;
import com.andrei.accela.codingexercise.repository.AddressRepository;
import com.andrei.accela.codingexercise.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class AddressServiceImpl implements AddressService{

    private final AddressRepository addressRepository;
    private final PersonRepository personRepository;
    private final AddressMapper addressMapper;
    private final PersonMapper personMapper;

    @Override
    public List<AddressDto> getAllAddresses() {
        return addressRepository.findAll().stream().map(addressMapper::addressEntityToDto).collect(Collectors.toList());
    }

    @Override
    public Set<Address> getAllPersonAddresses(Person person) {
        return addressRepository.findByPersons(person);
    }

    @Override
    public PersonDto save(UUID personId, AddressDto addressDto) {
        final Address address = addressMapper.addressDtoToEntity(addressDto);
        final Person person = personRepository.findById(personId).orElseThrow();
        final Set<Address> addresses = person.getAddresses();
        addresses.add(address);

        final Person savedPerson = personRepository.save(person);
        return personMapper.personEntityToDto(savedPerson);
    }

    @Override
    public void saveAddressList(Person person, Set<AddressDto> addressesDto) {

        for (AddressDto addressDto: addressesDto) {
            Optional<Address> optionalAddress = getAddressByCityAndStateAndPostalAndStreet(addressDto.getCity(), addressDto.getState(), addressDto.getPostal(), addressDto.getStreet());
            optionalAddress.ifPresentOrElse(address -> {
                Set<Person> persons = address.getPersons();
                persons.add(person);
                address.setPersons(persons);
                addressRepository.save(address);
            }, () -> save(person.getId(), addressDto));
        }
    }

    @Override
    public Address getAddressById(UUID id) {
        return addressRepository.findById(id).orElseThrow(() -> new GeneralException("Address with id: " + id + " not found"));
    }

    private Optional<Address> getAddressByCityAndStateAndPostalAndStreet(String city, String state, String postal, String street) {
        return addressRepository.findByCityAndStateAndPostalAndStreet(city, state, postal, street);

    }

    @Override
    public AddressDto update(UUID addressId, AddressDto addressDto) {

        Address address = getAddressById(addressId);
        address.setCity(addressDto.getCity());
        address.setPostal(addressDto.getPostal());
        address.setState(addressDto.getState());
        address.setStreet(addressDto.getStreet());
        addressRepository.save(address);

        final Address updatedAddress = addressRepository.save(address);
        log.info("Address Updated: {}", address.getId());
        return addressMapper.addressEntityToDto(updatedAddress);
    }

    @Override
    public void delete(UUID personId, UUID addressId) {
        final Address address = getAddressById(addressId);

        final Person person = personRepository.findById(personId).orElseThrow(() -> new GeneralException("Person with id " + personId + " not found"));
        person.getAddresses().remove(address);
        personRepository.save(person);

        if(CollectionUtils.isEmpty(personRepository.findByAddresses(address))) {
            addressRepository.delete(address);
        }
    }
}
