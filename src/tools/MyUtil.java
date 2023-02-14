/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author huyngo
 */
public class MyUtil {

    public static Scanner sc = new Scanner(System.in);
    public static String dateFormat = "dd/MM/yyyy";

    public static int getInt(String message, String mode) {
        int value = 0;
        String s;
        boolean isValid;
        do {
            isValid = true;
            System.out.print(message);
            s = sc.nextLine();

            if (mode.contains("update") && (s.equals(null) || s.equals(""))) {
                return Integer.MIN_VALUE;
            }

            try {
                value = Integer.parseInt(s);
            } catch (Exception e) {
                isValid = false;
            }
            if (isValid == false) {
                System.out.println("Invalid Input");
            }
        } while (isValid == false);
        return value;
    }

    public static int getInt(String message, int min, int max, String mode) {
        int value = 0;
        String s;
        boolean isValid;
        do {
            isValid = true;
            System.out.print(message);
            s = sc.nextLine();

            if (mode.contains("update") && (s.equals(null) || s.equals(""))) {
                return Integer.MIN_VALUE;
            }

            try {
                value = Integer.parseInt(s);
            } catch (Exception e) {
                isValid = false;
            }
            if (value < min || value > max) {
                isValid = false;
            }
            if (isValid == false) {
                System.out.println("Invalid Input");
            }
        } while (isValid == false);
        return value;
    }
    
    public static double getDouble(String message, double min, double max, String mode) {
        double value = 0;
        String s;
        boolean isValid;
        do {
            isValid = true;
            System.out.print(message);
            s = sc.nextLine();

            if (mode.contains("update") && (s.equals(null) || s.equals(""))) {
                return Double.MIN_VALUE;
            }

            try {
                value = Double.parseDouble(s);
            } catch (Exception e) {
                isValid = false;
            }
            if (value < min || value > max) {
                isValid = false;
            }
            if (isValid == false) {
                System.out.println("Invalid Input");
            }
        } while (isValid == false);
        return value;
    }

    public static String getString(String message, String mode) {
        String s;
        boolean inputIsValid;
        do {
            inputIsValid = true;
            System.out.print(message);
            s = sc.nextLine();

            if (mode.contains("update") && (s.equals(null) || s.equals(""))) {
                return "_not_change";
            }

            if (s.equals(null) || s.equals("")) {
                inputIsValid = false;
                System.out.println("Input cannot be empty");
            }
        } while (!inputIsValid);
        if (mode.contains("uppercase")) {
            s = s.toUpperCase();
        }
        return s.trim();
    }

    public static String getString(String message, int min, int max, String mode) {
        String s;
        boolean inputIsValid;
        do {
            inputIsValid = true;
            System.out.print(message);
            s = sc.nextLine();

            if (mode.contains("update") && (s.equals(null) || s.equals(""))) {
                return "_not_change";
            }

            if (s.equals(null) || s.equals("")) {
                inputIsValid = false;
                System.out.println("Input cannot be empty");
            } else if (s.length() < min) {
                inputIsValid = false;
                System.out.println("Input is too short");
            } else if (s.length() > max) {
                inputIsValid = false;
                System.out.println("Input is too long");
            }
        } while (!inputIsValid);
        if (mode.contains("uppercase")) {
            s = s.toUpperCase();
        }
        return s.trim();
    }

    public static String getString(String message, String format, String mode) {
        String s;
        boolean inputIsValid;
        do {
            inputIsValid = true;
            System.out.print(message);
            s = sc.nextLine();

            if (mode.contains("update") && (s.equals(null) || s.equals(""))) {
                return "_not_change";
            }

            if (s.equals(null) || s.equals("")) {
                inputIsValid = false;
                System.out.println("Input cannot be empty");
            } else if (!s.matches(format)) {
                inputIsValid = false;
                System.out.println("Input does not match the format");
            }
        } while (!inputIsValid);
        if (mode.contains("uppercase")) {
            s = s.toUpperCase();
        }
        return s.trim();
    }
    
    public static String getDate(String message, String mode) {
        String s = "";
        boolean inputIsValid;
        do {            
            inputIsValid = true;
            System.out.print(message);
            s = sc.nextLine();
            if (s.equals(null) || s.equals("")){
                if (mode.contains("update")) {
                    return "_not_change";
                }
                System.out.println("Date cannot be empty!");
                inputIsValid = false;
            } else {
                inputIsValid = isValidDate(s);
                if (!inputIsValid) {
                    System.out.println("Date is not valid!");
                }
            }
        } while (!inputIsValid);
        return s;
    }

    public static boolean isValidDate(String strDate) {
        if (strDate.trim().equals("")) {
            return true;
        } else {
            SimpleDateFormat sdfrmt = new SimpleDateFormat(dateFormat);
            sdfrmt.setLenient(false);
            try {
                Date javaDate = sdfrmt.parse(strDate);
            } catch (ParseException e) {
                return false;
            }
            return true;
        }
    }
}
