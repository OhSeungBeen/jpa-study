// JPA (Java Persistence API)
// 자바 진영의 ORM 기술 표준
// ORM(Object-relational mapping) 객체 관계 매핑
// 객체는 객체대로 설계, 관계형 데이터베이스는 관계형 데이터베이스로 설계
// ORM 프레임워크가 중간에서 매핑
// JPA는 에플리케이션과 JDBC 사이에서 동작 한다.
// JPA는 인터페이스의 모음, 3가지 구현체 하이버네이트, EclipseLink, DataNucleus 존재

// JPA의 장점
// SQL 중심적인 개발에서 객체 중심으로 개발
// 생산성, 유지보수, 패라다임의 불일치 해결, 성능등
// 성능최적화 - 1차 캐시와 동일성 보장, 트랜잭셩을 지원하는 쓰기 지연, 지연 로딩

package step01;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        // JPA 구동 방식
        // Persistence -> META-INF/persistence.xml 설정 정보조회 -> EntityManagerFactory 생성
        // -> EntityManger 생성
        // EntityManagerFactory는 하나만 생성해서 애플리케이션 전체에서 공유한다.
        // EntityManager는 쓰레드간에 공유하지 않는다. (사용하고 버린다. Close)
        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("persistence");

        EntityManager em = emf.createEntityManager();
        // JPA 모든 데이터 변경은 트랜잭션 안에서 실행되어야 한다.
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Member member = new Member();
        member.setId(3L);
        member.setName("오승빈");
        em.persist(member);
        tx.commit();

        em.close();
        emf.close();
    }
}
