package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private CustomerRepository customersRepository;

    private PetRepository petsRepository;

    public CustomerService(CustomerRepository customersRepository, PetRepository petsRepository) {
        this.customersRepository = customersRepository;
        this.petsRepository = petsRepository;
    }

    public Customer saveCustomer(Customer customer) {
        return customersRepository.save(customer);
    }

    public List<Customer> getAllCustomers(){
        return customersRepository.findAll();
    }
}
