package com.andrei.accela.codingexercise.service;

import com.andrei.accela.codingexercise.dto.AddressDto;
import com.andrei.accela.codingexercise.dto.PersonDto;
import com.andrei.accela.codingexercise.exception.GeneralException;
import com.andrei.accela.codingexercise.mapper.PersonMapper;
import com.andrei.accela.codingexercise.model.Person;
import com.andrei.accela.codingexercise.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final AddressService addressService;

    @Override
    public Page<PersonDto> getAllPersonsByPage(Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("firstName").descending());

        return personRepository.findAll(pageable).map(personMapper::personEntityToDto);
    }

    @Override
    public PersonDto save(PersonDto personDto) {
        final Set<AddressDto> dtoAddresses = personDto.getAddresses();

        personDto.setAddresses(Collections.emptySet());

        final Person person = personMapper.personDtoToEntity(personDto);
        final Person savedPerson = personRepository.save(person);

        addressService.saveAddressList(savedPerson, dtoAddresses);
        savedPerson.setAddresses(addressService.getAllPersonAddresses(savedPerson));

        log.info("Person Saved: {}", person.getId());
        return personMapper.personEntityToDto(savedPerson);
    }

    @Override
    public Person getPersonById(UUID id) {
        return personRepository.findById(id).orElseThrow(() -> new GeneralException("Person with id: " + id + " not found"));
    }

    @Override
    public PersonDto update(UUID id, PersonDto personDto) {

        final Person person = getPersonById(id);
        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());

        final Person updatedPerson = personRepository.save(person);

        if(!CollectionUtils.isEmpty(personDto.getAddresses())) {
            addressService.saveAddressList(updatedPerson, personDto.getAddresses());
        }

        return personMapper.personEntityToDto(updatedPerson);
    }

    @Override
    public void delete(UUID id) {
        final Person person = getPersonById(id);
        personRepository.delete(person);
    }

    @Override
    public PersonDto getPerson(UUID id) {
        final Person person = getPersonById(id);
        return personMapper.personEntityToDto(person);
    }
}
