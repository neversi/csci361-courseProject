package hotel.model;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.plaf.synth.SynthScrollPaneUI;
import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;

import hotel.model.dto.UserDTO;
import hotel.model.dto.UserSignupDTO;

import java.util.ArrayList;
import java.util.HashMap;

public class User extends ModelSQL {

    private static final long serialVersionUID = 1L;

    private String name;
    private String surname;
    private String username;
    private String password;
    private String salt;
    

    public User(UserSignupDTO uDTO, String salt) {
        super("userss");
        this.id = 0L;
        this.name = uDTO.name;
        this.password = uDTO.password;
        this.surname = uDTO.surname;
        this.username = uDTO.username;
        this.salt = salt;
    }

    public User() { 
        super("userss");
        this.id = 0L;
        this.name = "";
        this.surname = "";
        this.username = "";
        this.password = "";
        this.salt = "";
    }

    public User(String name, String phone) {
        super("userss");
        this.name = name;
    }

    public User(Long id, String name, String phone, String password) {
        super("userss");
        this.name = name;
        this.password = password;
    }

    public boolean readResultSet(ResultSet result) throws SQLException {
        try {
            if (result.next()) {
                this.id = result.getLong(1);
                this.name = result.getString(2);
                this.surname = result.getString(3);
                this.username = result.getString(4);
                this.password = result.getString(5);
                this.salt = result.getString(6);
                return true;
            }
        } catch (SQLException e) {
            throw new SQLException("HERE" + e);
        }
        return false;
    }

    public List<String> placeholders() {
        ArrayList<String> ps = new ArrayList<>();

        ps.add("id");
        ps.add("name");
        ps.add("password");
        ps.add("username");
        ps.add("surname");
        ps.add("salt");
        
        return ps;
    }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return this.surname; }
    public void setSurname(String name) { this.surname = name; }

    public String getUsername() { return this.username; }
    public void setUsername(String phone) { this.username = phone; }

    public String getSalt() { return this.salt; }
    public void setSalt(String salt) { this.salt = salt; }

    public String getPwd() { return this.password; }
    public void setPwd(String password) { this.password = password; }


}
