/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import static dataStructure.OrderList.orderMap;
import entity.OrderDetail;
import entity.OrderHeader;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author huyngo
 */
public class CostComparator implements Comparator<OrderHeader> {

    @Override
    public int compare(OrderHeader o1, OrderHeader o2) {
        double totalPrice1 = 0;
        double totalPrice2 = 0;
        ArrayList<OrderDetail> details1 = orderMap.get(o1.getId());
        for (OrderDetail d : details1) {
            totalPrice1 += d.getCost();
        }
        ArrayList<OrderDetail> details2 = orderMap.get(o2.getId());
        for (OrderDetail d : details2) {
            totalPrice2 += d.getCost();
        }
        int result = (int)totalPrice1 - (int)totalPrice2;
        return result;
    }

}
