/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataStructure;

import static dataStructure.OrderList.orderMap;
import entity.OrderDetail;
import entity.OrderHeader;
import entity.Pet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import tools.MyUtil;

/**
 *
 * @author huyngo
 */
public class PetList extends ArrayList<Pet> {

    private static String fName = "src/data/pets.dat/";
    private OrderList orderList;

    public void setOrderList(OrderList orderList) {
        this.orderList = orderList;
    }

    public void readFromFile() {
        File f = new File(fName);
        if (!f.exists()) {
            System.out.println("File is not existed!");
            System.exit(0);
        }
        try {
            this.clear();
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(";");
                String id = details[0].trim();
                String description = details[1].trim();
                String date = details[2].trim();
                double price = Double.parseDouble(details[3].trim());
                String category = details[4].trim();
                this.add(new Pet(id, description, date, price, category));
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Failed to read file");
            return;
        }
        System.out.println("Load Pets from file successfully");
    }

    public void display() {
        if (this.isEmpty()) {
            System.out.println("Pets list is empty");
            return;
        }
        for (Pet p : this) {
            System.out.println(p);
        }
    }

    // FUNCTION #1
    public void addPet() {
        do {
            System.out.println("#Add a pet");
            int pos = 0;
            String id;
            do {
                id = MyUtil.getString("Pet ID:\n>> ", "");
                pos = this.indexOf(new Pet(id));
                if (pos >= 0) {
                    System.out.println("ID cannot be duplicated!");
                }
            } while (pos >= 0);
            String description = MyUtil.getString("Pet description:\n>> ", 3, 50, "");
            String date = MyUtil.getDate("Import date:\n>> ", "");
            double price = MyUtil.getDouble("Price:\n>> ", 0, 99999, "");
            String category;
            boolean isValid = true;
            do {
                category = MyUtil.getString("Category:\n>> ", "");
                isValid = category.equalsIgnoreCase("Cat") || category.equalsIgnoreCase("Dog") || category.equalsIgnoreCase("Parrot");
                if (!isValid) {
                    System.out.println("Category is not valid!");
                }
            } while (!isValid);
            category = category.substring(0, 1).toUpperCase() + category.substring(1);
            this.add(new Pet(id, description, date, price, category));
            System.out.println("Pet added succesfully");
        } while (MyUtil.getString("Add another Pet? Y/N\n>> ", "").equalsIgnoreCase("Y"));
    }

    //FUNCTION #2
    public void findPet() {
        System.out.println("#Find a pet");
        int pos = 0;
        String id;

        id = MyUtil.getString("Pet ID:\n>> ", "");
        pos = this.indexOf(new Pet(id));
        if (pos < 0) {
            System.out.println("The pet does not exist");
            return;
        }
        System.out.println(this.get(pos));
    }

    //FUNCTION #3
    public boolean updatePet() {
        if (this.isEmpty()) {
            System.out.println("Pet list is empty");
            return false;
        }
        System.out.println("#Update a pet");
        int pos = 0;
        String id;

        id = MyUtil.getString("Pet ID:\n>> ", "");
        pos = this.indexOf(new Pet(id));
        if (pos < 0) {
            System.out.println("Failed to update pet");
            System.out.println("The pet does not exist");
            return false;
        }
        Pet pet = this.get(pos);
        String description = MyUtil.getString("Pet description:\n>> ", 3, 50, "update");
        if (description.equals("_not_change")) {
            description = pet.getDescription();
            System.out.println(">> " + description);
        }
        String date = MyUtil.getDate("Import date:\n>> ", "update");
        if (date.equals("_not_change")) {
            date = pet.getDate();
            System.out.println(">> " + date);
        }
        double price = MyUtil.getDouble("Price:\n>> ", 0, 99999, "update");
        if (price == Double.MIN_VALUE) {
            price = pet.getPrice();
            System.out.println(">> " + price);
        }
        String category;
        boolean isValid = true;
        do {
            category = MyUtil.getString("Category:\n>> ", "update");
            if (category.equals("_not_change")) {
                category = pet.getCategory();
                System.out.println(">> " + category);
            } else {
                isValid = category.equalsIgnoreCase("Cat") || category.equalsIgnoreCase("Dog") || category.equalsIgnoreCase("Parrot");
                if (!isValid) {
                    System.out.println("Category is not valid!");
                } else {
                    category = category.substring(0, 1).toUpperCase() + category.substring(1);
                }
            }
        } while (!isValid);
        pet.setDescription(description);
        pet.setDate(date);
        pet.setPrice(price);
        pet.setCategory(category);
        System.out.println("Update pet successfully");
        return true;
    }

    // FUNCTION #4
    public boolean deletePet() {
        if (this.isEmpty()) {
            System.out.println("Pet list is empty");
            return false;
        }
        System.out.println("#Delete a pet");
        int pos = 0;
        String id;

        id = MyUtil.getString("Pet ID:\n>> ", "");
        pos = this.indexOf(new Pet(id));
        if (pos < 0) {
            System.out.println("Failed to delete pet");
            System.out.println("The pet does not exist");
            return false;
        }
        OrderDetail detail = orderList.getDetailContainsPet(id);
        if (detail != null) {
            System.out.println("Failed to delete pet");
            System.out.println("Pet is already in an order detail");
            System.out.println(detail);
            return false;
        }
        System.out.println(this.get(pos));
        if (MyUtil.getString("Do you want to delete this pet? Y/N\n>> ", "").equalsIgnoreCase("y")) {
            this.remove(pos);
            System.out.println("Delete pet successfully");
        } else {
            System.out.println("Deletion canceled");
        }
        return true;
    }

    public double getPrice(String id) {
        if (this.isEmpty()) {
            return 0;
        }
        for (Pet p : this) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p.getPrice();
            }
        }
        return 0;
    }

    public void saveFile() {
        System.out.println("#Saving Pets  to file");
        try {
            PrintWriter pw = new PrintWriter(fName);
            for (Pet p : this) {
                pw.println(p.getId() + ";" + p.getDescription() + ";" + p.getDate() + ";" + p.getPrice() + ";" + p.getCategory());
            }
            pw.close();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Failed to save file!");
        }
        System.out.println("Save Pets to file successfully");
    }

}
