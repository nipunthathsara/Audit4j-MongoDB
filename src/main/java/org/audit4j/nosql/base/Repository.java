package org.audit4j.nosql.base;

import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.exception.HandlerException;

public interface Repository {
   
    void createDataStore(String dataStoreName) throws HandlerException;
    
    boolean writeEvent(final AuditEvent event) throws HandlerException;
}
