package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private ScheduleService scheduleService;
    private EmployeeService employeeService;
    private PetService petService;
    private CustomerService customerService;

    public ScheduleController(ScheduleService scheduleService, EmployeeService employeeService, PetService petService, CustomerService customerService) {
        this.scheduleService = scheduleService;
        this.employeeService = employeeService;
        this.petService = petService;
        this.customerService = customerService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleService.getScheduleById(scheduleDTO.getId()).orElseGet(Schedule::new);
        schedule = scheduleDTOtoSchedule(scheduleDTO);
        Schedule savedSchedule = scheduleService.saveSchedule(schedule);
        return convertToDTO(savedSchedule);

    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.getAllSchedules().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId).orElseThrow(() -> new RuntimeException("Could not find a pet with id: "+ petId));
        return scheduleService.getSchedulesByPetId(pet.getId()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId).orElseThrow(() -> new RuntimeException("Could not find an employee wuth id: "+ employeeId));
        return scheduleService.getSchedulesByEmployeeId(employee.getId()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        Customer customer = customerService.getCustomerById(customerId).orElseThrow(() -> new RuntimeException("Could not find an employee wuth id: "+ customerId));
        return  scheduleService.getSchedulesByCustomerId(customer.getId()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ScheduleDTO convertToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(schedule.getActivities());

        List<Long> employeeIds = schedule.getEmployees().stream()
                .map(Employee::getId)
                .collect(Collectors.toList());
        scheduleDTO.setEmployeeIds(employeeIds);

        List<Long> petIds = schedule.getPets().stream()
                .map(Pet::getId)
                .collect(Collectors.toList());
        scheduleDTO.setPetIds(petIds);

        return scheduleDTO;
    }

    private Schedule scheduleDTOtoSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        schedule.setDate(scheduleDTO.getDate());
        schedule.setActivities(scheduleDTO.getActivities());

        List<Employee> employees = employeeService.getAllById(scheduleDTO.getEmployeeIds());
        schedule.setEmployees(employees);

        List<Pet> pets = petService.getAllById(scheduleDTO.getPetIds());
        schedule.setPets(pets);

        return schedule;
    }
}
