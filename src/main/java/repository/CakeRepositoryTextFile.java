package repository;

import domain.Cake;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.util.Iterator;

public class CakeRepositoryTextFile extends FileRepository<Integer, Cake> {

    public CakeRepositoryTextFile(String filename) throws FileNotValidException {
        super(filename);
        readFile();
        writeFile();
    }

    @Override
    protected void readFile() throws FileNotValidException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename)))
        {
            String line;
            while((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                if(tokens.length != 4){
                    throw new FileNotValidException("File not valid!");
                }
                else{
                    Cake d = new Cake(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), tokens[3]);
                    map.put(d.getId(), d);
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void writeFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {

            Iterator<Cake> it = get_all();
            while(it.hasNext()) {
                Cake cake = it.next();
                bw.write(cake.getId() + ","+ cake.getWeight()+","+cake.getPrice() + ","+cake.getType()+'\n');
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

