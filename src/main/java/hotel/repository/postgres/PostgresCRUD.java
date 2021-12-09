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
                while(model.readResultSet(resultSet)){
                    T newModel = (T) model.clone();
                    objects.add(newModel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } 
        
        return objects;
    }

    @SuppressWarnings("unchecked")
    public List<T> getListByParam(T model, String param) {
        ArrayList<T> objects = new ArrayList<>();

        try{
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "SELECT * FROM "  + model.tableName() + " WHERE " + param + " = ?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    model.setPlaceHolders(preparedStatement);
                    ResultSet result = preparedStatement.executeQuery();
                    while(model.readResultSet(result)){
                        T newModel = (T) model.clone();
                        objects.add(newModel);
                    }
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
                String sql = "SELECT * FROM "  + model.tableName() + " WHERE " + model.pKey()[0] + " = ?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    model.setPlaceHolders(preparedStatement);
                    ResultSet result = preparedStatement.executeQuery();
                    if(!model.readResultSet(result)) {
                        return null;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } 
        
        return model;
    }

    public T getOneByParam(T model, String param) {
        
        try{
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "SELECT * FROM "  + model.tableName() + " WHERE " + param + " = ?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    model.setPlaceHolders(preparedStatement);
                    ResultSet result = preparedStatement.executeQuery();
                    if(!model.readResultSet(result)) {
                        return null;
                    }
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
                String sql = "UPDATE " + model.tableName() + " SET ";
                for (String p : model.placeholders()) {
                    boolean isKey = false;
                    for (String k : model.pKey()) {
                        if (p.equals(k)) {
                            break;
                        }
                    }
                    if (isKey) { continue; }

                    sql += p + " = ?, ";
                }
                sql = sql.substring(0, sql.length() - 2);
                sql += " WHERE ";
                for (String p : model.pKey()) {
                    sql += p + " = ? and ";
                }
                sql = sql.substring(0, sql.length() - 5);
                
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    model.setPlaceHolders(preparedStatement);
                    int count = preparedStatement.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("PostgresCRUD.update: " + e.toString());
        }
        
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
        try{
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                  
                String sql = "DELETE FROM " + model.tableName() + " WHERE ";
                for (String p : model.pKey()) {
                    sql += p + " = ? and ";
                }
                sql = sql.substring(0, sql.length() - 5);

                System.out.println(sql);

                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    model.setPlaceHolders(preparedStatement);
                    preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            throw new Exception("PostgresCRUD.delete: " + ex.toString());
        }
    }
    
}
