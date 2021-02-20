package com.andrei.accela.codingexercise.controller;

import com.andrei.accela.codingexercise.dto.PersonDto;
import com.andrei.accela.codingexercise.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/persons")
public class PersonController {

    private static final String DEFAULT_PAGE_SIZE = "20";
    private static final String DEFAULT_PAGE_NUMBER = "0";

    private final PersonService personService;
    @GetMapping
    public ResponseEntity<Page<PersonDto>> listPersons(@RequestParam(value="pagenumber", defaultValue=DEFAULT_PAGE_NUMBER) Integer pageNumber,
                                      @RequestParam(value="pagesize", defaultValue=DEFAULT_PAGE_SIZE) Integer pageSize) {

        return ResponseEntity.ok().body(personService.getAllPersonsByPage(pageNumber, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getPersonById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(personService.getPerson(id));
    }

    @PostMapping
    public ResponseEntity<PersonDto> addPerson(@RequestBody PersonDto person) {

        PersonDto savedPerson  = personService.save(person);
        return new ResponseEntity<>(savedPerson, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable UUID id, @RequestBody PersonDto personDto) {
        PersonDto updatedPerson = personService.update(id, personDto);
        return ResponseEntity.ok(updatedPerson);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable UUID id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
