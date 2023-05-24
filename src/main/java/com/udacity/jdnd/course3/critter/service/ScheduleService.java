package com.udacity.jdnd.course3.critter.service;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleService {

    private ScheduleRepository scheduleRepository;
    private CustomerRepository customerRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, CustomerRepository customerRepository) {
        this.scheduleRepository = scheduleRepository;
        this.customerRepository = customerRepository;
    }

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public Optional<Schedule> getScheduleById(Long scheduleId){
        return scheduleRepository.findById(scheduleId);
    }

    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    }

    public List<Schedule> getSchedulesByPetId(Long petId){
        return scheduleRepository.findByPetsId(petId);
    }

    public List<Schedule> getSchedulesByEmployeeId(Long employeeId){
        return scheduleRepository.findByEmployeesId(employeeId);
    }

    public List<Schedule> getSchedulesByCustomerId(Long customerId){
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Could not find an customer wuth id: "+ customerId));
        return customer.getPets().stream()
                .flatMap(pet -> scheduleRepository.findByPetsId(pet.getId()).stream())
                .collect(Collectors.toList());
    }
}