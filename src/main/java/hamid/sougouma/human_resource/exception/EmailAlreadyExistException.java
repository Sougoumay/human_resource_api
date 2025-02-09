package hamid.sougouma.human_resource.exception;

public class EmailAlreadyExistException extends Throwable {
    public EmailAlreadyExistException(String email) {
        super(String.format("Employee already exist with email : %s. Please choose another email address", email));
    }
}
