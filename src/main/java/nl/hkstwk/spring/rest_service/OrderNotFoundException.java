package nl.hkstwk.spring.rest_service;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException() {
        this(null);
    }
    public OrderNotFoundException(Long id) { super("Could not find order " + id); }
}
