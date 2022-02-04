package step01;

import javax.persistence.Entity;
import javax.persistence.Id;

// entity(JPA가 관리할 객체) 생성
@Entity
public class Member {

    // 데이터베이스 primary key와 매핑
    @Id
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
