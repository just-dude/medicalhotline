/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.common.exception;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import smartvalidation.constraintViolation.ConstraintViolation;


public class ValidationFailedException extends BusinessException {

    private List<ConstraintViolation> constraintsViolations;
    
    public ValidationFailedException(List<ConstraintViolation> constraintsViolations) {
        super("Constraints violations list: "+StringUtils.join(constraintsViolations.toArray(), ","));
        this.constraintsViolations=constraintsViolations;
    }

    public List<ConstraintViolation> getConstraintsViolations() {
        return constraintsViolations;
    }
}
