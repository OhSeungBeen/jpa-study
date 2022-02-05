// 프록시
// 실제 클래스를 상속 받아서 만들어진다. 겉 모양이 같다. 프록시 객체는 실제 객체의 참조(target)을 보관한다.
// 프록시 객체를 호출하면 프록시 객체는 실제 객체의 메소드를 호출한다.
// 사용하는 입장에서 진짜 객체인지 프록시 객체인지 구분하지 않고 사용하면되지만 비교 할 때 문제가 발생 할 수 있다.
// 프록시 객체는 처음 사용할 때 한 번만 초기화된다.
// 프록시 객체를 초기화 할 때, 프록시 객체가 실제 엔티티로 바뀌는 것이아니고 초기화 되면 프록시 객체를 통해 실제 엔티티 접근이 가능하다.
// 영속성 컨텍스트에 찾는 엔티티가 이미 있으면 getReference()호출 할 경우에도 실제 엔티티를 반환한다. (반대도 마찬가지)
// 영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때, 프록시를 초기화하면 문제가 발생한다. (LazyInitializationException 예외)

// 프록시 관련 함수
// 프록시 인스턴스의 초기화 여부 확인: PersistenceUnitUtil.isLoaded(Object entity)
// JPA 표준은 강제 초기화 없다. 하이버네이트에서 제공
// 프록시 강제 초기화: org.hibernate.Hibernate.initialize(entity);

// 즉시 로딩과 지연 로딩
// 어떤 객체를 조회할때, 연관관계가 걸려있는 객체도 함께 조회할지 말지를 설정 할 수있다. (프록시 조회 방법으로 해결)
// 지연로딩: fetch=FetchType.LAZY
// 즉시로딩: fetch=FetchType.EAGER
// 지연 로딩
// 사용하면 SELECT 쿼리가 따로따로 나간다. 연관 객체는 프록시 객체를 반환한다.
// JPQL의 fetch join을 통해서 해당 시점에 한방 쿼리로 가져와 쓸 수 있는 전략이나 엔티티 그래프 기능을 사용한다.
// 즉시 로딩
// 대부분의 JPA구현체는 가능하면 조인을 사용해서 SQL 한번에 함꼐 조회하려고 한다.
// 실무에서는 가급적 지연 로딩만 사용한다. 즉시로딩을 적용하면 JPQL에서 N+1 문제를 일으킨다.
// 연관 객체가 여러개라면 전부 채워서 반환시키기위해 각각 쿼리를 날려서 가져온다.
// 쿼리를 1개 날렸는데 그것 때문에 추가 쿼리 N개가 발생 할 수 있다는 의미이다.

// 연속성 전이: CASCADE
// 특정 엔티티를 영속 상태로 말들 때 연관된 엔티티도 함께 영속상태로 만들고 싶을때 사용한다.
// 연관관계를 매핑하는 것과 아무런 관련이 없다. 엔티티를 영속화할때 연관된 엔티티도 함께 영속화하는 편리함을 제공 할 뿐이다.
// ex) 부모 엔티티를 저장할 때 자식 엔티티도 함께 저장한다.
// ALL: 모두 적용
// PERSIST: 영속
// REMOVE: 삭제
// MERGE: 병합
// REFRESH: REFRESH
// DETACH: DETACH

// 고아 객체
// 부모 엔티티와 연관관계가 끊어진 자식 엔티티를 자동으로 삭제
// orphanRemoval = true
// 참조가 제거된 엔티티는 다른 곳에서 참조하지 않는 고아객체로 보고 삭제하는 기능이다. 참조하는 곳이 하나일 때 사용해야된다.
// 특정 엔티티가 개인 소유할 때 사용
// @OneToOne, @OneToMany만 가능
// 부모를 제거하면 자식이 고아가되기 때문에 부모를 제거할때 자식도 함께 제거된다. (CascadeType.REMOVE 처럼 동작)

// 영속성 전이 + 고아객체를 사용하면 부모 엔티티를 통해서 자식의 생명 주기를 관리 할 수 있다.
// CascadeType.ALL + orphanRemoval = true
package step05;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.hibernate.Hibernate;
import step05.domain.Child;
import step05.domain.Member;
import step05.domain.Parent;
import step05.domain.Team;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("persistence");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("A");
            // em.persist(team); // CASCADE 적용했으므로 연관 객체 자동 영속  
            
            Member member = new Member();
            member.setName("오승빈");
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

//            // 프록시 반환 확인
//            Member findMember = em.find(Member.class, member.getId()); // 실제 엔티티
//            em.flush();
//            em.clear();
//            Member reference = em.getReference(Member.class, member.getId()); // 프록시
//            System.out.println(findMember == reference); // false
//
//            Member findMember2 = em.find(Member.class, member.getId()); // 실제 엔티티
//            Member reference2 = em.getReference(Member.class, member.getId()); // 실제 엔티티
//            System.out.println(findMember2 == reference2); // true
//
//            Member reference = em.getReference(Member.class, member.getId());
//            em.detach(reference); // 프록시 객체를 초기화 할 수 없다. 더이상 영속성 컨텍스트의 도움을 받지 못한다.
//            System.out.println(reference.getName()); //org.hibernate.LazyInitializationException 예외

//            // 프록시 초기화 및 확인
//            Member reference = em.getReference(Member.class, member.getId());
//            boolean isLoaded = emf.getPersistenceUnitUtil().isLoaded(reference);
//            System.out.println(isLoaded); // false
//            reference.getName(); // 강제  초기화
//            // Hibernate.initialize(reference); // 강제 초기화2
//            boolean isLoaded2 = emf.getPersistenceUnitUtil().isLoaded(reference);
//            System.out.println(isLoaded2); // true

//            // 지연 로딩
//            Member findMember = em.find(Member.class, member.getId()); // member만 select
//            System.out.println(findMember.getTeam()); // 실제로 팀 객체가 조회가 필요한 시점에 쿼리가 나간다.

            // 고아객체
            Child child = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.setChild(child);
            parent.setChild(child2);

            em.persist(parent);

            em.clear();
            em.flush();

            Parent parent1 = em.find(Parent.class, parent.getId());
            parent1.getChildren().remove(0); // delete 쿼리문 실행된다.

            // em.remove(parent); // 자식도 전부 삭제된다.

            tx.commit();
        }catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
