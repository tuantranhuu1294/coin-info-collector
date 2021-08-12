package net.cglcapital.coininfo.consumer;

public interface EventConsumer<E> {

    void consume(E event);
}
