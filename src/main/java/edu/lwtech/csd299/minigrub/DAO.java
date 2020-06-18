//Owned by Seth Peterson
package edu.lwtech.csd299.minigrub;

import java.util.*;

public interface DAO<T> {
    //public boolean init(String jdbc, String user, String password, String driver);
    public boolean init();
    public int insert(T pojo);
    public void delete(int id);
    public T getByID(int id);
    public List<T> getAll();
    public List<Integer> getAllIDs();
    public T search(String keyword);
    public int size();
    public boolean update(T item);
    public void disconnect();
}
