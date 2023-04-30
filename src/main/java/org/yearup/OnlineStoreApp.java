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
    HashMap<String, Product> productMap = productIdMap();
    HashMap<String, Integer> cartItems = new HashMap<>();


    public void run()
    {
        try
        {
            while (true)
            {
                int selection = displayHomeScreen();

                if (selection == 1)
                {
                    displayAllProducts();
                }
                else if (selection == 2)
                {
                    displayCart();
                }
                else if (selection == 3)
                {
                    System.out.println();
                    System.out.println("Good bye, see you again!");
                    break;
                }
                else
                {
                    System.out.println();
                    System.out.println("Invalid selection");
                }
            }
        }
        catch (Exception e)
        {
            System.out.println();
            System.out.println(e.getMessage());
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
                String productId = columm[0];
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
        finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
        }

        Collections.sort(inventory);
        return inventory;
    }


    private HashMap<String, Product> productIdMap()
    {
        FileReader fileReader;
        BufferedReader reader = null;
        HashMap<String, Product> product = new HashMap<>();

        try
        {
            fileReader = new FileReader("inventory.csv");
            reader = new BufferedReader(fileReader);
            String line;

            while ((line = reader.readLine()) != null)
            {
                String[] columm = line.split("\\|");
                String productId = columm[0];
                String productName = columm[1];
                float productPrice = Float.parseFloat(columm[2]);

                product.put(productId, new Product(productId, productName, productPrice) );
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
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
        System.out.println("\t 1. Shop for products");
        System.out.println("\t 2. Check your cart");
        System.out.println("\t 3. Exit the store");
        System.out.print("Enter your selection: ");

        return scanner.nextInt();
    }


    // display products for shopping and add products to shopping cart
    private void displayAllProducts()
    {
        try
        {
            System.out.println();
            System.out.println("Shopping Center");
            System.out.println("--------------------------------------------------------------------------------------------");
            System.out.println("ID          Product Name                         Price");

            for (Product product : products)
            {
                displayProduct(product);
            }

            System.out.println();
            System.out.println("What you want to do?");
            System.out.println("\t 1. Add item");
            System.out.println("\t 2. Back to home screen");
            System.out.print("Enter your selection: ");
            int selection = scanner.nextInt();
            scanner.nextLine();

            if (selection == 1)
            {
                System.out.print("Enter item ID to add it: ");
                String id = scanner.nextLine().toUpperCase().strip();

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

                    System.out.println();
                    System.out.println("Added Item successfully");

                }
                else
                {
                    System.out.println();
                    System.out.println("Invalid ID");
                    displayAllProducts();
                }
            }
            else if (selection == 2)
            {
                displayHomeScreen();
            }
            else
            {
                System.out.println();
                System.out.println("Invalid selection");
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }


    // display shopping cart for check out
    private void displayCart()
    {
        try
        {
            System.out.println();
            System.out.println("Your Shopping Cart");
            System.out.println("--------------------------------------------------------------------------------------------");
            System.out.println("ID          Product Name                         Price");
            cartList();
            System.out.println();
            System.out.println("What you want to do?");
            System.out.println("\t 1. Check out");
            System.out.println("\t 2. Remove item");
            System.out.println("\t 3. Clear the shopping cart");
            System.out.println("\t 4. Back to shopping");
            System.out.print("Enter your selection: ");
            int selection = scanner.nextInt();
            scanner.nextLine();

            if (selection == 1)
            {
                displayCheckOut();
            }
            else if (selection == 2)
            {
                System.out.print("Enter item ID to remove it: ");
                String id = scanner.nextLine().toUpperCase().strip();

                if (cartItems.containsKey(id))
                {
                    cartItems.remove(id);
                    System.out.println();
                    System.out.println("Removed product successfully");
                }
                else
                {
                    System.out.println();
                    System.out.println("Invalid Id");
                    displayCart();
                }
            }
            else if (selection == 3)
            {
                cartItems.clear();
                System.out.println();
                System.out.println("Cleared shopping cart successfully");
            }
            else if (selection == 4)
            {
                displayAllProducts();
            }
            else
            {
                System.out.println();
                System.out.println("Invalid selection.");
                displayHomeScreen();
            }
        }
        catch (Exception e)
        {
            System.out.println();
            System.out.println(e.getMessage());
        }
    }


    // display check out screen
    private void displayCheckOut()
    {
        try
        {
            // print out list of products in cart
            System.out.println();
            System.out.println("Checking Out");
            System.out.println("--------------------------------------------------------------------------------------------");
            System.out.println("ID          Product Name                         Price");
            cartList();
            System.out.println("--------------------------------------------------------------------------------------------");
            System.out.printf("ToTal:                                          $ %.2f \n", calculation());

            // customer payment
            System.out.println();
            System.out.print("Enter the amount of money: ");
            float money = scanner.nextFloat();
            scanner.nextLine();

            if (money < calculation())
            {
                System.out.println("Return: $ " + money);
                System.out.println("PLease enter enough amount of money to pay");
                displayCheckOut();

            }
            else if (money > calculation())
            {
                float change = money - calculation();

                System.out.println();
                System.out.printf("This is your change: $ %.2f \n", change);
                System.out.println();
                System.out.println("Completed payment!");
                System.out.println();
                System.out.println("Receipt");
                System.out.println("---------------------------------------------------------------");
                System.out.println("ID          Product Name                         Price");
                cartList();
                System.out.println("---------------------------------------------------------------");
                System.out.printf("ToTal:                                          $ %.2f \n", calculation());
                System.out.printf("Payment:                                        $ %.2f \n", money);
                System.out.printf("Change:                                         $ %.2f \n", change);
                System.out.println("Balance:                                        $ 0.00");
                System.out.println("---------------------------------------------------------------");
                System.out.println("                       THANK YOU");

                cartItems.clear();

            }
            else if (money == calculation())
            {
                System.out.println();
                System.out.println("Completed payment!");
                System.out.println();
                System.out.println("Receipt");
                System.out.println("---------------------------------------------------------------");
                System.out.println("ID          Product Name                         Price");
                cartList();
                System.out.println("---------------------------------------------------------------");
                System.out.println("ToTal:                                          $ " + calculation());
                System.out.println("Payment:                                        $ " + money);
                System.out.println("Balance:                                        $ 0.00");
                System.out.println("---------------------------------------------------------------");
                System.out.println("                    THANK YOU");

                cartItems.clear();

            }
            else
            {
                System.out.println("Invalid money input!");
                displayCheckOut();
            }
        }
        catch (Exception e)
        {
            System.out.println();
            System.out.println(e.getMessage());
        }
    }


    // display cart's items list
    private void cartList()
    {
        for (String key : cartItems.keySet())
        {
            Product product = productMap.get(key);
            int quantity = cartItems.get(key);
            displayProduct(product);

            System.out.println("\t\t\t Quantity: " + quantity);
        }
    }


    // calculate total price for check out
    private float calculation()
    {
        float count = 0;
        for (String key : cartItems.keySet())
        {
            int quantity = cartItems.get(key);
            Product product = productMap.get(key);
            count = count + (product.getProductPrice() * quantity);
        }
        return count;
    }


    private void displayProduct(Product products)
    {
        System.out.printf("%-10s %-37s $ %.2f\n", products.getProductId(), products.getProductName(), products.getProductPrice());
    }
}
