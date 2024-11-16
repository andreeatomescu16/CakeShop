package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import domain.Cake;
import repository.RepoInterface;

public class CakeRepositoryDB extends RepoMemory<Integer, Cake> {
    private String url;

    public CakeRepositoryDB(String url) {
        this.url = url;
    }

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url);
    }

    @Override
    public boolean add(Integer id, Cake cake) {
        String sql = "INSERT INTO Cake(id, weight, price, type) VALUES(?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.setInt(2, cake.getWeight());
            pstmt.setInt(3, cake.getPrice());
            pstmt.setString(4, cake.getType());
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM Cake WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);  // Set the id to delete the corresponding record

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return true; // Successfully deleted
            } else {
                System.out.println("No record found with the specified ID.");
                return false; // No rows deleted, meaning the ID was not found
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean modify(Integer id, Cake cake) {
        String sql = "UPDATE Cake SET weight = ?, price = ?, type = ? WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, cake.getWeight());
            pstmt.setInt(2, cake.getPrice());
            pstmt.setString(3, cake.getType());
            pstmt.setInt(4, id);  // Setting the ID to locate the correct record

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return true; // Successfully updated
            } else {
                System.out.println("No record found with the specified ID.");
                return false; // No rows updated, meaning the ID was not found
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Cake findById(Integer integer) {
        String sql = "SELECT * FROM Cake";
        Cake id_cake = null;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cake cake = new Cake(rs.getInt("id"), rs.getInt("weight"),
                        rs.getInt("price"), rs.getString("type"));
                if (Objects.equals(cake.getId(), integer)) {
                    id_cake = cake;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return id_cake;
    }

    @Override
    public Iterator<Cake> get_all() {
        String sql = "SELECT * FROM Cake";
        List<Cake> cakes = new ArrayList<>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cake cake = new Cake(rs.getInt("id"), rs.getInt("weight"),
                        rs.getInt("price"), rs.getString("type"));
                cakes.add(cake);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return cakes.iterator();
    }
}
