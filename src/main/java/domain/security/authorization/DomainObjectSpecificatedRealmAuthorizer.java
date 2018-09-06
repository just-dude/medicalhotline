/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.security.authorization;

import domain.common.exception.AuthorizationFailedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.PermissionResolverAware;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.RolePermissionResolverAware;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DomainObjectSpecificatedRealmAuthorizer implements Authorizer, PermissionResolverAware, RolePermissionResolverAware, Realm {

    private static final String realmName = DomainObjectSpecificatedRealmAuthorizer.class.getName();

    protected static final Logger LOG = LogManager.getLogger(DomainObjectSpecificatedRealmAuthorizer.class.getName());

    protected Map<String, Realm> realms;

    protected PermissionResolver permissionResolver;

    protected RolePermissionResolver rolePermissionResolver;

    public DomainObjectSpecificatedRealmAuthorizer() {
    }

    public DomainObjectSpecificatedRealmAuthorizer(Map<String, Realm> realms) {
        setRealms(realms);
    }

    public Map<String, Realm> getRealms() {
        return this.realms;
    }

    public void setRealms(Map<String, Realm> realms) {
        this.realms = realms;
        applyPermissionResolverToRealms();
        applyRolePermissionResolverToRealms();
    }

    public PermissionResolver getPermissionResolver() {
        return this.permissionResolver;
    }

    public void setPermissionResolver(PermissionResolver permissionResolver) {
        this.permissionResolver = permissionResolver;
        applyPermissionResolverToRealms();
    }

    protected void applyPermissionResolverToRealms() {
        PermissionResolver resolver = getPermissionResolver();
        Map<String, Realm> realms = getRealms();
        if (resolver != null && realms != null && !realms.isEmpty()) {
            for (Realm realm : realms.values()) {
                if (realm instanceof PermissionResolverAware) {
                    ((PermissionResolverAware) realm).setPermissionResolver(resolver);
                }
            }
        }
    }

    public RolePermissionResolver getRolePermissionResolver() {
        return this.rolePermissionResolver;
    }

    public void setRolePermissionResolver(RolePermissionResolver rolePermissionResolver) {
        this.rolePermissionResolver = rolePermissionResolver;
        applyRolePermissionResolverToRealms();
    }

    protected void applyRolePermissionResolverToRealms() {
        RolePermissionResolver resolver = getRolePermissionResolver();
        Map<String, Realm> realms = getRealms();
        if (resolver != null && realms != null && !realms.isEmpty()) {
            for (Realm realm : realms.values()) {
                if (realm instanceof RolePermissionResolverAware) {
                    ((RolePermissionResolverAware) realm).setRolePermissionResolver(resolver);
                }
            }
        }
    }

    protected void assertRealmsConfigured() throws IllegalStateException {
        Map<String, Realm> realms = getRealms();
        if (realms == null || realms.isEmpty()) {
            String msg = "Configuration error:  No realms have been configured!  One or more realms must be " +
                    "present to execute an authorization operation.";
            throw new IllegalStateException(msg);
        }
    }

    protected void assertPermissionIsModuleSpecific(Permission permission) throws IllegalArgumentException {
        if (!(permission instanceof DomainObjectSpecificPermission)) {
            throw new IllegalArgumentException("Permission must be instance of DomainObjectSpecificPermission");
        }
    }

    public boolean isPermitted(PrincipalCollection principals, String permission) {
        return isPermitted(principals, getPermissionResolver().resolvePermission(permission));
    }

    public boolean isPermitted(PrincipalCollection principals, Permission permission) {
        LOG.debug("principals: " + principals);
        LOG.debug("permissionStringConsts: " + permission);
        assertRealmsConfigured();
        assertPermissionIsModuleSpecific(permission);
        DomainObjectSpecificPermission msp = (DomainObjectSpecificPermission) permission;
        if (getRealms().containsKey(msp.getDomainObjectSpecificName())) {
            Realm realm = getRealms().get(msp.getDomainObjectSpecificName());
            if (!(realm instanceof Authorizer)) {
                throw new IllegalStateException("Realm specific to module is not support Authorizer interface");
            }
            if (((Authorizer) realm).isPermitted(principals, permission)) {
                return true;
            }
        }
        return false;
    }


    public boolean[] isPermitted(PrincipalCollection principals, String... permissions) {
        assertRealmsConfigured();
        if (permissions != null && permissions.length > 0) {
            boolean[] isPermitted = new boolean[permissions.length];
            for (int i = 0; i < permissions.length; i++) {
                isPermitted[i] = isPermitted(principals, permissions[i]);
            }
            return isPermitted;
        }
        return new boolean[0];
    }

    /**
     * Returns <code>true</code> if any of the configured realms'
     * {@link #isPermitted(PrincipalCollection, List)} call returns <code>true</code>,
     * <code>false</code> otherwise.
     */
    public boolean[] isPermitted(PrincipalCollection principals, List<Permission> permissions) {
        assertRealmsConfigured();
        if (permissions != null && !permissions.isEmpty()) {
            boolean[] isPermitted = new boolean[permissions.size()];
            int i = 0;
            for (Permission p : permissions) {
                isPermitted[i++] = isPermitted(principals, p);
            }
            return isPermitted;
        }

        return new boolean[0];
    }

    /**
     * Returns <code>true</code> if any of the configured realms'
     * {@link #isPermitted(PrincipalCollection, String)} call returns <code>true</code>
     * for <em>all</em> of the specified string permissionStringConsts, <code>false</code> otherwise.
     */
    public boolean isPermittedAll(PrincipalCollection principals, String... permissions) {
        assertRealmsConfigured();
        if (permissions != null && permissions.length > 0) {
            for (String perm : permissions) {
                if (!isPermitted(principals, perm)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns <code>true</code> if any of the configured realms'
     * {@link #isPermitted(PrincipalCollection, Permission)} call returns <code>true</code>
     * for <em>all</em> of the specified Permissions, <code>false</code> otherwise.
     */
    public boolean isPermittedAll(PrincipalCollection principals, Collection<Permission> permissions) {
        assertRealmsConfigured();
        if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
                if (!isPermitted(principals, permission)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * If !{@link #isPermitted(PrincipalCollection, String) isPermitted(permissionStringConsts)}, throws
     * an <code>UnauthorizedException</code> otherwise returns quietly.
     */
    public void checkPermission(PrincipalCollection principals, String permission) throws AuthorizationFailedException {
        assertRealmsConfigured();
        if (!isPermitted(principals, permission)) {
            throw new UnauthorizedException("Subject does not have permissionStringConsts [" + permission + "]");
        }
    }

    /**
     * If !{@link #isPermitted(PrincipalCollection, Permission) isPermitted(permissionStringConsts)}, throws
     * an <code>UnauthorizedException</code> otherwise returns quietly.
     */
    public void checkPermission(PrincipalCollection principals, Permission permission) throws AuthorizationFailedException {
        assertRealmsConfigured();
        if (!isPermitted(principals, permission)) {
            throw new UnauthorizedException("Subject does not have permissionStringConsts [" + permission + "]");
        }
    }

    /**
     * If !{@link #isPermitted(PrincipalCollection, String...) isPermitted(permissionStringConsts)},
     * throws an <code>UnauthorizedException</code> otherwise returns quietly.
     */
    public void checkPermissions(PrincipalCollection principals, String... permissions) throws AuthorizationFailedException {
        assertRealmsConfigured();
        if (permissions != null && permissions.length > 0) {
            for (String perm : permissions) {
                checkPermission(principals, perm);
            }
        }
    }

    /**
     * If !{@link #isPermitted(PrincipalCollection, Permission) isPermitted(permissionStringConsts)} for
     * <em>all</em> the given Permissions, throws
     * an <code>UnauthorizedException</code> otherwise returns quietly.
     */
    public void checkPermissions(PrincipalCollection principals, Collection<Permission> permissions) throws AuthorizationFailedException {
        assertRealmsConfigured();
        if (permissions != null) {
            for (Permission permission : permissions) {
                checkPermission(principals, permission);
            }
        }
    }

    /**
     * Returns <code>true</code> if any of the configured realms'
     * {@link #hasRole(PrincipalCollection, String)} call returns <code>true</code>,
     * <code>false</code> otherwise.
     */
    public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
        assertRealmsConfigured();
        for (Realm realm : getRealms().values()) {
            if (!(realm instanceof Authorizer)) continue;
            if (((Authorizer) realm).hasRole(principals, roleIdentifier)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calls {@link #hasRole(PrincipalCollection, String)} for each role name in the specified
     * collection and places the return value from each call at the respective location in the returned array.
     */
    public boolean[] hasRoles(PrincipalCollection principals, List<String> roleIdentifiers) {
        assertRealmsConfigured();
        if (roleIdentifiers != null && !roleIdentifiers.isEmpty()) {
            boolean[] hasRoles = new boolean[roleIdentifiers.size()];
            int i = 0;
            for (String roleId : roleIdentifiers) {
                hasRoles[i++] = hasRole(principals, roleId);
            }
            return hasRoles;
        }

        return new boolean[0];
    }

    /**
     * Returns <code>true</code> iff any of the configured realms'
     * {@link #hasRole(PrincipalCollection, String)} call returns <code>true</code> for
     * <em>all</em> roles specified, <code>false</code> otherwise.
     */
    public boolean hasAllRoles(PrincipalCollection principals, Collection<String> roleIdentifiers) {
        assertRealmsConfigured();
        for (String roleIdentifier : roleIdentifiers) {
            if (!hasRole(principals, roleIdentifier)) {
                return false;
            }
        }
        return true;
    }

    /**
     * If !{@link #hasRole(PrincipalCollection, String) hasRole(role)}, throws
     * an <code>UnauthorizedException</code> otherwise returns quietly.
     */
    public void checkRole(PrincipalCollection principals, String role) throws AuthorizationFailedException {
        assertRealmsConfigured();
        if (!hasRole(principals, role)) {
            throw new UnauthorizedException("Subject does not have role [" + role + "]");
        }
    }

    /**
     * Calls {@link #checkRoles(PrincipalCollection principals, String... roles) checkRoles(PrincipalCollection principals, String... roles) }.
     */
    public void checkRoles(PrincipalCollection principals, Collection<String> roles) throws AuthorizationFailedException {
        //SHIRO-234 - roles.toArray() -> roles.toArray(new String[roles.size()])
        if (roles != null && !roles.isEmpty()) checkRoles(principals, roles.toArray(new String[roles.size()]));
    }

    /**
     * Calls {@link #checkRole(PrincipalCollection, String) checkRole} for each role specified.
     */
    public void checkRoles(PrincipalCollection principals, String... roles) throws AuthorizationFailedException {
        assertRealmsConfigured();
        if (roles != null) {
            for (String role : roles) {
                checkRole(principals, role);
            }
        }
    }

    @Override
    public String getName() {
        return realmName;
    }

    @Override
    public boolean supports(AuthenticationToken at) {
        return false;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken at) throws AuthenticationException {
        throw new UnsupportedOperationException("This class not support authentication");
    }


}
