package uddipak.cabbooking.exceptions;

public class AppUserNotFound extends RuntimeException{
    public AppUserNotFound (String message) {
        super(message);
    }
}
