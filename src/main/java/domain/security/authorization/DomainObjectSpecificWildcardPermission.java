/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.security.authorization;

import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * @author SuslovAI
 */
public class DomainObjectSpecificWildcardPermission extends WildcardPermission implements DomainObjectSpecificPermission {

    public DomainObjectSpecificWildcardPermission() {
    }

    public DomainObjectSpecificWildcardPermission(String wildcardString) {
        super(wildcardString);
    }

    public DomainObjectSpecificWildcardPermission(String wildcardString, boolean caseSensitive) {
        super(wildcardString, caseSensitive);
    }


    @Override
    public String getDomainObjectSpecificName() {
        return this.getParts().get(0).iterator().next();
    }

}
