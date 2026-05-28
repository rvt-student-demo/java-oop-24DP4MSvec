package rvt.Todolistik;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TodoDB {

    private static final String DB_URL = "jdbc:sqlite:todo.db";

    public TodoDB() {
        initSchema();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private void initSchema() {
        String sql = "CREATE TABLE IF NOT EXISTS todo ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "task TEXT NOT NULL)";

        try (
            Connection conn = connect();
            Statement stmt = conn.createStatement()
        ) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Schema init failed: "
                    + e.getMessage());
        }
    }

    public void add(String task) {
        String sql = "INSERT INTO todo(task) VALUES (?)";
        try (
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, task);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add task: " + e.getMessage(), e);
        }
    }

    public List<TodoItem> findAll() {
        String sql = "SELECT id, task FROM todo ORDER BY id";
        List<TodoItem> items = new ArrayList<>();

        try (
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()
        ) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String task = rs.getString("task");
                items.add(new TodoItem(id, task));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read tasks: " + e.getMessage(), e);
        }

        return items;
    }

    public boolean removeById(int id) {
        String sql = "DELETE FROM todo WHERE id = ?";
        try (
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove task: " + e.getMessage(), e);
        }
    }
}