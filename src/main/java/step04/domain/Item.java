package step04.domain;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy= InheritanceType.JOINED)
@DiscriminatorColumn // JOINED일 경우 선언하지 않으면 DTYPE 컬럼이 생성되지 않는다.
public abstract class Item extends BaseEntity{

    @Id @GeneratedValue
    private Long id;
    private int price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
