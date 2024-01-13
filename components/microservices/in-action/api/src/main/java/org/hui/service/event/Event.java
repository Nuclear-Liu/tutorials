package org.hui.service.event;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;

import java.time.ZonedDateTime;

import static java.time.ZonedDateTime.now;

public record Event<K, T>(
        Type eventType,
        K key,
        T data,
        @JsonSerialize(using = ZonedDateTimeSerializer.class)
        ZonedDateTime eventCreatedAt) {
    public Event(Type eventType, K key, T data) {
        this(eventType, key, data, now());
    }

    public enum Type {
        CREATE,
        DELETE
    }
}
