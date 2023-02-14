/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import entity.OrderHeader;
import java.util.Comparator;

/**
 *
 * @author huyngo
 */
public class IDComparator implements Comparator<OrderHeader>{
    @Override
    public int compare(OrderHeader o1, OrderHeader o2) {
        return o1.getId().compareTo(o2.getId());
    }
}
