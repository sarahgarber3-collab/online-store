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
                    displayProducts(scanner);
                    formatSpaces();
                    break;
                case 2:
                    //displayCart();
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

    public static void displayProducts(Scanner scanner) {
        HashMap<String,Product> products = loadInventory();
        for(Product p : products.values()) {
            System.out.printf("%s: %.2f%n", p.getProductName(),p.getPrice());
        }

    }
    public static HashMap<String, Product> loadInventory() {
         HashMap<String,Product> products = new HashMap<>();
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

                Product product = new Product(sku,productName,department,price);
                products.put(sku,product);


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
