package rvt.ProduktiDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Produktdb {
    private final Connection connection;

    public Produktdb(String url) {
        try {
            this.connection = DriverManager.getConnection(url);
            initializeDatabase();
        } catch (SQLException e) {
            throw new RuntimeException("Nevar pieslegties databazei: " + e.getMessage(), e);
        }
    }

    private void initializeDatabase() {
        String categories = "CREATE TABLE IF NOT EXISTS categories (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL UNIQUE)";
        String products = "CREATE TABLE IF NOT EXISTS products (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, price REAL NOT NULL, category_id INTEGER NOT NULL, quantity INTEGER NOT NULL, FOREIGN KEY(category_id) REFERENCES categories(id))";
        try (var stmt = connection.createStatement()) {
            stmt.execute(categories);
            stmt.execute(products);
        } catch (SQLException e) {
            throw new RuntimeException("Datubazes inicializacijas kluda: " + e.getMessage(), e);
        }
    }

    public boolean addCategory(String name) {
        return executeUpdate("INSERT INTO categories(name) VALUES(?)", ps -> ps.setString(1, name));
    }

    public List<Category> getAllCategories() {
        return query("SELECT id, name FROM categories", rs -> new Category(rs.getInt("id"), rs.getString("name")));
    }

    public Category getCategoryById(int id) {
        return queryOne("SELECT id, name FROM categories WHERE id = ?", ps -> ps.setInt(1, id), rs -> new Category(rs.getInt("id"), rs.getString("name")));
    }

    public boolean addProduct(String name, double price, int categoryId, int quantity) {
        return executeUpdate("INSERT INTO products(name,price,category_id,quantity) VALUES(?,?,?,?)", ps -> {
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, categoryId);
            ps.setInt(4, quantity);
        });
    }

    public List<Product> getAllProducts() {
        return query("SELECT id, name, price, category_id, quantity FROM products", rs -> new Product(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), rs.getInt("category_id"), rs.getInt("quantity")));
    }

    public List<Product> getProductsByCategory(int categoryId) {
        return query("SELECT id, name, price, category_id, quantity FROM products WHERE category_id = ?", ps -> ps.setInt(1, categoryId), rs -> new Product(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), rs.getInt("category_id"), rs.getInt("quantity")));
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException ignored) {
        }
    }

    private boolean executeUpdate(String sql, SQLConsumer<PreparedStatement> binder) {
        try (var ps = connection.prepareStatement(sql)) {
            binder.accept(ps);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQL kluda: " + e.getMessage());
            return false;
        }
    }

    private <T> List<T> query(String sql, SQLFunction<ResultSet, T> mapper) {
        var list = new ArrayList<T>();
        try (var stmt = connection.createStatement(); var rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapper.apply(rs));
            }
        } catch (SQLException e) {
            System.out.println("SQL kluda: " + e.getMessage());
        }
        return list;
    }

    private <T> List<T> query(String sql, SQLConsumer<PreparedStatement> binder, SQLFunction<ResultSet, T> mapper) {
        var list = new ArrayList<T>();
        try (var ps = connection.prepareStatement(sql)) {
            binder.accept(ps);
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapper.apply(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL kluda: " + e.getMessage());
        }
        return list;
    }

    private <T> T queryOne(String sql, SQLConsumer<PreparedStatement> binder, SQLFunction<ResultSet, T> mapper) {
        try (var ps = connection.prepareStatement(sql)) {
            binder.accept(ps);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapper.apply(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL kluda: " + e.getMessage());
        }
        return null;
    }

    private interface SQLConsumer<T> {
        void accept(T t) throws SQLException;
    }

    private interface SQLFunction<T, R> {
        R apply(T t) throws SQLException;
    }
}
