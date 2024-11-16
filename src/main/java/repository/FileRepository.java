package repository;

import domain.Identifiable;


public abstract class FileRepository <ID, T extends Identifiable<ID>> extends RepoMemory<ID, T> {
    protected String filename;

    FileRepository(String filename)throws FileNotValidException{
        this.filename = filename;
    }

    protected abstract void readFile() throws FileNotValidException;
    protected abstract void writeFile();

    @Override
    public boolean add(ID id, T entity){
        try {
            super.add(id, entity);
            writeFile();
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(ID id){
        try {
            super.delete(id);
            writeFile();
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean modify(ID id, T entity){
        try {
            super.modify(id, entity);
            writeFile();
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
