package com.andrei.accela.codingexercise.service;

import com.andrei.accela.codingexercise.dto.PersonDto;
import com.andrei.accela.codingexercise.model.Person;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface PersonService {
    Page<PersonDto> getAllPersonsByPage(Integer pageNumber, Integer pageSize);
    PersonDto save(PersonDto personDto);
    Person getPersonById(UUID id);
    PersonDto update(UUID id, PersonDto personDto);
    void delete(UUID id);
    PersonDto getPerson(UUID id);
}
