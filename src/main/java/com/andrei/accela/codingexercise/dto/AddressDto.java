package com.andrei.accela.codingexercise.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class AddressDto {

    private UUID id;
    private String street;
    private String city;
    private String state;
    private String postal;

}