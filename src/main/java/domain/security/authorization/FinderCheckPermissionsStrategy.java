package domain.security.authorization;

import domain.common.exception.AuthorizationFailedException;

import java.util.List;

/**
 * Created by Администратор on 31.10.2017.
 */
public interface FinderCheckPermissionsStrategy<T> {
    void checkFindAllPermission() throws AuthorizationFailedException;

    void checkFindOnePermission(T entity) throws AuthorizationFailedException;

    void checkFindAllByIdsPermission(List<T> entities) throws AuthorizationFailedException;
}
