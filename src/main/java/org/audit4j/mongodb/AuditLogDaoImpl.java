/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.audit4j.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.dto.Field;
import org.audit4j.core.exception.HandlerException;
import static org.audit4j.mongodb.Utils.checkNotEmpty;

/**
 *
 * @author T-NipunT
 */
public class AuditLogDaoImpl extends AuditBaseDao implements AuditLogDao {

    private final String collectionName;//set in handler
    BasicDBObject document;
    private final String insertQuery;//erase this
    DBCollection collection;

    AuditLogDaoImpl(String collectionName) throws HandlerException {//caller of the constructor knows the table name
        this.collectionName = checkNotEmpty(collectionName, "Collection name must not be empty");
        collection = db.getCollection(collectionName);//resolve db issue, should handle exceptions?
        //boolean auth = db.authenticate(myUserName, myPassword);
        document = new BasicDBObject();//initiating the query

        //createTableIfNotExists(); //no table creation, since mongo doesn't require
    }
    
    @Override
    public boolean writeEvent(AuditEvent event) throws HandlerException {
        StringBuilder elements = new StringBuilder();
        for (Field element : event.getFields()) {
            elements.append(
                    element.getName() + " " + element.getType() + ":" + element.getValue() + ", ");
        }
        try(MongoClient client = getConnection()){
            document.clear();//to clear past entries
            document.put("identifier", event.getUuid().toString());
            document.put("timestamp", event.getTimestamp().getTime());//check if String is returned
            document.put("actor", event.getActor());
            document.put("origin", event.getOrigin());
            document.put("action", event.getAction());
            document.put("elements", elements.toString());
            collection.insert(document);
            return true;
            //query goes here
        }catch (MongoClientException e) {
            throw new HandlerException("Mongo Exception", DatabaseAuditHandler.class, e);//error will resolve once databaseAudithandler class is written
        }
    }

//    private boolean createTableIfNotExists() throws HandlerException {
//        boolean result = false;
//        try (Connection conn = getConnection()) {
//            StringBuilder query = new StringBuilder();
//
//            if (isOracleDatabase()) {
//                // Create table if Oracle Database
//                String values[] = collectionName.split("\\.");
//                query.append("select count(*) from all_tables where table_name = upper('")
//                        .append(values[1]).append("') and tablespace_name = upper('")
//                        .append(values[0]).append("')");
//                try (PreparedStatement statement = conn.prepareStatement(query.toString())) {
//                    result = statement.execute();
//                }
//                if (result == false) {
//                    query.append("create table ").append(collectionName).append(" (")
//                            .append("identifier VARCHAR2(200) NOT NULL,")
//                            .append("timestamp TIMESTAMP NOT NULL,")
//                            .append("actor VARCHAR2(200) NOT NULL,").append("origin VARCHAR2(200),")
//                            .append("action VARCHAR2(200) NOT NULL,").append("elements CLOB")
//                            .append(");");
//                }
//            } else if (isHSQLDatabase()) {
//                // Create Table if HSQLDB database
//                query.append("create table if not exists ").append(collectionName).append(" (")
//                        .append("identifier VARCHAR(200) NOT NULL,")
//                        .append("timestamp TIMESTAMP NOT NULL,")
//                        .append("actor VARCHAR(200) NOT NULL,").append("origin VARCHAR(200),")
//                        .append("action VARCHAR(200) NOT NULL,").append("elements LONGVARCHAR")
//                        .append(");");
//                try (PreparedStatement statement = conn.prepareStatement(query.toString())) {
//                    result = statement.execute();
//                }
//            } else if (isMySQLDatabase()) {
//                // Create table if MySQL database
//                query.append("create table if not exists ").append(collectionName).append(" (")
//                        .append("identifier VARCHAR(200) NOT NULL,")
//                        .append("timestamp TIMESTAMP NOT NULL,")
//                        .append("actor VARCHAR(200) NOT NULL,").append("origin VARCHAR(200),")
//                        .append("action VARCHAR(200) NOT NULL,").append("elements TEXT")
//                        .append(");");
//                try (PreparedStatement statement = conn.prepareStatement(query.toString())) {
//                    result = statement.execute();
//                }
//            } else if (isSQLServerDatabase()) {
//                // Create table if SQLServer database
//                query.append(" IF OBJECT_ID(N'" + collectionName + "', N'U') IS NULL BEGIN ");
//                query.append("create table ").append(collectionName).append(" (")
//                        .append("identifier VARCHAR(200) NOT NULL,")
//                        .append("timestamp DATETIME NOT NULL,")
//                        .append("actor VARCHAR(200) NOT NULL,").append("origin VARCHAR(200),")
//                        .append("action VARCHAR(200) NOT NULL,").append("elements TEXT")
//                        .append(");");
//                query.append(" END ");
//                try (PreparedStatement statement = conn.prepareStatement(query.toString())) {
//                    result = statement.execute();
//                }
//            } else {
//                query.append("create table if not exists ").append(collectionName).append(" (")
//                        .append("identifier VARCHAR(200) NOT NULL,")
//                        .append("timestamp TIMESTAMP NOT NULL,")
//                        .append("actor VARCHAR(200) NOT NULL,").append("origin VARCHAR(200),")
//                        .append("action VARCHAR(200) NOT NULL,").append("elements VARCHAR(70000)")
//                        .append(");");
//                try (PreparedStatement statement = conn.prepareStatement(query.toString())) {
//                    result = statement.execute();
//                }
//            }
//            return result;
//        } catch (SQLException e) {//mongo exception
//            throw new HandlerException("SQL Exception", DatabaseAuditHandler.class, e);
//        }
//    }
}
