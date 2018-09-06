package domain.common;

import common.beanFactory.BeanFactoryProvider;
import domain.common.exception.BusinessException;
import domain.common.stateChangesAccounting.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class AccountableDomainObject<T extends DomainObject, ID extends Serializable> extends DomainObject<T,ID> {

    private List<EntityPrevStateDiff> prevStateDiffList;

    @Override
    protected void beforeUpdate(T self) {
        super.beforeUpdate(self);
        self=findNotSoftDeletedSelf();
        EntityPrevStateDiffBuilder builder=new EntityPrevStateDiffBuilder(getClass().getName(),getId().toString());
        fillEntityPrevStateDiffBuilder(builder, self,(T)this);
        EntityPrevStateDiff entityPrevStateDiff=builder.getEntityPrevStateDiff();
        entityPrevStateDiff.save();
    }

    protected abstract void fillEntityPrevStateDiffBuilder(EntityPrevStateDiffBuilder builder, T prev, T current);

    public boolean hasPrevStateDiffs(){
        return getPrevStateDiffList().size()!=0;
    }

    public List<EntityPrevStateDiff> getPrevStateDiffList(){
        if (prevStateDiffList == null) {
            EntityPrevStateDiffFinder epsdFinder = (EntityPrevStateDiffFinder) BeanFactoryProvider.getBeanFactory().getBean("entityPrevStateDiffFinder");
            if (epsdFinder == null) {
                throw new BusinessException("entityPrevStateDiffFinder not define in spring context");
            }
            return epsdFinder.findAll(getClass().getName(),getId().toString());
        } else{
            return prevStateDiffList;
        }
    }

    public boolean hasAtLeastOneFieldPrevState(){
        EntityFieldPrevStatesFinder efpsFinder = (EntityFieldPrevStatesFinder) BeanFactoryProvider.getBeanFactory().getBean("entityFieldPrevStatesFinder");
        if (efpsFinder == null) {
            throw new BusinessException("entityFieldPrevStatesFinder not define in spring context");
        }
        return efpsFinder.hasAtLeastOneFieldPrevStateForEntity(getClass().getName(),getId().toString());
    }

    public Map<EntityFieldPrevState,String> findFieldPrevStatesWithNextFieldValue(){
        EntityFieldPrevStatesFinder efpsFinder = (EntityFieldPrevStatesFinder) BeanFactoryProvider.getBeanFactory().getBean("entityFieldPrevStatesFinder");
        if (efpsFinder == null) {
            throw new BusinessException("entityFieldPrevStatesFinder not define in spring context");
        }
        return efpsFinder.findAllWithNextFieldValue(getClass().getName(),getId().toString());
    }

}
