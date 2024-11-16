package service;

import domain.Cake;
import repository.RepoMemory;
import validator.CakeValidator;

import java.util.Iterator;

public class CakeService {

    private RepoMemory<Integer, Cake> my_repo = new RepoMemory<>();
    private int cakeIdCounter;
    private final CakeValidator cakeValidator = new CakeValidator();

    public CakeService(RepoMemory<Integer, Cake> repo) {
        this.my_repo = repo;
        this.cakeIdCounter = 0;
    }

    public boolean new_order(Cake new_cake) {
        try {
            cakeValidator.validate(new_cake);
            boolean result = my_repo.add(new_cake.getId(), new_cake);
            if (result) {
                System.out.println("Cake added successfully with ID: " + new_cake.getId());
            }
            return result;
        } catch (Exception e) {
            System.out.println("Invalid cake: " + e.getMessage());
            return false;
        }
    }

    public boolean cancel_order(int id) {
        return my_repo.delete(id);
    }


    public boolean update_order(int id, Cake new_cake) {

        try {
            cakeValidator.validate(new_cake);
            return my_repo.modify(id, new_cake);
        } catch (Exception e) {
            System.out.println("Invalid cake");
            return false;
        }
    }

    public Iterator<Cake> get_all() {
        return my_repo.get_all();
    }

    public RepoMemory<Integer, Cake> get_repo() {
        return my_repo;
    }
}
