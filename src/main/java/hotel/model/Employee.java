package hotel.model;

public class Employee extends ModelSQL {
    public int hotel_id;
    public int employee_id;
    public int rolle;
    public String name;
    public String surname;
    public String position;
    public int salary;

    public Employee() {}

    public String[] pKey() {
        return new String[]{
            "employee_id"
        };
    }
}
