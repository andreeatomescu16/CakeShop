package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Cake;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CakeRepositoryJsonFile extends FileRepository<Integer, Cake> {

    private final ObjectMapper objectMapper; // Initialize here

    public CakeRepositoryJsonFile(String fileName) throws FileNotValidException {
        super(fileName);
        this.objectMapper = new ObjectMapper();
        readFile();
    }

    @Override
    protected void readFile() throws FileNotValidException {
        try {
            File file = new File(this.filename);
            if (!file.exists()) return;
            Map<Integer, Cake> loadedData = objectMapper.readValue(file, objectMapper.getTypeFactory().constructMapType(Map.class, Integer.class, Cake.class));
            this.map.putAll(loadedData);
        } catch (IOException e) {
            System.out.println("Error loading cars from JSON file: " + e.getMessage());
        }
    }

    @Override
    protected void writeFile() {
        try {
            objectMapper.writeValue(new File(this.filename), this.map);
        } catch (IOException e) {
            System.out.println("Error saving cars to JSON file: " + e.getMessage());
        }
    }
}

