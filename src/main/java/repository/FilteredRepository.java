package repository;

import domain.Cake;
import filter.AbstractFilter;

import java.util.Iterator;

public class FilteredRepository extends RepoMemory<Integer, Cake> {

    private AbstractFilter<Cake> filter;

    public FilteredRepository(AbstractFilter<Cake> filter) {
        this.filter = filter;
    }

    @Override
    public boolean add(Integer integer, Cake entity) {
        if (filter.accept(entity)) {
            return super.add(integer, entity);
        }
        System.out.println("Doesn't respect");
        return false;
    }

    @Override
    public boolean delete(Integer integer) {
        return super.delete(integer);
    }

    @Override
    public boolean modify(Integer integer, Cake entity) {
        return super.modify(integer, entity);
    }

    @Override
    public Cake findById(Integer integer) {
        return super.findById(integer);
    }

    @Override
    public Iterator<Cake> get_all() {
        return super.get_all();
    }
}
