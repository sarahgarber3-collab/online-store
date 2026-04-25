package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class StoreApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the online store!");

        homeScreen(scanner);

    }

    public static void homeScreen(Scanner scanner) {
        HashMap<String, Product> cart = new HashMap<>();

        while (true) {
            System.out.println("Select from the following options: ");
            System.out.println("\t1) Display Products");
            System.out.println("\t2) Display Cart");
            System.out.println("\t0) Exit");
            System.out.print("Enter your selection: ");
            int userOption = scanner.nextInt();
            scanner.nextLine();

            switch (userOption) {
                case 1:
                    displayProducts(scanner, cart);
                    formatSpaces();
                    break;
                case 2:
                    displayCart(scanner,cart);
                    formatSpaces();
                    break;
                case 0:
                    System.out.println("Thanks for visiting my online store!!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Incorrect option entered");
                    scanner.nextLine();
                    formatSpaces();
            }


        }

    }

    public static void formatSpaces() {
        System.out.println("\n\n");
    }

    public static void displayProducts(Scanner scanner, HashMap<String, Product> cart) {
        HashMap<String, Product> products = loadInventory();

        boolean run = true;
        while (run) {
            System.out.println("Select from the following options: ");
            System.out.println("\t1) View All Products");
            System.out.println("\t2) Search Products");
            System.out.println("\t3) Add Product");
            System.out.println("\t0) Go Back");
            System.out.print("Enter your selection: ");
            int userOption = scanner.nextInt();
            scanner.nextLine();

            switch (userOption) {
                case 1:
                    for (Product p : products.values()) {
                        System.out.printf("%s: %s: $%.2f%n", p.getSku(), p.getProductName(), p.getPrice());
                    }
                    formatSpaces();
                    break;
                case 2:
                    System.out.print("What product are you looking for today?: ");
                    String userInput = scanner.nextLine();
                    for (Product p : products.values()) {
                        if (p.getProductName().toLowerCase().contains(userInput.toLowerCase())) {
                            System.out.printf("%s: %s: $%.2f%n", p.getSku(), p.getProductName(), p.getPrice());
                        }
                    }
                    formatSpaces();
                    break;
                case 3:
                    System.out.print("Enter product by sku: ");
                    String sku = scanner.nextLine();
                    Product product = products.get(sku);
                    if (product != null) {
                        cart.put(sku, product);
                    }
                    if (product == null) {
                        System.out.println("No such product here.");
                    }

                    break;
                case 0:
                    formatSpaces();
                    run = false;
                    break;
                default:
                    System.out.println("Incorrect option entered (press ENTER to continue)");
                    scanner.nextLine();
                    formatSpaces();
            }
        }

    }
    public static void displayCart(Scanner scanner, HashMap<String, Product> cart) {
        double total = 0;
        for (Product p : cart.values()) {
            System.out.printf("%s: %s: $%.2f%n", p.getSku(), p.getProductName(), p.getPrice());
            total += p.getPrice();
        }
        System.out.printf("Total: $%.2f%n", total);

        boolean run = true;
        while (run) {
            System.out.println("Select from the following options: ");
            System.out.println("\t1) Check Out Products");
            System.out.println("\t2) Remove Products");
            System.out.println("\t0) Go Back");
            System.out.print("Enter your selection: ");
            int userOption = scanner.nextInt();
            scanner.nextLine();

            switch (userOption) {
                case 1:
                    System.out.println("thank you for shopping with us today!");
                    cart.clear();
                    formatSpaces();
                    run = false;
                    break;
                case 2:
                    System.out.print("What product are you wanting to remove today(please enter sku)?: ");
                    String userInput = scanner.nextLine();
                    cart.remove(userInput);
                    System.out.println("Product removed from cart");
                    formatSpaces();
                    break;
                case 0:
                    formatSpaces();
                    run = false;
                    break;
                default:
                    System.out.println("Incorrect option entered (press ENTER to continue)");
                    scanner.nextLine();
                    formatSpaces();


            }

            public static HashMap<String, Product> loadInventory () {
                HashMap<String, Product> products = new HashMap<>();
                try {
                    BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/products.csv"));
                    String productItem;
                    bufReader.readLine();
                    while ((productItem = bufReader.readLine()) != null) {
                        String[] splitProductItem = productItem.split("\\|");
                        String sku = splitProductItem[0];
                        String productName = splitProductItem[1];
                        double price = Double.parseDouble(splitProductItem[2]);
                        String department = splitProductItem[3];

                        Product product = new Product(sku, productName, department, price);
                        products.put(sku, product);


                    }
                    bufReader.close();

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return products;
            }


        }
    }
    }