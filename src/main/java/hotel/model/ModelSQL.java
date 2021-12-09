package hotel.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class ModelSQL implements Serializable, Cloneable {
    
    public ModelSQL() {};

    public String tableName() {
        return "modelSQL";
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
    

    public boolean readResultSet(ResultSet result) throws SQLException {
        try {
            if (result.next()) {
                Field[] fields = this.getClass().getDeclaredFields();
                for (Field f: fields) {
                    try {
                        Object o = result.getObject(f.getName());
                        System.out.println(f.getName() + ": " + o);
                        if (o instanceof java.sql.Date) {
                            Date d = (Date) o;
                            f.set(this, d.toLocalDate());
                        } else {
                            f.set(this, o);
                        }
                        // System.out.println(o.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new SQLException("readResult: " + e.toString());
                    }
                }
                
                return true;
            }
        } catch (SQLException e) {
            throw new SQLException("User.readResultSet: " + e);
        }
        return false;
    }

    public List<String> placeholders() {
        ArrayList<String> ps = new ArrayList<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field f: fields) {
            try {
                System.out.println(f.getName());
                ps.add(f.getName());
            } catch (Exception e) {
            }
        }  
        return ps;
    }
    public void setPlaceHolders(PreparedStatement preparedStatement) throws Exception {
        try {
            Field[] fields = this.getClass().getDeclaredFields();
            String seq = preparedStatement.toString();
            int count = 0;
            System.out.println(seq);

            for (char c: seq.toCharArray()) {
                if (c == '?') {
                    count++;
                }
            }

            Map<Integer, String> params = new TreeMap<>();
            System.out.println("----- Fields name -----");
            for (Field f : fields) {
                int i = 0;
                int prev = 0;
                System.out.println(f.getName());
                while (true) {
                    i = seq.indexOf(f.getName(), prev);
                    if (i != -1) {
                        if (i > 0 && seq.charAt(i - 1) == ' ') {
                            params.put(i, f.getName());
                            break;
                        }
                        System.out.println(seq.substring(i));
                        prev = i + f.getName().length();
                    } else {
                        break;
                    }
                }
                if ((i = seq.indexOf(f.getName(), prev)) != -1 && seq.charAt(i - 1) == ' ') {
                    System.out.println(i + ": " + f.getName());
                    params.put(i, f.getName());
                }
            }

            if (params.size() != count) {
                throw new Exception("Incorrect num of params, given: " + count + ", expected: " + params.size());
            }

            count = 1;
            System.out.println("----- Param Names -----");

            for (Integer k : params.keySet()) {
                System.out.println(k + ": " + this.getClass().getDeclaredField(params.get(k)).get(this));
                preparedStatement.setObject(count++, this.getClass().getDeclaredField(params.get(k)).get(this));
            }
            System.out.println("-----             -----");

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("User.setPlaceHolders:" + e.toString());
        }
    }

    public abstract String[] pKey();
}
