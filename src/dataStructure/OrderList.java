/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataStructure;

import entity.OrderDetail;
import entity.OrderHeader;
import entity.Pet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import tools.CostComparator;
import tools.DateComparator;
import tools.IDComparator;
import tools.MyUtil;

/**
 *
 * @author huyngo
 */
public class OrderList extends ArrayList<OrderHeader> {

    private final static String fName = "src/data/orders.dat/";
    private PetList petList;
    public static HashMap<String, ArrayList<OrderDetail>> orderMap = new HashMap<>();

    public OrderList() {

    }

    public void setPetList(PetList petList) {
        this.petList = petList;
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
                StringTokenizer stk = new StringTokenizer(line, ";");
                String orderID = stk.nextToken();
                String orderDate = stk.nextToken();
                String customerName = stk.nextToken();
                this.add(new OrderHeader(orderID, orderDate, customerName));

                ArrayList<OrderDetail> detailList = new ArrayList();
                while (stk.hasMoreTokens()) {
                    String[] details = stk.nextToken().split(",");
                    String detailID = details[0];
                    String petID = details[1];
                    int quantity = Integer.parseInt(details[2]);
                    double price = petList.getPrice(petID) * quantity;
                    detailList.add(new OrderDetail(detailID, petID, quantity, price));
                }
                orderMap.put(orderID, detailList);
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Failed to read file");
            return;
        }
        System.out.println("Load Orders from file succesffully");
    }

    public void display() {
        if (this.isEmpty()) {
            System.out.println("OrderList empty!");
            return;
        }
        for (OrderHeader o : this) {
            System.out.println(o);
            ArrayList<OrderDetail> details = orderMap.get(o.getId());
            for (OrderDetail d : details) {
                System.out.println("\t" + d);
            }
        }
    }

    public OrderDetail getDetailContainsPet(String petID) {
        if (this.isEmpty()) {
            return null;
        }
        for (OrderHeader o : this) {
            ArrayList<OrderDetail> details = orderMap.get(o.getId());
            for (OrderDetail d : details) {
                if (d.getPetId().equalsIgnoreCase(petID)) {
                    return d;
                }
            }
        }
        return null;
    }

    public boolean isExistedDetail(String id) {
        if (this.isEmpty()) {
            return false;
        }
        for (OrderHeader o : this) {
            ArrayList<OrderDetail> details = orderMap.get(o.getId());
            for (OrderDetail d : details) {
                if (d.getId().equalsIgnoreCase(id)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addOrder() {
        if (petList.isEmpty()) {
            System.out.println("Cannot add order if there is no pet data");
        }
        System.out.println("#Add an order");
        do {
            String orderID;
            int pos = 0;
            do {
                orderID = MyUtil.getString("Order ID:\n>> ", "");
                pos = this.indexOf(new OrderHeader(orderID));
                if (pos >= 0) {
                    System.out.println("ID is duplicated!");
                }
            } while (pos >= 0);
            String date = MyUtil.getDate("Order date:\n>> ", "");
            String customerName = MyUtil.getString("Customer name:\n>> ", "");

            ArrayList<OrderDetail> details = new ArrayList<>();
            do {
                System.out.println("\t#Add a detail");
                String detailID;
                boolean isValid;
                do {
                    detailID = MyUtil.getString("\tOrder Detail ID:\n\t>> ", "");
                    isValid = !isExistedDetail(detailID);
                    if (!isValid) {
                        System.out.println("\tDetail ID is duplicated!");
                    }
                } while (!isValid);
                int petPos;
                String petID;
                petList.display();
                do {
                    petID = MyUtil.getString("\tPet ID:\n\t>> ", "");
                    petPos = petList.indexOf(new Pet(petID));
                    if (petPos < 0) {
                        System.out.println("\tPet ID not found");
                    }
                } while (petPos < 0);
                int quantity = MyUtil.getInt("\tQuantity:\n\t>> ", 0, 9999, "");
                double price = petList.getPrice(petID) * quantity;
                details.add(new OrderDetail(detailID, petID, quantity, price));
            } while (MyUtil.getString("\tAdd another Order details? Y/N\n\t>> ", "").equalsIgnoreCase("y"));
            this.add(new OrderHeader(orderID, date, customerName));
            orderMap.put(orderID, details);
        } while (MyUtil.getString("Add another Order? Y/N\n>> ", "").equalsIgnoreCase("y"));
    }

    public void listOrder() {
        if (this.isEmpty()) {
            System.out.println("Order List is empty");
            return;
        }
        System.out.println("#List orders");
        ArrayList<OrderHeader> result = new ArrayList<>();
        try {
            SimpleDateFormat sdformat = new SimpleDateFormat(MyUtil.dateFormat);
            Date startDate = sdformat.parse(MyUtil.getDate("Start date:\n>> ", ""));
            boolean isValid;
            Date endDate;
            do {
                endDate = sdformat.parse(MyUtil.getDate("End date:\n>> ", ""));
                isValid = startDate.compareTo(endDate) <= 0;
                if (!isValid) {
                    System.out.println("End date must happen after Start date");
                }
            } while (!isValid);

            for (OrderHeader o : this) {
                Date orderDate = sdformat.parse(o.getDate());
                if (orderDate.compareTo(startDate) >= 0 && orderDate.compareTo(endDate) <= 0) {
                    result.add(o);
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(OrderList.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (result.isEmpty()) {
            System.out.println("No Order found between given dates");
            return;
        }
        attributes();
        for (OrderHeader o : result) {
            int petCount = 0;
            double totalPrice = 0;
            ArrayList<OrderDetail> details = orderMap.get(o.getId());
            for (OrderDetail d : details) {
                petCount += d.getQuantity();
                totalPrice += d.getCost();
            }
            System.out.format("%-12s%-12s%-15s%-12d$%-8.2f\n", o.getId(), o.getDate(), o.getCustomerName(), petCount, totalPrice);
        }
    }

    private static void attributes() {
        System.out.println("");
        System.out.format("%-12s%-12s%-15s%-12s%-8s\n", "Order ID", "Order Date", "Customer", "Pet Count", "Order Total");
        System.out.println("==============================================================");
    }

    public void sortOrder() {
        if (this.isEmpty()) {
            System.out.println("Order List is empty");
            return;
        }
        System.out.println("#Sort orders");
        boolean isValid;
        String sortBy;
        String sortOrder;
        do {
            isValid = true;
            sortBy = MyUtil.getString("Sort by? (Order ID/Order date/Order total):\n>> ", "").toUpperCase();
            if (!sortBy.contains("ID") && !sortBy.contains("DATE") && !sortBy.contains("TOTAL")) {
                System.out.println("Invalid input");
                isValid = false;
            }
        } while (!isValid);

        do {
            isValid = true;
            sortOrder = MyUtil.getString("Sort order? (ASC/DESC):\n>> ", "").toUpperCase();
            if (!sortOrder.contains("ASC") && !sortOrder.contains("DESC")) {
                System.out.println("Invalid input");
                isValid = false;
            }
        } while (!isValid);

        if (sortBy.contains("ID")) {
            if (sortOrder.contains("ASC")) {
                Collections.sort(this, new IDComparator());
            } else {
                Collections.sort(this, new IDComparator().reversed());
            }
        } else if (sortBy.contains("DATE")) {
            if (sortOrder.contains("ASC")) {
                Collections.sort(this, new DateComparator());
            } else {
                Collections.sort(this, new DateComparator().reversed());
            }
        } else if (sortBy.contains("TOTAL")) {
            if (sortOrder.contains("ASC")) {
                Collections.sort(this, new CostComparator());
            } else {
                Collections.sort(this, new CostComparator().reversed());
            }
        }
        attributes();
        for (OrderHeader o : this) {
            int petCount = 0;
            double totalPrice = 0;
            ArrayList<OrderDetail> details = orderMap.get(o.getId());
            for (OrderDetail d : details) {
                petCount += d.getQuantity();
                totalPrice += d.getCost();
            }
            System.out.format("%-12s%-12s%-15s%-12d$%-8.2f\n", o.getId(), o.getDate(), o.getCustomerName(), petCount, totalPrice);
        }
    }

    public void saveFile() {
        System.out.println("#Saving Orders to file");
        try {
            PrintWriter pw = new PrintWriter(fName);
            for (OrderHeader o : this) {
                String detailString = "";
                ArrayList<OrderDetail> details = orderMap.get(o.getId());
                for (OrderDetail d : details) {
                    detailString += ";" + d.getId() + "," + d.getPetId() + "," + d.getQuantity();
                }
                pw.println(o.getId() + ";" + o.getDate() + ";" + o.getCustomerName() + detailString);
            }
            pw.close();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Failed to save file!");
        }
        System.out.println("Save Orders to file successfully");
    }
}
