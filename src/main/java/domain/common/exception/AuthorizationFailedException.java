/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.common.exception;

import java.util.Arrays;

/**
 *
 * @author SuslovAI
 */
public class AuthorizationFailedException extends BusinessException{

    public AuthorizationFailedException(String permission) {
        super(String.format("Authorization failed. Not permitted: %s",permission));
    }

    public AuthorizationFailedException(String permission, Throwable cause) {
        super(String.format("Authorization failed. Not permitted: %s",permission), cause);
    }
    
    public AuthorizationFailedException(String... permissions) {
        super(String.format("Authorization failed. Not permitted: %s",Arrays.toString(permissions)));
    }

    public AuthorizationFailedException( Throwable cause, String... permissions) {
        super(String.format("Authorization failed. Not permitted: %s",Arrays.toString(permissions)), cause);
    }
    
    
}
