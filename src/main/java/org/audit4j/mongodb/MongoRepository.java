/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.audit4j.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.dto.Field;
import org.audit4j.core.exception.HandlerException;
import org.audit4j.nosql.base.Repository;
import org.audit4j.nosql.base.StoreCredential;
import org.audit4j.nosql.base.StoreIdentifier;
import org.audit4j.nosql.base.StoreServerAddress;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientException;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author T-NipunT
 */
public class MongoRepository implements Repository {

    private List<StoreServerAddress> serverAddresses;

    private StoreIdentifier dbName;

    private List<StoreCredential> credentials;

    @Override
    public void createDataStore(String dataStoreName) throws HandlerException {
        MongoClient client;
        List<ServerAddress> mongoServerAddresses = null;
        ServerAddress mongoServerAddress = null;
        List<MongoCredential> mongoCredentials = null;
        MongoCredential mongoCredential = null;

        if (serverAddresses != null && !serverAddresses.isEmpty() && serverAddresses.size() == 1) {
            mongoServerAddress = new ServerAddress(serverAddresses.get(0).getHost(),
                    serverAddresses.get(0).getPort());
        }
        if (serverAddresses != null && !serverAddresses.isEmpty()) {
            mongoServerAddresses = new ArrayList<>();
            for (StoreServerAddress address : serverAddresses) {
                mongoServerAddresses.add(new ServerAddress(address.getHost(), address.getPort()));
            }
        }
        if (credentials != null && !credentials.isEmpty() && credentials.size() == 1) {
            mongoCredential = MongoCredential.createCredential(credentials.get(0).getUsername(),
                    dbName.getName(), credentials.get(0).getPassword().toCharArray());
        }

        if (credentials != null && !credentials.isEmpty()) {
            mongoCredentials = new ArrayList<>();
            for (StoreCredential storeCredential : credentials) {
                mongoCredentials.add(MongoCredential.createCredential(storeCredential.getUsername(),
                        dbName.getName(), storeCredential.getPassword().toCharArray()));
            }
        }

        if (mongoServerAddress != null && mongoCredential == null && mongoCredentials == null) {
            client = new MongoClient(mongoServerAddress);
        } else if (mongoServerAddress != null && mongoCredentials != null
                && !mongoCredentials.isEmpty()) {
            client = new MongoClient(mongoServerAddress, mongoCredentials);
        } else if (mongoServerAddresses != null && !mongoServerAddresses.isEmpty()
                && mongoCredential == null && mongoCredentials == null) {
            client = new MongoClient(mongoServerAddresses);
        } else if (mongoServerAddresses != null && !mongoServerAddresses.isEmpty()
                && mongoCredentials != null && !mongoCredentials.isEmpty()) {
            client = new MongoClient(mongoServerAddresses, mongoCredentials);
        }
    }

    @Override
    public boolean writeEvent(AuditEvent event) throws HandlerException {
        return false;
        /*
         * StringBuilder elements = new StringBuilder(); for (Field element :
         * event.getFields()) { elements.append( element.getName() + " " +
         * element.getType() + ":" + element.getValue() + ", "); }
         * try(MongoClient client = getConnection()){ document.clear();//to
         * clear past entries document.put("identifier",
         * event.getUuid().toString()); document.put("timestamp",
         * event.getTimestamp().getTime());//check if String is returned
         * document.put("actor", event.getActor()); document.put("origin",
         * event.getOrigin()); document.put("action", event.getAction());
         * document.put("elements", elements.toString());
         * collection.insert(document); return true; //query goes here }catch
         * (MongoClientException e) { throw new HandlerException(
         * "Mongo Exception", DatabaseAuditHandler.class, e);//error will
         * resolve once databaseAudithandler class is written }
         */
    }

}
