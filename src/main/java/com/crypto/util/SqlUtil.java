package com.crypto.util;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class SqlUtil {
    public Timestamp createCurrentTimestamp() {
        return new java.sql.Timestamp(System.currentTimeMillis());
    }
}
