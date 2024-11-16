package repository;

import domain.Cake;

import java.io.*;
import java.util.HashMap;

public class CakeRepositoryBinaryFile extends FileRepository<Integer, Cake>{

    public CakeRepositoryBinaryFile(String filename) throws FileNotValidException {
        super(filename);
        readFile();
        writeFile();
    }

    @Override
    protected void readFile() throws FileNotValidException {
        File file = new File(this.filename);

        if (file.length() == 0) { // Check if the file is empty
            this.map = new HashMap<>(); // Initialize an empty map if the file is empty
            return;
        }

        try (FileInputStream fis = new FileInputStream(file); ObjectInputStream ois = new ObjectInputStream(fis)) {
            this.map = (HashMap<Integer, Cake>) ois.readObject();
        } catch (EOFException e) {
            this.map = new HashMap<>(); // Initialize an empty map if end-of-file is reached unexpectedly
        } catch (IOException | ClassNotFoundException e) {
            throw new FileNotValidException("Error reading file: " + e.getMessage());
        }
    }


    @Override
    protected void writeFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.filename)) ) {
            oos.writeObject(this.map);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
