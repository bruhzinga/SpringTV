package by.zvor.springtv.DTO;

/**
 * A Projection for the {@link by.zvor.springtv.Entity.User} entity
 */
public interface UserInfo {
    Integer getId();

    String getUsername();

    String getPasswordHash();

    String getEmail();

    Integer  getRoleId();

    /**
     * A Projection for the {@link by.zvor.springtv.Entity.Role} entity
     */
    interface RoleInfo {
        Integer getId();

        String getName();
    }
}