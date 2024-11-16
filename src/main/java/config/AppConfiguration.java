package config;

import domain.Cake;
import domain.Order;
import filter.FilterCakesByWeight;
import repository.*;
import service.CakeService;
import service.OrderService;
import validator.CakeValidator;
import validator.OrderValidator;
import validator.ValidatorInterface;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfiguration {
    private final FilteredRepository filteredRepo;
    private CakeService cakeService;
    private OrderService orderService;

    public AppConfiguration() {
        filteredRepo = new FilteredRepository(new FilterCakesByWeight(100));
        ValidatorInterface<Cake> cakeValidator = new CakeValidator();
        OrderValidator orderValidator = new OrderValidator();

        RepoMemory<Integer, Cake> cakeRepo = new RepoMemory<>();
        RepoMemory<Integer, Order> orderRepo = new RepoMemory<>();

        cakeService = null;
        orderService = null;

        final String SETTINGS_FILE = "settings.properties";
        Properties prop = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(SETTINGS_FILE)) {
            if (input == null) {
                throw new RuntimeException("Could not find " + SETTINGS_FILE);
            }

            // Load properties from the settings.properties file
            prop.load(input);
            String repoType = prop.getProperty("repo_type");
            String repoPath = prop.getProperty("repo_path");
            String repoPath2 = prop.getProperty("repo_path2");

            // Configure repositories based on repo_type
            switch (repoType.toLowerCase()) {
                case "inmemory":
                    cakeRepo = new CakeShop();
                    orderRepo = new OrderShop();
                    break;
                case "binary":
                    cakeRepo = new CakeRepositoryBinaryFile(repoPath);
                    orderRepo = new OrderShop();
                    break;
                case "text":
                    cakeRepo = new CakeRepositoryTextFile(repoPath);
                    orderRepo = new OrderShop();
                    break;
                case "db":
                    System.out.println(repoPath);
                    cakeRepo = new CakeRepositoryDB(repoPath);
                    orderRepo = new OrderRepositoryDB(repoPath);
                    break;
                case "xml":
                    cakeRepo = new CakeRepositoryXmlFile(repoPath);
                    orderRepo = new OrderRepositoryXmlFile(repoPath2);
                    break;
                case "json":

                    cakeRepo = new CakeRepositoryJsonFile(repoPath);
                    orderRepo = new OrderRepositoryJsonFile(repoPath2);
                    break;
                default:
                    throw new RuntimeException("Unknown repository type specified in settings.properties: " + repoType);
            }

            // Initialize services with the configured repositories
            cakeService = new CakeService(cakeRepo);
            orderService = new OrderService(orderRepo, cakeRepo, orderValidator);

        } catch (IOException e) {
            throw new RuntimeException("Error loading configuration from " + SETTINGS_FILE, e);
        } catch (FileNotValidException e) {
            throw new RuntimeException("File format error for repository file: " + e.getMessage(), e);
        }
    }

    public CakeService getCakeService() {
        return cakeService;
    }

    public OrderService getOrderService() {
        return orderService;
    }
}
