package com.eduardocode.webservices.rest.restfulindeep.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <h1>BasicUtils</h1>
 * Formatting mapping and other related things
 *
 * @author Eduardo Rasgado Ruiz
 */
public class BasicUtils {
    private static SimpleDateFormat simpleDateFormatter = new SimpleDateFormat("dd-MM-yyyy");

    public static Date transformToDate(String date) {
        try {
            Date dateObject = simpleDateFormatter.parse(date);
            return dateObject;
        } catch (ParseException pe) {
            System.out.println(pe.getMessage());
        }
        return null;
    }
}
