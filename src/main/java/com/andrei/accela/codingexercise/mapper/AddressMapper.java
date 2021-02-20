package com.andrei.accela.codingexercise.mapper;

import com.andrei.accela.codingexercise.dto.AddressDto;
import com.andrei.accela.codingexercise.model.Address;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {

    AddressDto addressEntityToDto(Address address);

    Address addressDtoToEntity(AddressDto addressDto);
}
