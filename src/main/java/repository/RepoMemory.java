package repository;

import domain.Identifiable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RepoMemory<ID, T extends Identifiable<ID>> implements RepoInterface<ID, T> {
    protected Map<ID, T> map = new HashMap<>();

    @Override
    public boolean add(ID id, T entity) {
        if (map.containsKey(id)) {
            System.out.println("Id already exists");
            return false;
        }
        map.put(id, entity);
        return true;
    }

    @Override
    public boolean delete(ID id) {
        if (!map.containsKey(id)) {
            System.out.println("Id does not exist?");
            return false;
        }
        map.remove(id);
        return true;
    }

    @Override
    public boolean modify(ID id, T entity) {
        if (!map.containsKey(id)) {
            System.out.println("Id does not exist");
            return false;
        }
        map.put(id, entity);  // Only replace if validation is correct
        return true;
    }


    @Override
    public T findById(ID id) {
        return map.get(id);  // get() returns null if id not found, no try-catch needed
    }

    @Override
    public Iterator<T> get_all() {
        return map.values().iterator();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }
}
