package se.ergot.country;

public class CountryInitializationException extends RuntimeException {

    public CountryInitializationException(String msg, Exception e) {
        super(msg, e);
    }
}
