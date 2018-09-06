package domain.common.stateChangesAccounting;

import common.DateTimeUtils;
import domain.common.DomainObject;
import domain.common.exception.EntityWithSuchIdDoesNotExistsBusinessException;
import domain.security.SecuritySubjectUtils;
import domain.userAccounts.UserAccount;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "EntitiesFieldsPrevStates")
public class EntityFieldPrevState extends DomainObject<EntityFieldPrevState,EntityFieldPrevState.EntityFieldPrevStateId>
    implements Comparable<EntityFieldPrevState>{

    @Id
    private EntityFieldPrevStateId id;

    @Lob
    @Column
    private String fieldValue;

    @ManyToOne
    @JoinColumn(name = "changerId", referencedColumnName = "id" ,nullable = false)
    private UserAccount changer;

    public EntityFieldPrevState() {}

    public EntityFieldPrevState(EntityFieldPrevStateId id, String fieldValue, UserAccount changer) {
        this.id = id;
        this.fieldValue = fieldValue;
        this.changer = changer;
    }

    public UserAccount getChanger() {
        return changer;
    }
    public void setChanger(UserAccount changer) {
        this.changer = changer;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    @Override
    public EntityFieldPrevStateId getId() {
        return id;
    }

    public void setId(EntityFieldPrevStateId id) {
        this.id = id;
    }

    @Override
    protected void beforeInsert() {
        super.beforeInsert();
        setChanger(SecuritySubjectUtils.getCurrentUserAccount());
        getId().setChangingDateTime(DateTimeUtils.now());
    }

    @Override
    protected void fillSelfForUpdate(EntityFieldPrevState self) {
        //super.fillSelfForUpdate(self); nothing to do, because entity not updateble
    }

    @Override
    protected boolean isNew() {
        return !getWithoutSoftDeletedFinder().exists(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntityFieldPrevState)) return false;
        EntityFieldPrevState that = (EntityFieldPrevState) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(fieldValue, that.fieldValue) &&
                Objects.equals(changer, that.changer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fieldValue, changer);
    }

    @Override
    public int compareTo(EntityFieldPrevState o) {
        if(o==null) {
            return 1;
        }
        if(this.equals(o)){
            return 0;
        } else{
            return this.getId().getChangingDateTime().compareTo(o.getId().getChangingDateTime());
        }
    }

    @Override
    public String toString() {
        return "EntityFieldPrevState{" +
                "id=" + id +
                ", fieldValue='" + fieldValue + '\'' +
                ", changer=" + changer +
                '}';
    }

    @Embeddable
    public static class EntityFieldPrevStateId implements Serializable {

        private String entityName;
        private String entityId;
        private String fieldName;
        private LocalDateTime changingDateTime;

        public EntityFieldPrevStateId() {
        }

        public EntityFieldPrevStateId(String entityName, String entityId, String fieldName, LocalDateTime changingDateTime) {
            this.entityName = entityName;
            this.entityId = entityId;
            this.fieldName = fieldName;
            this.changingDateTime = changingDateTime;
        }

        public String getEntityId() {
            return entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public LocalDateTime getChangingDateTime() {
            return changingDateTime;
        }

        public void setChangingDateTime(LocalDateTime changingDateTime) {
            this.changingDateTime = changingDateTime;
        }

        public String getEntityName() {
            return entityName;
        }

        public void setEntityName(String entityName) {
            this.entityName = entityName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof EntityFieldPrevStateId)) return false;
            EntityFieldPrevStateId that = (EntityFieldPrevStateId) o;
            return Objects.equals(entityName, that.entityName) &&
                    Objects.equals(entityId, that.entityId) &&
                    Objects.equals(fieldName, that.fieldName) &&
                    Objects.equals(changingDateTime, that.changingDateTime);
        }

        @Override
        public int hashCode() {
            return Objects.hash(entityName, entityId, fieldName, changingDateTime);
        }

        @Override
        public String toString() {
            return "EntityFieldPrevStateId{" +
                    "entityName='" + entityName + '\'' +
                    ", entityId='" + entityId + '\'' +
                    ", fieldName='" + fieldName + '\'' +
                    ", changingDateTime=" + changingDateTime +
                    '}';
        }
    }
}
