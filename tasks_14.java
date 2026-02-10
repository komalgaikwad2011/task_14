package komal1;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Product1 implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private int quantity;
    private double price;

    public Product1(int id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }

    // Setters
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Quantity: " + quantity + ", Price: " + price;
    }
}

public class tasks_14 {
    private static Map<Integer, Product1> inventory = new HashMap<>();
    private static final String FILE_NAME = "inventory.dat";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadInventory();
        while (true) {
            SystemDisplayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            switch (choice) {
                case 1 -> addProduct();
                case 2 -> updateProduct();
                case 3 -> deleteProduct();
                case 4 -> viewProducts();
                case 5 -> displaySummary();
                case 6 -> saveInventory();
                case 7 -> System.exit(0);
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void SystemDisplayMenu() {
        System.out.println("\nInventory Management System");
        System.out.println("1. Add Product");
        System.out.println("2. Update Product");
        System.out.println("3. Delete Product");
        System.out.println("4. View Products");
        System.out.println("5. Display Summary");
        System.out.println("6. Save Inventory");
        System.out.println("7. Exit");
    }

    private static void addProduct() {
        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        if (inventory.containsKey(id)) {
            System.out.println("Product ID already exists!");
            return;
        }
        scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        inventory.put(id, new Product1(id, name, quantity, price));
        System.out.println("Product added!");
    }

    private static void updateProduct() {
        System.out.print("Enter Product ID to update: ");
        int id = scanner.nextInt();
        Product1 product = inventory.get(id);
        if (product == null) {
            System.out.println("Product not found!");
            return;
        }
        System.out.print("Enter new quantity: ");
        product.setQuantity(scanner.nextInt());
        System.out.print("Enter new price: ");
        product.setPrice(scanner.nextDouble());
        System.out.println("Product updated!");
    }

    private static void deleteProduct() {
        System.out.print("Enter Product ID to delete: ");
        int id = scanner.nextInt();
        if (inventory.remove(id) == null) {
            System.out.println("Product not found!");
        } else {
            System.out.println("Product deleted!");
        }
    }

    private static void viewProducts() {
        System.out.println("Inventory:");
        inventory.values().forEach(System.out::println);
    }

    private static void displaySummary() {
        System.out.println("Inventory Summary:");
        System.out.println("Total Products: " + inventory.size());
        inventory.forEach((id, product) -> System.out.println(product));
    }

    private static void saveInventory() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(inventory);
            System.out.println("Inventory saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    private static void loadInventory() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                inventory = (Map<Integer, Product1>) ois.readObject();
                System.out.println("Inventory loaded from file.");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading inventory: " + e.getMessage());
            }
        }
    }
}