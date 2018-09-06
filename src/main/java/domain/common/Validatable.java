package domain.common;

import smartvalidation.constraintViolation.ConstraintViolation;

import java.util.List;

/**
 * Created by SuslovAI on 29.09.2017.
 */
public interface Validatable {

    boolean isValid();

    List<ConstraintViolation> getConstraintsViolations();
}
