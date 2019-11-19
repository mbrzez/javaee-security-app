package pl.brzezins.dao;

import pl.brzezins.entity.Employee;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class EmployeeDao {
    @PersistenceContext(unitName = "mysqlds")
    EntityManager entityManager;

    public void createUser(String name, String surname) {
        entityManager.persist(new Employee());
    }

    public void removeUser() {
        //TODO
    }

    public void updateUser() {
        //TODO
    }

    public List<Employee> getAllEmployees() {
        //TODO

        return new ArrayList<Employee>();
    }
}
