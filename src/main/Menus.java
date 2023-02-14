/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import tools.MyUtil;

/**
 *
 * @author huyngo
 */
public class Menus {

    public static int choice = 0;

    public static void mainMenu() {
        System.out.println(
                "\nORDER MANAGER\n"
                + "1. Add a pet\n"
                + "2. Find a pet\n"
                + "3. Update a pet\n"
                + "4. Delete a pet\n"
                + "5. Add an order\n"
                + "6. List orders\n"
                + "7. Sort orders\n"
                + "8. Save data\n"
                + "9. Load data\n"
                + "10. Quit\n");
    }

    public static void updateOrderMenu() {
        System.out.println(
                "\n10. Update an Order\n"
                + "\t10.1. Update an Order based on its ID\n"
                + "\t10.2. Delete an Order based on its ID\n "
                + "\tOthers - Back\n");
    }

    public static void getUserChoice() {
        choice = MyUtil.getInt(">> ", "");
    }

}
