package de.dttt.redis;

public interface MessagePublisher {

    void publish(final String message);
}
