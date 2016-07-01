/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.audit4j.mongodb;

import com.mongodb.MongoClient;
import org.audit4j.core.exception.HandlerException;

/**
 *
 * @author T-NipunT
 */
public class AuditBaseDao {
    private String host;
    private int port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    protected MongoClient getConnection() {
        MongoClient client = new MongoClient(host, port);
        return client;
    }
    
//    protected String determineDatabaseType() throws HandlerException {
//        String dbName = null;
//        try (Connection conn = getConnection()) {
//            dbName = conn.getMetaData().getDatabaseProductName();
//        } catch (SQLException e) {
//            throw new HandlerException("Exception occured while getting DB Name",
//                    DatabaseAuditHandler.class, e);
//        }
//        return dbName;
//    }
//    
//    protected boolean isMongoDatabase() throws HandlerException {
//        String dbName = determineDatabaseType();
//        if (dbName == null) {
//            return false;
//        }
//        return "Oracle".equalsIgnoreCase(dbName);
//    }
    
    
}
