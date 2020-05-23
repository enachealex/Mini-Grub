package edu.lwtech.csd299.minigrub;

import java.util.*;

public interface DAO<T> {
    public boolean init();
    public int insert(T pojo);
    public void delete(int id);
    public T getByID(int id);
    public T getByIndex(int index);
    public List<T> getAll();
    public List<Integer> getAllIDs();
    public void disconnect();
}
