package hotel.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import hotel.model.Employee;
import hotel.repository.EmployeeRepository;
import hotel.repository.postgres.EmployeeRepositoryPostgres;

public class EmployeeService {
    
    public EmployeeRepository er;

    public EmployeeService() {
        er = new EmployeeRepositoryPostgres();
    }

    public Optional<Employee> getByEmail(String email) {
        Employee emp = new Employee();
        emp.email = email;
        try {
            emp = er.getOneByParam(emp, "email");
            if (emp == null) {
                return Optional.empty();
            }

            return Optional.of(emp);
        } finally {}
    }

    public List<Employee> getEmployeesByHotel(int id) {
        List<Employee> emps = new ArrayList<>();

        try {
            Employee emp = new Employee();
            emp.hotel_id = id;

            emps = er.getListByParam(emp, "hotel_id");

        } finally {}
        
        return emps;
    }
}
