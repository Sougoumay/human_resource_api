package hamid.sougouma.human_resource.exception;

public class UserNotFoundException extends Throwable {

    public UserNotFoundException(long id) {
        super(String.format("La personne avec l'id %d n'existe en base", id));
    }

    public UserNotFoundException(String email) {
        super(String.format("La personne avec l'email %s n'existe en base", email));
    }
}
