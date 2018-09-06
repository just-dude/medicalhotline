package domain.common.stateChangesAccounting;

import dataAccess.common.Repository;
import dataAccess.common.SimpleJpaRepository;
import domain.common.SimpleFinder;
import domain.common.exception.DataAccessFailedBuisnessException;
import org.apache.commons.collections.MapUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static dataAccess.EntitiesFieldsPrevStatesSpecifications.entityFieldPrevStatesHaveEntityNameAndEntityId;

public class EntityFieldPrevStatesFinder extends SimpleFinder<EntityFieldPrevState, EntityFieldPrevState.EntityFieldPrevStateId>{

    public EntityFieldPrevStatesFinder(Repository repository) {
        super(repository);
    }

    public Map<EntityFieldPrevState,String> findAllWithNextFieldValue(String entityName, String entityId) {
        try {
            PageRequest pr = new PageRequest(0, Integer.MAX_VALUE, new Sort(new Sort.Order(Sort.Direction.ASC, "id.changingDateTime")));
            Page<EntityFieldPrevState> entityFieldPrevStatePage = repository.findAll(entityFieldPrevStatesHaveEntityNameAndEntityId(entityName, entityId), pr);
            Map<EntityFieldPrevState,String> fieldPrevStateWithNextFieldValueMap = new LinkedHashMap<>();
            Map<String,Deque<EntityFieldPrevState>> entityFieldPrevStatesByName=new HashMap<>();
            for(EntityFieldPrevState fps:entityFieldPrevStatePage.getContent()){
                if(!entityFieldPrevStatesByName.containsKey(fps.getId().getFieldName())){
                    Deque<EntityFieldPrevState> deque=new ArrayDeque<>();
                    deque.add(fps);
                    entityFieldPrevStatesByName.put(fps.getId().getFieldName(),deque);
                } else{
                    entityFieldPrevStatesByName.get(fps.getId().getFieldName()).add(fps);
                }
            }
            for (EntityFieldPrevState fps:entityFieldPrevStatePage.getContent()) {
                Deque<EntityFieldPrevState> currentDeque=entityFieldPrevStatesByName.get(fps.getId().getFieldName());
                EntityFieldPrevState fpsEq=currentDeque.removeFirst();
                if(currentDeque.size()>0) {
                    fieldPrevStateWithNextFieldValueMap.put(fpsEq, currentDeque.getFirst().getFieldValue());
                } else{
                    fieldPrevStateWithNextFieldValueMap.put(fpsEq, "currentValue");
                }
            }
            return reverseMap(fieldPrevStateWithNextFieldValueMap);
        } catch (Exception e) {
            throw new DataAccessFailedBuisnessException("An error has occured on read from data storage", e, this.getClass().getSimpleName() + ".findAllWithNextFieldValue(String entityName, String entityId)", entityName,entityId);
        }
    }

    public boolean hasAtLeastOneFieldPrevStateForEntity(String entityName, String entityId){
        return hasAtLeastOne(entityFieldPrevStatesHaveEntityNameAndEntityId(entityName,entityId));
    }

    public static Map reverseMap(Map toReverse)
    {
        LinkedHashMap reversedMap = new LinkedHashMap<>();
        List reverseOrderedKeys = new ArrayList<>(toReverse.keySet());
        Collections.reverse(reverseOrderedKeys);
        reverseOrderedKeys.forEach((key)->reversedMap.put(key,toReverse.get(key)));
        return reversedMap;
    }
}
