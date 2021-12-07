package hotel.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class ModelSQL implements Serializable, Cloneable {
    
    private static final long serialVersionUID = 1L;

    public Long id;
    public String table;

    public ModelSQL() {};

    public ModelSQL(String table) { this.table = table; this.id = 0L; }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTable() {
        return this.table;
    }

    public abstract boolean readResultSet(ResultSet result) throws SQLException;
    public abstract List<String> placeholders();
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
            int i;
            for (Field f : fields) {
                if ((i = seq.indexOf(f.getName())) != -1 && seq.charAt(i - 1) == ' ') {
                    System.out.println(i + ": " + f.getName());
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
    public void setID(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, id);
    }
}
