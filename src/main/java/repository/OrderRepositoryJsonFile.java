package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Order;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class OrderRepositoryJsonFile extends FileRepository<Integer, Order> {

    private final ObjectMapper objectMapper;

    public OrderRepositoryJsonFile(String fileName) throws FileNotValidException {
        super(fileName);
        this.objectMapper = new ObjectMapper();
        readFile();
    }

    @Override
    protected void readFile() throws FileNotValidException {
        try {
            File file = new File(this.filename);
            if (!file.exists()) return;
            Map<Integer, Order> loadedData = objectMapper.readValue(file, objectMapper.getTypeFactory().constructMapType(Map.class, Integer.class, Order.class));
            this.map.putAll(loadedData);
        } catch (IOException e) {
            System.out.println("Error loading orders from JSON file: " + e.getMessage());
            throw new FileNotValidException("Failed to read from file: " + filename, e);
        }
    }

    @Override
    protected void writeFile() {
        try {
            objectMapper.writeValue(new File(this.filename), this.map);
        } catch (IOException e) {
            System.out.println("Error saving orders to JSON file: " + e.getMessage());
        }
    }
}
