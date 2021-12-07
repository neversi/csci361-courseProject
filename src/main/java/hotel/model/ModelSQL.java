package hotel.model;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
    public abstract void setPlaceHolders(PreparedStatement preparedStatement) throws Exception;
    public void setID(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, id);
    }
}
