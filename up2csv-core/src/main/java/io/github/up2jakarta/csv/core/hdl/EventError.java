package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.xml.api.SeverityType;

final class EventError extends EventType<Event> {
    public static final EventError INSTANCE = new EventError();

    private EventError() {
        super(Event.class);
    }

    @Override
    public Event of(SeverityType level, String code, String message) {
        return new Event(level, code, message, null);
    }

    public Event of(SeverityType level, String code, Throwable cause) {
        return new Event(level, code, cause.getMessage(), cause);
    }
}
