package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private PetRepository petRepository;
    private CustomerRepository customerRepository;

    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public Pet savePet(Pet pet, long ownerId){
        Customer customer = customerRepository.findById(ownerId).orElseThrow(() -> new IllegalArgumentException("Invalid owner ID"));
        pet.setCustomer(customer);
        pet = petRepository.save(pet);
        customer.getPets().add(pet);
        customerRepository.save(customer);
        return pet;
    }

    public Optional<Pet> getPetById(Long petId){
        return petRepository.findById(petId);
    }

    public List<Pet> getPetsByOnwer(Long customerId){
        return petRepository.getPetsByCustomerId(customerId);
    }

    public List<Pet> getAllPets(){
        return petRepository.findAll();
    }

}
