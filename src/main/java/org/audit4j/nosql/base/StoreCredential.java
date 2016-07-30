package org.audit4j.nosql.base;

public final class StoreCredential {

    private static String username;

    private static String password;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        StoreCredential.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        StoreCredential.password = password;
    }
}
