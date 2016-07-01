/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.audit4j.mongodb;

/**
 *
 * @author T-NipunT
 * 
 */
public class Utils {
    static final String EMBEDED_DB_NAME = "audit4j";
    static final String EMBEDED_DB_USER = "audit4jdbuser";
    static final String EMBEDED_DB_PASSWORD = "audit4jdbpassword";
    
    private Utils() {
        throw new UnsupportedOperationException();
    }
    
    public static String getDBName(String userName) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(EMBEDED_DB_NAME);
        buffer.append("@");
        buffer.append(userName);
        return buffer.toString();
    }
    
    public static <T> T checkNotNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return t;
    }
    
    public static String checkNotEmpty(String str, String message) {
        if (checkNotNull(str).isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return str;
    }
    
}
