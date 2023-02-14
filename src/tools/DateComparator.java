/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import entity.OrderHeader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import static tools.MyUtil.dateFormat;

/**
 *
 * @author huyngo
 */
public class DateComparator implements Comparator<OrderHeader>{

    @Override
    public int compare(OrderHeader o1, OrderHeader o2) {
        SimpleDateFormat sdfrmt = new SimpleDateFormat(dateFormat);
        try {
            Date date1 = sdfrmt.parse(o1.getDate());
            Date date2 = sdfrmt.parse(o2.getDate());
            return date2.compareTo(date1);
        } catch (ParseException ex) {
            Logger.getLogger(DateComparator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
}
