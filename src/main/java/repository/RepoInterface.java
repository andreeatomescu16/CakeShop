package repository;

import domain.Identifiable;

import java.util.Iterator;

public interface RepoInterface<Id, T extends Identifiable<Id>> {
    public boolean add(Id id, T entity);
    public boolean delete(Id id);
    public boolean modify(Id id, T entity);
    public T findById(Id id);
    public Iterator<T> get_all();
    public boolean isEmpty();
}
