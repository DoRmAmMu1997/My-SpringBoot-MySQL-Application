package com.hemant.db.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hemant.db.model.Address;
import com.hemant.db.service.AddressService;

@Validated
@RestController
@RequestMapping(value = "/rest/Address")
public class AddressResource {
    private final AddressService addressService;

    public AddressResource(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping(value = "/all")
    public List<Address> getAll() {
        return addressService.getAllAddresses();
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<List<Address>> persist(@Valid @RequestBody final Address address) {
        return ResponseEntity.ok(addressService.createAddress(address));
    }

    @GetMapping(value = "/findbycity")
    public List<Address> fetchDataByCity(@RequestParam("city") @NotBlank String city) {
        return addressService.findByCity(city);
    }

    @GetMapping(value = "/findbystate")
    public List<Address> fetchDataByState(@RequestParam("state") @NotBlank String state) {
        return addressService.findByState(state);
    }

    @PostMapping("/updateaddress")
    public ResponseEntity<Address> updateAddress(
            @RequestParam("Id") Integer id,
            @RequestParam("address1") @NotBlank String address1,
            @RequestParam("address2") @NotBlank String address2,
            @RequestParam("city") @NotBlank String city,
            @RequestParam("state") @NotBlank String state,
            @RequestParam("pin") Integer pin) {
        return ResponseEntity.ok(addressService.updateAddress(id, address1, address2, city, state, pin));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam("Id") Integer id) {
        addressService.deleteAddress(id);
        return ResponseEntity.ok("Success");
    }
}
