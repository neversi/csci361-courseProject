package hotel.model;

public class Employee extends ModelSQL {
    public int hotel_id;
    public int employee_id;
    public String name;
    public String email;
    public String surname;
    public String position;
    public int salary;

    public Employee() {}

    public String tableName() {
        return "Employee";
    }
    
    public String[] pKey() {
        return new String[]{
            "employee_id"
        };
    }
}
