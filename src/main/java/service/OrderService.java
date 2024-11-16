package service;

import domain.Cake;
import domain.Order;
import repository.RepoInterface;
import validator.OrderValidator;

import java.util.Iterator;

public class OrderService {
    private RepoInterface<Integer, Order> orderRepo;
    private RepoInterface<Integer, Cake> cakeRepo;
    private OrderValidator orderValidator;

    public OrderService(RepoInterface<Integer, Order> orderRepo, RepoInterface<Integer, Cake> cakeRepo, OrderValidator orderValidator) {
        this.orderRepo = orderRepo;
        this.cakeRepo = cakeRepo;
        this.orderValidator = orderValidator;
    }

    public boolean addOrder(Integer id, boolean status, Integer cakeId, String name) {
        Cake cake = cakeRepo.findById(cakeId);
        try {
            Order order = new Order(id, status, cake, name);
            orderValidator.validate(order);
            return orderRepo.add(id, order);
        }
        catch (Exception e) {
            System.out.println("Invalid order");
            return false;
        }
    }

    public boolean deleteOrder(Integer id) {
        return orderRepo.delete(id);
    }

    public boolean modifyOrder(Integer id, boolean status, Integer cakeId, String name) {
        Cake cake = cakeRepo.findById(cakeId);
        try {
            Order order = new Order(id, status, cake, name);
            orderValidator.validate(order);
            return orderRepo.modify(id, order);
        }
        catch (Exception e)
        {
            System.out.println("Invalid order");
            return false;
        }
    }

    public Order findOrder(Integer id) {
        return orderRepo.findById(id);
    }

    public Iterator<Order> getAllOrders() {
        return orderRepo.get_all();
    }

    public boolean changeOrderStatus(Integer id) {
        Order order = orderRepo.findById(id);
        if (order == null) {
            return false;
        }
        return order.changeStatus();
    }

    public boolean getOrderStatus(Integer id) {
        Order order = orderRepo.findById(id);
        if (order == null) {
            return false;
        }
        return order.getStatus();
    }

    public Order getOrderByCakeId(Integer cakeId) {
        Iterator<Order> orders = orderRepo.get_all();
        while (orders.hasNext()) {
            Order order = orders.next();
            if (order.getCake().getId().equals(cakeId)) {
                return order;
            }
        }
        return null;
    }
}
