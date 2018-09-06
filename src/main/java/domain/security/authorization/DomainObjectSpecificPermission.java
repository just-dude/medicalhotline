/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.security.authorization;

import org.apache.shiro.authz.Permission;

/**
 * @author SuslovAI
 */
public interface DomainObjectSpecificPermission extends Permission {

    String getDomainObjectSpecificName();
}
