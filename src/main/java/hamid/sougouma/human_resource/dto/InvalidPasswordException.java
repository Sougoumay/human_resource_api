package hamid.sougouma.human_resource.dto;

public class InvalidPasswordException extends Throwable {

    public InvalidPasswordException() {
        super("The password must exactly the same to the confirmation");
    }

}
