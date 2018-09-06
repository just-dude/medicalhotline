package domain.common;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class HavingLongIdDomainObject<T extends HavingLongIdDomainObject> extends DomainObject<T, Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public HavingLongIdDomainObject() {
    }

    public HavingLongIdDomainObject(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    protected boolean isNew() {
        return id == null;
    }

}
