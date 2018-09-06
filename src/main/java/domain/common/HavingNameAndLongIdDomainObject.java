package domain.common;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class HavingNameAndLongIdDomainObject<T extends HavingNameAndLongIdDomainObject>
        extends HavingLongIdDomainObject<T> {

    protected String name;

    public HavingNameAndLongIdDomainObject() {
    }

    public HavingNameAndLongIdDomainObject(Long id) {
        super(id);
    }

    public HavingNameAndLongIdDomainObject(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
