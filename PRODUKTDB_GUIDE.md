# Product & Category Management System - Complete Guide

## Overview
This is a Java application that manages **Products** and **Categories** using **SQLite database**. The system includes a console-based user interface (UI) and uses **prepared statements** to prevent SQL injection attacks.

---

## Project Structure

```
ProduktiDB/
├── Main.java              # Application entry point
├── Produktdb.java         # Database operations (CRUD)
├── Product.java           # Product model class
├── Category.java          # Category model class
└── UserInterface.java     # Console menu interface
```

---

## Classes Explanation

### 1. **Product.java** (Model Class)
Represents a product in the system with attributes:
- `id` - Unique product identifier
- `name` - Product name
- `price` - Product price
- `categoryId` - Foreign key linking to category
- `quantity` - Stock quantity

**Key Methods:**
- `Product(id, name, price, categoryId, quantity)` - Constructor with ID
- `Product(name, price, categoryId, quantity)` - Constructor without ID
- Getters and setters for all properties
- `toString()` - Display product information

---

### 2. **Category.java** (Model Class)
Represents a product category with attributes:
- `id` - Unique category identifier
- `name` - Category name

**Key Methods:**
- `Category(id, name)` - Constructor with ID
- `Category(name)` - Constructor without ID
- Getters and setters
- `toString()` - Display category information

---

### 3. **Produktdb.java** (Database Layer)
Handles all database operations with **prepared statements** (prevents SQL injection).

**Constructor:**
```java
Produktdb(String databaseUrl)
```
- Creates connection to SQLite
- Automatically creates tables if they don't exist

**Category Operations:**
| Method | Purpose |
|--------|---------|
| `addCategory(String name)` | Add new category |
| `getAllCategories()` | Retrieve all categories |
| `getCategoryById(int id)` | Get specific category |
| `updateCategory(int id, String name)` | Modify category |
| `deleteCategory(int id)` | Remove category |

**Product Operations:**
| Method | Purpose |
|--------|---------|
| `addProduct(name, price, categoryId, quantity)` | Add new product |
| `getAllProducts()` | Retrieve all products |
| `getProductById(int id)` | Get specific product |
| `getProductsByCategory(int categoryId)` | Get products in category |
| `updateProduct(id, name, price, categoryId, quantity)` | Modify product |
| `deleteProduct(int id)` | Remove product |
| `close()` | Close database connection |

**Important:** All SQL queries use **prepared statements** with `?` placeholders to safely inject user input and prevent SQL injection attacks.

Example:
```java
String sql = "INSERT INTO categories (name) VALUES (?)";
var pstmt = connection.prepareStatement(sql);
pstmt.setString(1, name);  // Safe parameter binding
pstmt.executeUpdate();
```

---

### 4. **UserInterface.java** (Console Menu)
Provides interactive console menu for users.

**Main Menu:**
```
1. Manage Categories
2. Manage Products
3. Exit
```

**Category Menu Options:**
- Add category
- View all categories
- Update category
- Delete category

**Product Menu Options:**
- Add product
- View all products
- View products by category
- Update product
- Delete product

All inputs are validated:
- Non-empty strings
- Valid numeric inputs
- Existence checks before update/delete

---

### 5. **Main.java** (Entry Point)
```java
public static void main(String[] args) {
    String dbUrl = "jdbc:sqlite:produktdb.db";
    
    Produktdb db = new Produktdb(dbUrl);
    UserInterface ui = new UserInterface(db);
    ui.start();
    
    db.close();
}
```

Steps:
1. Creates/connects to `produktdb.db` SQLite database
2. Initializes database tables
3. Starts the console UI
4. Closes connection when done

---

## Database Schema

### Categories Table
```sql
CREATE TABLE categories (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL UNIQUE
)
```

### Products Table
```sql
CREATE TABLE products (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    price REAL NOT NULL,
    category_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories(id)
)
```

---

## How to Use

### Running the Application
1. Compile all Java files
2. Run `Main.java`
3. The `produktdb.db` file will be created automatically in the project directory

### Example Usage Flow

```
1. Select "Manage Categories"
   → Add "Electronics" category
   
2. Select "Manage Products"
   → Add "Laptop" with price 999.99, category ID 1, quantity 10
   
3. View all products
   → See the laptop in the list
   
4. Update/Delete as needed
```

---

## Security Features

### SQL Injection Prevention
The application uses **prepared statements** instead of string concatenation:

❌ **UNSAFE** (vulnerable to SQL injection):
```java
String sql = "INSERT INTO categories (name) VALUES ('" + name + "')";
stmt.execute(sql);
```

✅ **SAFE** (protected):
```java
String sql = "INSERT INTO categories (name) VALUES (?)";
pstmt.setString(1, name);  // Parameter binding
pstmt.executeUpdate();
```

Prepared statements ensure:
- User input is properly escaped
- Attackers cannot inject SQL commands
- Database integrity is maintained

---

## Error Handling

- **SQL Exceptions**: Caught and logged with descriptive messages
- **Input Validation**: 
  - Empty strings rejected
  - Invalid numbers handled
  - Foreign key constraints checked
- **User-Friendly Messages**: Clear feedback for success/failure

---

## Key Features

✅ **CRUD Operations** - Create, Read, Update, Delete for products and categories  
✅ **Data Validation** - Input verification before database operations  
✅ **SQL Injection Protection** - Prepared statements throughout  
✅ **Relationship Management** - Categories linked to products via foreign keys  
✅ **Console Interface** - Easy-to-use menu-driven system  
✅ **Auto-Initialization** - Tables created automatically on first run  
✅ **Clean Code** - Well-organized, commented, follows OOP principles  

---

## Potential Enhancements

1. Add search functionality (search products by name/price range)
2. Add sorting options (sort by price, quantity)
3. Add reports/statistics (total inventory value, stock alerts)
4. Add user authentication
5. Add data export (CSV, PDF)
6. Add graphical UI instead of console
7. Add transaction support for multiple operations
8. Add product image support

---

## Testing the System

**Test Scenario 1: Basic CRUD**
1. Add category "Books"
2. Add product "Java Programming" in Books category
3. View all products
4. Update product price
5. Delete product

**Test Scenario 2: SQL Injection Prevention**
Try entering malicious input like `'; DROP TABLE products; --` 
The system will safely reject or escape it due to prepared statements

---

## Troubleshooting

| Issue | Solution |
|-------|----------|
| Database not created | Ensure write permissions in project directory |
| Foreign key errors | Make sure category ID exists before adding product |
| Input errors | Follow numeric format prompts |
| Connection errors | Check database file permissions |

---

## Summary

This is a complete, production-ready product management system that demonstrates:
- Object-oriented programming with model classes
- Database design with relationships
- JDBC for Java-SQL integration
- SQL injection prevention best practices
- User input validation
- Exception handling
- Clean separation of concerns (UI, Database, Models)

The system is ready to extend with more features as needed!
