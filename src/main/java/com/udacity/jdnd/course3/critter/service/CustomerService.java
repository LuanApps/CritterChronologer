package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    private PetRepository petRepository;

    public CustomerService(CustomerRepository customerRepository, PetRepository petsRepository) {
        this.customerRepository = customerRepository;
        this.petRepository = petsRepository;
    }

    public Customer saveCustomer(Customer customer, List<Long> petIds) {
        if (petIds != null && !petIds.isEmpty()) {
            customer.getPets().clear();
            List<Pet> pets = petIds.stream()
                    .map(petId -> petRepository.getOne(petId))
                    .collect(Collectors.toList());
            customer.setPets(pets);
        }
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long customerId){
        return customerRepository.findById(customerId);
    }
}
