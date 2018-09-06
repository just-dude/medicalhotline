package domain.security.authorization;

import org.apache.shiro.authz.Permission;

/**
 * Created by Администратор on 25.10.2017.
 */
public class NotOnePermission implements Permission{

    @Override
    public boolean implies(Permission permission) {
        return false;
    }
}
