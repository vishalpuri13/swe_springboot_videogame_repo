package Gameatorium.videogames.exceptions;


public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(String roleName) {
        super("Role not found: " + roleName);
    }

    public RoleNotFoundException(String roleName, Throwable cause) {
        super("Role not found: " + roleName, cause);
    }
}