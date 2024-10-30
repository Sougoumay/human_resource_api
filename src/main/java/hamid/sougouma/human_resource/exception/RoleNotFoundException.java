package hamid.sougouma.human_resource.exception;

public class RoleNotFoundException extends Throwable {

    public RoleNotFoundException(long id) {
        super(String.format("Role not found with id : %d", id));
    }

    public RoleNotFoundException(String name) {
        super(String.format("Role not found with name : %s", name));
    }
}
