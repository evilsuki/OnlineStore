package org.yearup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class OnlineStoreApp
{
    Scanner scanner = new Scanner(System.in);
    ArrayList<Product> products = loadInventory();
    HashMap<Integer, Product> productMap = productIdMap();

    HashMap<Integer, Integer> cartItems = new HashMap<>();

    public void run()
    {
        while (true)
        {
            int selection = displayHomeScreen();

            switch (selection)
            {
                case 1 -> displayAllProducts();
                case 2 -> displayCart();
                case 3 ->
                {
                    return;
                }
            }
        }
    }

    // arraylist for load inventory
    private ArrayList<Product> loadInventory()
    {
        FileReader fileReader;
        BufferedReader reader = null;
        ArrayList<Product> inventory = new ArrayList<>();

        try
        {
            fileReader = new FileReader("inventory.csv");
            reader = new BufferedReader(fileReader);
            String line;

            while ((line = reader.readLine()) != null)
            {
                String[] columm = line.split("\\|");
                int productId = Integer.parseInt(columm[0]);
                String productName = columm[1];
                float productPrice = Float.parseFloat(columm[2]);

                Product product = new Product(productId, productName, productPrice);
                inventory.add(product);

            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        Collections.sort(inventory);
        return inventory;
    }


    private HashMap<Integer, Product> productIdMap()
    {
        FileReader fileReader;
        BufferedReader reader = null;
        HashMap<Integer, Product> product = new HashMap<>();

        try
        {
            fileReader = new FileReader("inventory.csv");
            reader = new BufferedReader(fileReader);
            String line;

            while ((line = reader.readLine()) != null)
            {
                String[] columm = line.split("\\|");
                int productId = Integer.parseInt(columm[0]);
                String productName = columm[1];
                float productPrice = Float.parseFloat(columm[2]);

                product.put(productId, new Product(productId, productName, productPrice) );
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        return product;
    }


    // home screen display
    private int displayHomeScreen()
    {
        System.out.println();
        System.out.println("The Store Home Screen");
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("What you like to do?");
        System.out.println("\t 1. Shop for products.");
        System.out.println("\t 2. Check your cart.");
        System.out.println("\t 3. Exit the store.");
        System.out.print("Enter your selection: ");

        return scanner.nextInt();
    }


    // display products for shopping and add products to shopping cart
    private void displayAllProducts()
    {
        System.out.println();
        System.out.println("Shopping Center");
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("ID          Product Name                         Price");

        for (Product p : products)
        {
            displayProduct(p);
        }

       System.out.println();
       System.out.print("Enter id number to add item to cart or Enter X to go back home screen: ");
       String selection = scanner.nextLine().strip();

       if (selection.equalsIgnoreCase("x"))
       {
           return;
       }

       int id = Integer.parseInt(selection);

       if (productMap.containsKey(id))
       {
           if (cartItems.containsKey(id))
           {
               int quantity = cartItems.get(id) + 1;
               cartItems.put(id, quantity);
           }
           else
           {
               cartItems.put(id, 1);
           }
       }
    }


    // display shopping cart for check out
    private void displayCart()
    {
        System.out.println();
        System.out.println("Your Shopping Cart");
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("ID          Product Name                         Price");

        for (int productId : cartItems.keySet())
        {
            Product product = productMap.get(productId);
            int quantity = cartItems.get(productId);

            displayProduct(product);
            System.out.print("\t\t\t Quantity: " + quantity);
        }

    }


    private void displayProduct(Product products)
    {
        System.out.printf("%-10d %-37s $ %.2f\n", products.getProductId(), products.getProductName(), products.getProductPrice());
    }


}
