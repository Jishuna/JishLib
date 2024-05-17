package me.jishuna.jishlib.util;

public class ValidationException extends Exception {
    private static final long serialVersionUID = 1L;

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(String message) {
        super(message);
    }

    public void print() {
        print(0);
    }

    private void print(int depth) {
        System.out.println(" ".repeat(depth) + "- " + getMessage());
        if (getCause() instanceof ValidationException e) {
            e.print(++depth);
        }
    }
}
