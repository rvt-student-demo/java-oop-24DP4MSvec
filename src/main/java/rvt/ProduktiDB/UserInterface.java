package rvt.ProduktiDB;

import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private final Produktdb db;
    private final Scanner scanner = new Scanner(System.in);

    public UserInterface(Produktdb db) {
        this.db = db;
    }

    public void start() {
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> addCategory();
                case "2" -> addProduct();
                case "3" -> showCategories();
                case "4" -> showProducts();
                case "5" -> productsByCategory();
                case "6" -> {
                    System.out.println("Iziet no programmas.");
                    return;
                }
                default -> System.out.println("Nederiga izvele, megini velreiz.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n1 - Pievienot kategoriju");
        System.out.println("2 - Pievienot produktu");
        System.out.println("3 - Paradit visas kategorijas");
        System.out.println("4 - Paradit visus produktus");
        System.out.println("5 - Meklet produktus pec kategorijas");
        System.out.println("6 - Iziet");
        System.out.print("Izvele: ");
    }

    private void addCategory() {
        System.out.print("Kategorijas nosaukums: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Nosaukums nevar but tuks.");
            return;
        }
        System.out.println(db.addCategory(name) ? "Kategorija pievienota." : "Neizdevas pievienot kategoriju.");
    }

    private void addProduct() {
        System.out.print("Produkta nosaukums: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Nosaukums nevar but tuks.");
            return;
        }
        double price = parseDouble("Cena: ");
        if (price < 0) return;
        int categoryId = parseInt("Kategorijas ID: ");
        if (categoryId < 0 || db.getCategoryById(categoryId) == null) {
            System.out.println("Kategorija neeksiste.");
            return;
        }
        int quantity = parseInt("Daudzums: ");
        if (quantity < 0) return;
        System.out.println(db.addProduct(name, price, categoryId, quantity) ? "Produkts pievienots." : "Neizdevas pievienot produktu.");
    }

    private void showCategories() {
        List<Category> categories = db.getAllCategories();
        if (categories.isEmpty()) {
            System.out.println("Nav kategoriju.");
            return;
        }
        categories.forEach(System.out::println);
    }

    private void showProducts() {
        List<Product> products = db.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("Nav produktu.");
            return;
        }
        products.forEach(System.out::println);
    }

    private void productsByCategory() {
        int categoryId = parseInt("Kategorijas ID: ");
        if (categoryId < 0 || db.getCategoryById(categoryId) == null) {
            System.out.println("Kategorija neeksiste.");
            return;
        }
        List<Product> products = db.getProductsByCategory(categoryId);
        if (products.isEmpty()) {
            System.out.println("Nav produktu saja kategorija.");
            return;
        }
        products.forEach(System.out::println);
    }

    private int parseInt(String label) {
        System.out.print(label);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Ievaditais skaitlis nav pareizs.");
            return -1;
        }
    }

    private double parseDouble(String label) {
        System.out.print(label);
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Ievadita summa nav pareiza.");
            return -1;
        }
    }
}
