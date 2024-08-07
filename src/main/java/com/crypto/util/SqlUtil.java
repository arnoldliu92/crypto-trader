package com.crypto.util;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class SqlUtil {
    public Timestamp createCurrentTimestamp() {
        ZoneId zoneSg = ZoneId.of("Singapore");
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(now, zoneSg);
        return Timestamp.from(zonedDateTime.toInstant());
    }
}
