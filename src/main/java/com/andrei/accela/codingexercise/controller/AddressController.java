package com.andrei.accela.codingexercise.controller;

import com.andrei.accela.codingexercise.dto.AddressDto;
import com.andrei.accela.codingexercise.dto.PersonDto;
import com.andrei.accela.codingexercise.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("addresses")
    public ResponseEntity<List<AddressDto>> getAddresses(){
        return ResponseEntity.ok().body(addressService.getAllAddresses());
    }

    @PostMapping("persons/{personId}/addresses")
    public ResponseEntity<PersonDto> addAddressToPerson(@PathVariable UUID personId, @RequestBody AddressDto addressDto) {

        final PersonDto updatedPerson = addressService.save(personId, addressDto);

        return ResponseEntity.ok().body(updatedPerson);
    }

    @PutMapping("addresses/{id}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable UUID id, @RequestBody AddressDto addressDto) {

        AddressDto updatedAddress = addressService.update(id, addressDto);
        return ResponseEntity.ok().body(updatedAddress);
    }

    @DeleteMapping("persons/{personId}/addresses/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable UUID personId, @PathVariable UUID addressId) {
        addressService.delete(personId, addressId);
        return ResponseEntity.noContent().build();
    }
}
