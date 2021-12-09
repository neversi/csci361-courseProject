package hotel.repository;

import java.util.List;

public interface ICRUDRepository<T> {
    public List<T> getList(T model);
    public T getById(T model);
    public T update(T model) throws Exception;
    public void delete(T model) throws Exception;
    public T create(T model) throws Exception;
    public List<T> getListByParam(T model, String param);
    public T getOneByParam(T model, String param);
}
