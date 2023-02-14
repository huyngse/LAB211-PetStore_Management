/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import dataStructure.OrderList;
import dataStructure.PetList;
import entity.OrderHeader;
import tools.MyUtil;

/**
 *
 * @author huyngo
 */
public class PetStoreManagement {

    static PetList petList = new PetList();
    static OrderList orderList = new OrderList();

    public static void main(String[] args) {
        orderList.setPetList(petList);
        petList.setOrderList(orderList);
        petList.readFromFile();
        orderList.readFromFile();

        boolean isChanged = false;
        do {
            Menus.mainMenu();
            Menus.choice = MyUtil.getInt(">> ", 1, 10, "");
            switch (Menus.choice) {
                case 1: { //1.	Add a pet
                    petList.addPet();
                    isChanged = true;
                    break;
                }
                case 2: {//2.	Find a pet
                    petList.findPet();
                    break;
                }
                case 3: {//3.	Update a pet
                    if (petList.updatePet()) {
                        isChanged = true;
                    }
                    break;
                }
                case 4: {//4.	Delete a pet
                    if (petList.deletePet()) {
                        isChanged = true;
                    }
                    break;
                }
                case 5: {//5.	Add an order
                    orderList.addOrder();
                    isChanged = true;
                    break;
                }
                case 6: {//6.	List orders
                    orderList.listOrder();
                    break;
                }
                case 7: {//7.	Sort orders
                    orderList.sortOrder();
                    break;
                }
                case 8: {//8.	Save data
                    petList.saveFile();
                    orderList.saveFile();
                    isChanged = false;
                    break;
                }
                case 9: {//9.	Load data
                    petList.readFromFile();
                    orderList.readFromFile();
                    break;
                }
                case 10: {
                    if (MyUtil.getString("Do you want to exist the program? Y/N\n>> ", "").equalsIgnoreCase("Y")) {
                        if (isChanged) {
                            petList.saveFile();
                            orderList.saveFile();
                        }
                        Menus.choice = 10;
                    } else {
                        Menus.choice = 1;
                    }
                    break;
                }
                case 99: {
                    System.out.println("");
                    System.out.println("PET LIST");
                    petList.display();
                    System.out.println("ORDER LIST");
                    orderList.display();
                }
            }
        } while (Menus.choice != 10);
    }

}
