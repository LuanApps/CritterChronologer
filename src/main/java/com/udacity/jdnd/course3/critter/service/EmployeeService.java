package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.model.EmployeeSkill;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepositoryWithoutJpa;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private EmployeeRepositoryWithoutJpa employeeRepositoryWithoutJpa;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeRepositoryWithoutJpa employeeRepositoryWithoutJpa) {
        this.employeeRepository = employeeRepository;
        this.employeeRepositoryWithoutJpa = employeeRepositoryWithoutJpa;
    }

    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllById(List<Long> ids){
        return employeeRepository.findAllById(ids);
    }

    public Optional<Employee> getEmployeeById(Long id){
        return employeeRepository.findById(id);
    }

    public List<Employee> findEmployeesForService(Set<EmployeeSkill> skills, LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        List<Long> employeesIds = employeeRepositoryWithoutJpa.getEmployeesIdsWithRequiredSkillsAndDay(skills, dayOfWeek);
        List<Employee> employees = employeeRepository.findAllById(employeesIds);
        return employees;
    }

}
