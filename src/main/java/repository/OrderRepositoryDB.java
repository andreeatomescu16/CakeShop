package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import domain.Cake;
import domain.Order;
import repository.RepoInterface;

public class OrderRepositoryDB extends RepoMemory<Integer, Order> implements RepoInterface<Integer, Order> {
    private String url;

    public OrderRepositoryDB(String url) {
        this.url = url;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url);
    }

    @Override
    public boolean add(Integer id, Order order) {
        String sql = "INSERT INTO CakeOrder(order_id, status, cake_id, name) VALUES(?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.setBoolean(2, order.getStatus()); // Assuming status is a boolean field
            pstmt.setInt(3, order.getCakeId()); // Assuming cake_id is a foreign key referring to Cake's id
            pstmt.setString(4, order.getName());
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM CakeOrder WHERE order_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean modify(Integer id, Order order) {
        String sql = "UPDATE CakeOrder SET status = ?, cake_id = ?, name = ? WHERE order_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBoolean(1, order.getStatus());
            pstmt.setInt(2, order.getCakeId());
            pstmt.setString(3, order.getName());
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Order findById(Integer id) {
        String sql = "SELECT * FROM CakeOrder WHERE order_id = ?";
        Order order = null;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Cake cake = new CakeRepositoryDB(url).findById(rs.getInt("cake_id"));
                order = new Order(rs.getInt("order_id"), rs.getBoolean("status"),
                        cake, rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return order;
    }

    @Override
    public Iterator<Order> get_all() {
        String sql = "SELECT * FROM CakeOrder";
        List<Order> orders = new ArrayList<>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cake cake = new CakeRepositoryDB(url).findById(rs.getInt("cake_id"));
                Order order = new Order(rs.getInt("order_id"), rs.getBoolean("status"),
                        cake, rs.getString("name"));
                orders.add(order);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return orders.iterator();
    }
}
