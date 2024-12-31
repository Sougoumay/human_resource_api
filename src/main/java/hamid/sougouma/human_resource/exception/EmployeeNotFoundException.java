package hamid.sougouma.human_resource.exception;

public class EmployeeNotFoundException extends Throwable {

    public EmployeeNotFoundException(long id) {
        super(String.format("User not found with id : %d", id));
    }

    public EmployeeNotFoundException(String email) {
        super(String.format("User not found with email : %s", email));
    }

}
