package com.hemant.db.resource;

import com.hemant.db.model.Address;
import com.hemant.db.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/Address")
public class AddressResource {
	
	@Autowired
    AddressRepository AddressRepository;

    @GetMapping(value = "/all")
    public List<Address> getAll() {
        return AddressRepository.findAll();
    }

    @PostMapping(value = "/insert")
    public List<Address> persist(@RequestBody final Address Address) {
        AddressRepository.save(Address);
        return AddressRepository.findAll();
    }  
    
    @GetMapping(value = "/findbycity")
    public List<Address> fetchDataByCity(@RequestParam("city") String city) {
    	return AddressRepository.findByCity(city);
    }
    
    @GetMapping(value = "/findbystate")
    public List<Address> fetchDataByState(@RequestParam("state") String state) {
    	return AddressRepository.findByState(state);
    }
    
    @PostMapping("/updateaddress")
    public List<Address> updateAddress(@RequestParam("Id") Integer Id, @RequestParam("address1") String address1, @RequestParam("address2") String address2, @RequestParam("city") String city, @RequestParam("state") String state, @RequestParam("pin") Integer pin) {
    	Address a = AddressRepository.findById(Id).get();
    	a.setAddress1(address1); a.setAddress2(address2); a.setCity(city); a.setState(state); a.setPIN(pin);
    	AddressRepository.save(a);
    	return AddressRepository.findAll();
    }
    
    @DeleteMapping("/delete")
    public List<Address> deleteUser(@RequestParam("Id") Integer Id){
    	AddressRepository.deleteById(Id);
    	return AddressRepository.findAll();
    }
}
