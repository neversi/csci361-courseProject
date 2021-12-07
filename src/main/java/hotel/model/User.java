package hotel.model;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
        super("users");
        this.id = 0L;
        this.name = uDTO.name;
        this.password = uDTO.password;
        this.surname = uDTO.surname;
        this.username = uDTO.username;
        this.salt = salt;
    }

    public User() { 
        super("users");
        this.id = 0L;
        this.name = "";
        this.surname = "";
        this.username = "";
        this.password = "";
        this.salt = "";
    }

    public User(String name, String phone) {
        super("users");
        this.name = name;
    }

    public User(Long id, String name, String phone, String password) {
        super("users");
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

    public void setPlaceHolders(PreparedStatement preparedStatement) throws Exception {
        try {
            
            Field[] fields = this.getClass().getDeclaredFields();
            
            String seq = preparedStatement.toString();
            int count = 0;

            for (char c: seq.toCharArray()) {
                if (c == '?') {
                    count++;
                }
            }
            
            Map<Integer, String> params = new TreeMap<>();
            int i;
            for (Field f : fields) {
                if ((i = seq.indexOf(f.getName())) != -1 && seq.charAt(i - 1) == ' ') {
                    params.put(i, f.getName());
                }
            }

            if (params.size() != count) {
                throw new Exception("Incorrect num of params, given: " + count + ", expected: " + params.size());
            }

            count = 1;
            for (Integer k : params.keySet()) {
                preparedStatement.setObject(count++, this.getClass().getDeclaredField(params.get(k)).get(this));
            }

        } catch (Exception e) {
            throw new Exception("User.setPlaceHolders:" + e.toString());
        }
    }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getUsername() { return this.username; }
    public void setUsername(String phone) { this.username = phone; }

    public String getSalt() { return this.salt; }
    public void setSalt(String salt) { this.salt = salt; }

    public String getPwd() { return this.password; }
    public void setPwd(String password) { this.password = password; }


}
