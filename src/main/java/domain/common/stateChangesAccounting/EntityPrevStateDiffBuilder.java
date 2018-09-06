package domain.common.stateChangesAccounting;

import common.DateTimeUtils;
import domain.security.SecuritySubjectUtils;
import domain.userAccounts.UserAccount;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EntityPrevStateDiffBuilder {

    private String entityName;
    private String entityId;
    private LocalDateTime changingDateTime;
    private UserAccount changer;

    private List<EntityFieldPrevState> entityFieldPrevStateList=new ArrayList<>();

    public EntityPrevStateDiffBuilder(String entityName, String entityId) {
        this.entityName = entityName;
        this.entityId = entityId;
        this.changingDateTime = DateTimeUtils.now();
        changer=SecuritySubjectUtils.getCurrentUserAccount();
    }

    public void addField(String fieldName,String fieldValue){
        entityFieldPrevStateList.add(
                new EntityFieldPrevState(new EntityFieldPrevState.EntityFieldPrevStateId(entityName,entityId,fieldName, changingDateTime),fieldValue,changer)
        );
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public LocalDateTime getChangingDateTime() {
        return changingDateTime;
    }

    public UserAccount getChanger() {
        return changer;
    }

    public EntityPrevStateDiff getEntityPrevStateDiff() {
        return new EntityPrevStateDiff(entityFieldPrevStateList);
    }
}
