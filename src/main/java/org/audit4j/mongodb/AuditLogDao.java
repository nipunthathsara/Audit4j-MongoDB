/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.audit4j.mongodb;

import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.exception.HandlerException;

/**
 *
 * @author T-NipunT
 */
public interface AuditLogDao {
    boolean writeEvent(final AuditEvent event) throws HandlerException;
}
