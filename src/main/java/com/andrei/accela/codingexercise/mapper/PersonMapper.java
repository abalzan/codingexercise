package com.andrei.accela.codingexercise.mapper;

import com.andrei.accela.codingexercise.dto.AddressDto;
import com.andrei.accela.codingexercise.dto.PersonDto;
import com.andrei.accela.codingexercise.model.Address;
import com.andrei.accela.codingexercise.model.Person;
import org.mapstruct.Mapper;

@Mapper
public interface PersonMapper {

    PersonDto personEntityToDto(Person person);

    Person personDtoToEntity(PersonDto personDto);
}
