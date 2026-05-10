package com.hemant.db.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hemant.db.exception.ResourceNotFoundException;
import com.hemant.db.model.Address;
import com.hemant.db.repository.AddressRepository;

@Service
@Transactional
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public List<Address> createAddress(Address address) {
        addressRepository.save(address);
        return addressRepository.findByEmpId(address.getEmpId());
    }

    public List<Address> findByCity(String city) {
        return addressRepository.findByCity(city);
    }

    public List<Address> findByState(String state) {
        return addressRepository.findByState(state);
    }

    public Address updateAddress(Integer id, String address1, String address2, String city, String state, Integer pin) {
        Address address = findById(id);
        address.setAddress1(address1);
        address.setAddress2(address2);
        address.setCity(city);
        address.setState(state);
        address.setPIN(pin);
        return addressRepository.save(address);
    }

    public void deleteAddress(Integer id) {
        Address address = findById(id);
        addressRepository.delete(address);
    }

    private Address findById(Integer id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address", id));
    }
}
