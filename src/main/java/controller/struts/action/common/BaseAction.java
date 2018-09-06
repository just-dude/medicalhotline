
package controller.struts.action.common;


import com.opensymphony.xwork2.interceptor.ValidationWorkflowAware;
import controller.struts.action.common.util.ActionUtils;
import org.apache.commons.lang3.ArrayUtils;
import smartvalidation.constraintDescription.ConstraintDescription;
import smartvalidation.constraintDescription.PartsOfWholeSupportedConstraintDescription;
import smartvalidation.constraintViolation.ConstraintViolation;
import smartvalidation.constraintViolation.EntityConstraintViolation;
import smartvalidation.constraintViolation.PropertyConstraintViolation;

import java.util.List;
import java.util.Map;

public abstract class BaseAction extends com.opensymphony.xwork2.ActionSupport implements ValidationWorkflowAware {

    protected void fillActionErrors(List<ConstraintViolation> constraintsViolations) {
        for (ConstraintViolation cv : constraintsViolations) {
            if (cv instanceof PropertyConstraintViolation) {
                PropertyConstraintViolation pcv = (PropertyConstraintViolation) cv;
                if(pcv.getConstraintDescription() instanceof PartsOfWholeSupportedConstraintDescription) {
                    String subDescriptionsText="";
                    for(Map.Entry<String,ConstraintDescription> entry:((PartsOfWholeSupportedConstraintDescription)pcv.getConstraintDescription()).getPartNameToConstraintDesciptionMap().entrySet()){
                        subDescriptionsText+=entry.getKey()+": "+getText(
                                entry.getValue().getConstraintFullName(),
                                entry.getValue().getConstraintFullName(),
                                entry.getValue().getConstraintParameters())+", ";
                    }
                    subDescriptionsText.substring(0,subDescriptionsText.length()-3);
                    this.addFieldError(pcv.getPropertyPath(),
                            getText(
                                    pcv.getConstraintDescription().getConstraintFullName(),
                                    pcv.getConstraintDescription().getConstraintFullName(),
                                    ArrayUtils.add(pcv.getConstraintDescription().getConstraintParameters(),subDescriptionsText)
                            )
                    );
                } else{
                    this.addFieldError(pcv.getPropertyPath(),
                            getText(
                                    pcv.getConstraintDescription().getConstraintFullName(),
                                    pcv.getConstraintDescription().getConstraintFullName(),
                                    pcv.getConstraintDescription().getConstraintParameters()
                            )
                    );
                }
            }
            if (cv instanceof EntityConstraintViolation) {
                String actionError = ((EntityConstraintViolation) cv).getConstraintDescription().getConstraintFullName();
                this.addActionError(getText(actionError,actionError));
            }
        }
    }

    @Override
    public String getInputResultName() {
        return ActionUtils.getDecoratedByViewSettingsResult(CustomResults.INVALID_USER_INPUT);
    }
}
