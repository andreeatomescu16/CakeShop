package ui;

import config.AppConfiguration;
import domain.Cake;
import domain.Order;
import service.CakeService;
import service.OrderService;

import java.util.Scanner;
import java.util.Iterator;

public class EmployeeUI {

    public static void handleEmployeeOptions(AppConfiguration config, Scanner sc) {
        OrderService orderService = config.getOrderService();
        CakeService service = config.getCakeService();

        System.out.println("Choose an option:");
        System.out.println("1. Change order status");
        System.out.println("2. Check order status");
        System.out.println("3. Get all orders");

        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            changeOrderStatus(sc, orderService);
        } else if (choice == 2) {
            checkOrderStatus(sc, orderService);
        } else if (choice == 3) {
            getAllOrders(orderService, service);
        }
    }

    private static void changeOrderStatus(Scanner sc, OrderService orderService) {
        System.out.println("Enter the order ID to change status:");
        int id = sc.nextInt();
        sc.nextLine();
        orderService.changeOrderStatus(id);
    }

    private static void checkOrderStatus(Scanner sc, OrderService orderService) {
        System.out.println("Enter the order ID:");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println(orderService.getOrderStatus(id) ? "Order is ready" : "Order is not ready");
    }

    private static void getAllOrders(OrderService orderService, CakeService service) {
        Iterator<Cake> iter = service.get_all();
            while (iter.hasNext())
                System.out.println(iter.next());


        Iterator<Order> iter2 = orderService.getAllOrders();
            while (iter2.hasNext())
                System.out.println(iter2.next());
    }
}
