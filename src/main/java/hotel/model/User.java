package hotel.model;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import hotel.model.dto.UserSignupDTO;

import java.util.ArrayList;

public class User extends ModelSQL {

    public String tableName() {
        return "Users";
    }

    public String email;
    public String password;
    public String name;
    public String surname;
    public String salt;
    public int money_spent;
    

    public User(UserSignupDTO uDTO, String salt) {
        super();
        this.name = uDTO.name;
        this.password = uDTO.password;
        this.surname = uDTO.surname;
        this.email = uDTO.username;
        this.salt = salt;
    }

    public User() { 
        this.name = "";
        this.surname = "";
        this.email = "";
        this.password = "";
        this.salt = "";
        this.money_spent = 0;
    }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return this.surname; }
    public void setSurname(String name) { this.surname = name; }

    public String getUsername() { return this.email; }
    public void setUsername(String email) { this.email = email; }

    public String getSalt() { return this.salt; }
    public void setSalt(String salt) { this.salt = salt; }

    public String getPwd() { return this.password; }
    public void setPwd(String password) { this.password = password; }

    public String[] pKey() {
        return new String[]{
            "email"
        };
    }
}
