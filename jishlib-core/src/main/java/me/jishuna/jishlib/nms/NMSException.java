package me.jishuna.jishlib.nms;

public class NMSException extends RuntimeException {
    private static final long serialVersionUID = -8993267447747521908L;

    public NMSException(String message, Throwable cause) {
        super(message, cause);
    }

    public NMSException(String message) {
        super(message);
    }
}
