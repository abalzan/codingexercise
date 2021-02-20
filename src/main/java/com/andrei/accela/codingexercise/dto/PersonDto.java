package com.andrei.accela.codingexercise.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@Setter
public class PersonDto {

    private UUID id;
    private String firstName;
    private String lastName;

    private Set<AddressDto> addresses;

}
