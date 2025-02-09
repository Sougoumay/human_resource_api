package hamid.sougouma.human_resource.exception;

public class EmployeeNotFoundException extends Throwable {

    public EmployeeNotFoundException(long id) {
        super(String.format("Employee not found with id : %d", id));
    }

}
