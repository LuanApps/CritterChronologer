package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.model.EmployeeSkill;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepositoryWithoutJpa {
    @PersistenceContext
    EntityManager entityManager;

    public List<Long> getEmployeesIdsWithRequiredSkillsAndDay(Set<EmployeeSkill> skills, DayOfWeek dayOfWeek) {
        String jpqlQuery = "SELECT e.id " +
                "FROM Employee e " +
                "JOIN e.skills es " +
                "JOIN e.daysAvailable d " +
                "WHERE es IN :skills " +
                "AND d = :dayOfWeek " +
                "GROUP BY e.id " +
                "HAVING COUNT(es) = :skillCount";

        TypedQuery<Long> query = entityManager.createQuery(jpqlQuery, Long.class);
        query.setParameter("skills", skills);
        query.setParameter("dayOfWeek", dayOfWeek);
        query.setParameter("skillCount", (long) skills.size());

        return query.getResultList();
    }
}
