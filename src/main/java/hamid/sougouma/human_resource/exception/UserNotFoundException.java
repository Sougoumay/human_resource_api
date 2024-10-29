package hamid.sougouma.human_resource.exception;

public class UserNotFoundException extends Throwable {

    public UserNotFoundException(long id) {
        super(String.format("Experience not found with id : %d", id));
    }

    public UserNotFoundException(String email) {
        super(String.format("Experience not found with email : %s", email));
    }
}
