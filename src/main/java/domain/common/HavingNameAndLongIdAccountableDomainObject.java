package domain.common;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class HavingNameAndLongIdAccountableDomainObject<T extends HavingNameAndLongIdAccountableDomainObject>
        extends HavingLongIdAccountableDomainObject<T> {

    protected String name;

    public HavingNameAndLongIdAccountableDomainObject() {
    }

    public HavingNameAndLongIdAccountableDomainObject(Long id) {
        super(id);
    }

    public HavingNameAndLongIdAccountableDomainObject(Long id, String name) {
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
