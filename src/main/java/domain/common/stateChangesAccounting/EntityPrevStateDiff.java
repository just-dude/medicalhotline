package domain.common.stateChangesAccounting;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public class EntityPrevStateDiff {

    private List<EntityFieldPrevState> entityFieldPrevStateList;

    public EntityPrevStateDiff(List<EntityFieldPrevState> entityFieldPrevStateList) {
        this.entityFieldPrevStateList = entityFieldPrevStateList;
    }

    public List<EntityFieldPrevState> getEntityFieldPrevStateList() {
        return entityFieldPrevStateList;
    }

    public LocalDateTime getChangingDateTime(){
        if(entityFieldPrevStateList!=null && entityFieldPrevStateList.size()>0) {
            return entityFieldPrevStateList.get(0).getId().getChangingDateTime();
        } else{
            return null;
        }
    }

    public boolean isEmpty(){
        return entityFieldPrevStateList==null || entityFieldPrevStateList.size()==0;
    }

    @Transactional
    public void save(){
        for(EntityFieldPrevState fieldPrevState:entityFieldPrevStateList){
            fieldPrevState.save();
        }
    }
}
