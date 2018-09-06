package domain.common.stateChangesAccounting;

import domain.common.SimpleFinder;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static dataAccess.EntitiesFieldsPrevStatesSpecifications.entityFieldPrevStatesHaveEntityNameAndEntityId;

public class EntityPrevStateDiffFinder {

    private SimpleFinder<EntityFieldPrevState,EntityFieldPrevState.EntityFieldPrevStateId> entityFieldPrevStateFinder;

    public EntityPrevStateDiffFinder(SimpleFinder<EntityFieldPrevState,EntityFieldPrevState.EntityFieldPrevStateId> entityFieldPrevStateFinder) {
        this.entityFieldPrevStateFinder=entityFieldPrevStateFinder;
    }

    public List<EntityPrevStateDiff> findAll(String entityName,String entityId) {
        PageRequest pr =new PageRequest(0,Integer.MAX_VALUE,new Sort(new Sort.Order(Sort.Direction.DESC, "id.changingDateTime")));
        Page<EntityFieldPrevState> entityFieldPrevStatePage=entityFieldPrevStateFinder.findAll(entityFieldPrevStatesHaveEntityNameAndEntityId(entityName,entityId),pr);
        List<EntityPrevStateDiff> entityPrevStateDiffList = new ArrayList<>();
        if(entityFieldPrevStatePage.getContent().size()==0){
            return entityPrevStateDiffList;
        }
        LocalDateTime prevChangingDateTime=entityFieldPrevStatePage.getContent().get(0).getId().getChangingDateTime();
        List<EntityFieldPrevState> entityFieldPrevStateListForSomeState=new ArrayList<>();
        for(EntityFieldPrevState efps:entityFieldPrevStatePage.getContent()){
            if(!efps.getId().getChangingDateTime().equals(prevChangingDateTime)){
                entityPrevStateDiffList.add(new EntityPrevStateDiff(entityFieldPrevStateListForSomeState));
                entityFieldPrevStateListForSomeState=new ArrayList<>();
                prevChangingDateTime=efps.getId().getChangingDateTime();
            }
            entityFieldPrevStateListForSomeState.add(efps);
        }
        entityPrevStateDiffList.add(new EntityPrevStateDiff(entityFieldPrevStateListForSomeState));
        return entityPrevStateDiffList;
    }
}
