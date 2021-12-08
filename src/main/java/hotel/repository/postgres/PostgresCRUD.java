package hotel.repository.postgres;

import hotel.model.ModelSQL;
import hotel.repository.ICRUDRepository;

import java.util.ArrayList;
import java.util.List;

import java.sql.*;

public class PostgresCRUD<T extends ModelSQL> extends Postgres implements ICRUDRepository<T> {

    @SuppressWarnings("unchecked")
    public List<T> getList(T model) {
        ArrayList<T> objects = new ArrayList<>();

        try{
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM "  + model.tableName());
                T newModel = (T) model.clone();
                while(newModel.readResultSet(resultSet)){
                    objects.add(newModel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } 
        
        return objects;
    }
    public T getById(T model) {
        
        try{
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "SELECT * FROM "  + model.tableName() + " WHERE " + model.placeholders().get(0) + " = ?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    ResultSet result = preparedStatement.executeQuery();
                    while(model.readResultSet(result)) {}
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } 
        
        return model;
    }
    public T update(T model) throws Exception {
        try {
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "SELECT * FROM "  + model.tableName() + " WHERE " + model.placeholders().get(0) + " = ?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    ResultSet result = preparedStatement.executeQuery();
                    while(model.readResultSet(result)) {}
                }
            }
        } finally {}
        
        return model;
    }
    public T create(T model) throws Exception {
        try{
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "INSERT INTO " + model.tableName() + " ( ";
                List<String> ps = model.placeholders();
                for (int i = 0; i < ps.size(); i++) {
                    sql += ps.get(i);
                    if (i != ps.size() - 1) {
                        sql += ", ";
                    }
                }
                sql += ") values (";
                for (int i = 0; i < ps.size(); i++) {
                    sql += "?";
                    if (i != ps.size() - 1) {
                        sql += ", ";
                    } else {
                        sql += ")";
                    }
                }

                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    model.setPlaceHolders(preparedStatement);
                    int count = preparedStatement.executeUpdate();
                    if (count == 0) {
                        throw new Exception("No new row is created");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw new Exception("PostgresCRUD.create: " + e);
        } 
        
        return model;
    }
    public void delete(T model) throws Exception {
        throw new Exception("Not Implemented");
    }
    
}
