// 엔티티 매핑
// 객체와 테이블 매핑: @Entity, @Table
// 필드와 컬럼 매핑: @Column
// 기본 키 매핑: @Id
// 연관관계 매핑: @ManyToOne, @JoinColumn

// 데이터베이스 스키마 자동 생성
// DDL을 애플리케이션 실행 시점에 데이터베이스 방언을 활용해서 적절하게 자동 생성한다.
// 자동 생성된 DDL은 개발 장비에서만 사용하고 운영서버에서 사용하지않거나 적절히 다듬은 후에 사용한다.
// DDL 생성기능은 DDL 자동 생성할 때만 사용되고 JPA의 실행 로직에 영향을 주지 않는다.

package step02;

import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("persistence");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Member member = new Member();
        member.setName("오승빈");
        member.setCreatedDate(new Date());
        member.setRoleType(RoleType.ADMIN);
        member.setDescription("안녕하세요.");
        em.persist(member);
        tx.commit();

        em.close();
        emf.close();
    }
}
