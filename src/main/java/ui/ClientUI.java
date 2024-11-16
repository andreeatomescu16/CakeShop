package ui;

import config.AppConfiguration;
import domain.Cake;
import service.CakeService;
import service.OrderService;
import ui.CakeUtils;

import java.util.Scanner;

public class ClientUI {

    public static void handleClientOptions(AppConfiguration config, Scanner sc) {
        CakeService service = config.getCakeService();
        OrderService orderService = config.getOrderService();

        System.out.println("Choose an option:");
        System.out.println("1. Make a new order");
        System.out.println("2. Update an order");
        System.out.println("3. Check status");
        System.out.println("4. Cancel order");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                makeNewOrder(sc, service, orderService);
                break;
            case 2:
                updateOrder(sc, service, orderService);
                break;
            case 3:
                checkOrderStatus(sc, orderService);
                break;
            case 4:
                cancelOrder(sc, service, orderService);
                break;
        }
    }

    private static void makeNewOrder(Scanner sc, CakeService service, OrderService orderService) {
        System.out.println("Please enter in the following order: the id, weight, type (chocolate, vanilla, caramel) and your name");
        int id = sc.nextInt();
        int weight = sc.nextInt();
        sc.nextLine();
        String type = sc.nextLine();
        String name = sc.nextLine();

        int price = CakeUtils.calculatePrice(type, weight);
        System.out.println("The price for your order will be: " + price);

        Cake cake = new Cake(id, weight, price, type);
        service.new_order(cake);
        orderService.addOrder(id, false, id, name);
    }

    private static void updateOrder(Scanner sc, CakeService service, OrderService orderService) {
        System.out.println("Please enter in the following order: id, the new weight, the new type (chocolate, vanilla, caramel)");
        int id = sc.nextInt();
        int weight = sc.nextInt();
        sc.nextLine();
        String type = sc.nextLine();

        int price = CakeUtils.calculatePrice(type, weight);
        System.out.println("The price for your order will be: " + price);

        String name = orderService.getOrderByCakeId(id).getName();

        service.cancel_order(id);
        orderService.deleteOrder(id);

        Cake cake = new Cake(id, weight, price, type);
        service.new_order(cake);
        orderService.addOrder(id, false, id, name);
    }

    private static void checkOrderStatus(Scanner sc, OrderService orderService) {
        System.out.println("Enter the order ID:");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println(orderService.getOrderStatus(id) ? "Order is ready" : "Order is not ready");
    }

    private static void cancelOrder(Scanner sc, CakeService service, OrderService orderService) {
        System.out.println("Enter the order ID to cancel:");
        int id = sc.nextInt();
        sc.nextLine();
        service.cancel_order(id);
        orderService.deleteOrder(id);
    }
}
