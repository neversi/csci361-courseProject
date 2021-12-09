package hotel.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import hotel.model.Employee;
import hotel.model.WorkingSchedule;
import hotel.model.dto.EmployeeWSDTO;
import hotel.repository.EmployeeRepository;
import hotel.repository.WorkingScheduleRepository;
import hotel.repository.postgres.EmployeeRepositoryPostgres;
import hotel.repository.postgres.WorkingScheduleRepositoryPostgres;

public class EmployeeService {
    
    public EmployeeRepository er;
    public WorkingScheduleRepository wr;

    public EmployeeService() {
        er = new EmployeeRepositoryPostgres();
        wr = new WorkingScheduleRepositoryPostgres();
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

    public List<EmployeeWSDTO> getEmployeesTable(int hotelId) {
        List<EmployeeWSDTO> empws = new ArrayList<>();
        try {
            List<Employee> emps = getEmployeesByHotel(hotelId);
            for (Employee emp: emps) {
                WorkingSchedule ws = new WorkingSchedule();
                ws.employee_id = emp.employee_id;
                ws = wr.getOneByParam(ws, "employee_id");
                EmployeeWSDTO empw = new EmployeeWSDTO();
                empw.froom = ws.froom;
                empw.too = ws.too;
                empw.position = emp.position;
                empw.name = emp.name;
                empw.surname = emp.surname;
                empw.salary = emp.salary;
                empws.add(empw);
            }
        } finally {}
        
        return empws;
    }
}
