// 연관관계 매핑
// 객체를 테이블에 맞추어 데이터 중심으로 모델링하면, 협력관계를 만들 수 없다.
// => 연관관계 매핑을 통해 참조를 사용하여 연관된 객체를 찾는다.

// 단방향, 양반향 매핑
// 테이블: 외래 키 하나로 양쪽 조인 가능, 방향 없음
// 객체: 참조용 필드가 있는 쪽으로만 참조 가능, 한쪽만 참조하면 단방향 양쪽이 서로참조하면 양방향

// 양방향 매핑
// 객체 연관관계
// ex) 회원 -> 팀 (단방향)
//     팀 -> 회원 (단방향)
// 테이블 연관관계
// 회원 <-> 팀 (양방향)
// 객체의 양방향 관계는 사실 양방향 관계가아니라 서로 다른 단 방향 관계 2개이다.
// 객체를 양방향으로 참조하려면 단방향 연관관계를 2개 만들어야 한다.
// 둘 중 하나로 외래 키를 관리해야 한다.
// 단방향 매핑만으로 이미 연관관계 매핑은 완료, 반대 방향으로 기능이 추가된 것 뿐이다. 필요할 때 추가한다(테이블에 영향을 주지 않으므로)
// 순수 객체 상태를 고려해서 항상 양쪽에 값을 설정한다. 연관관계 편의 메소드 활용
// 무한루프 주의 ex) toString(), lombok, JSON 생성 라이브러리

// 다중성
// 다대일: @ManyToOne 가장 많이 사용하는 연관관계, 외래 키가 있는 쪽이 연관관계의 주인

// 일대다: @OneToMany 1:N에서 1이 연관관계의 주인, 엔티티가 관리하는 외래키가 다른 테이블에 있다.
// 연관관계 관리를 위해 추가로 UPDATE SQL이 실행된다. => 일대다 단방향보다는 다대일 양방향 매핑을 사용하자.
// 양방향 매핑이 공식적으로 존재하지않는다. => 다대일 양방향 매핑을 사용하자.

// 일대일: @OneToOne 주 테이블이나 대상 테이블 중에 외래 키 선택 가능. 외래 키에 데이터베이스 유니크 제약조건 추가
// 양방향은 다대일 양방향 매핑 처럼 외래 키가 있는 곳이 연관관계의 주인이다. 반대편은 mappedBy 적용한다.
// 대상 테이블에 외래 키를 지정할 경우 단방향 관계는 JPA가 지원하지 않는다. 양방향 관계는 지원한다. 지연로딩 적용안됨

// 다대다: @ManyToMany 객체는 컬렉션을 사용하여 객체 2개로 다대다 관계가 가능하다.
// @JoinTable로 연결 테이블 지정 편리해보이지만 추가데이터를 추가할 수 없고 엔티티 테이블 불일치. 실무에서 사용하지 않는다.
// 중간 테이블을 이용해서 1:N, N:1 관계로 풀어야된다. @ManyToMany => @OneToMany, @manyToOne

package step03;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import step03.domain.Delivery;
import step03.domain.Item;
import step03.domain.Order;
import step03.domain.OrderItem;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("persistence");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // N:1 단방향 예제 실행
//            Member member = new Member();
//            member.setName("오승빈");
//            em.persist(member);
//
//            Order order = new Order();
//            order.setMember(member);
//            em.persist(order);

            // N:1양방향 예제 실행
//            Order order = new Order();
//            em.persist(order);
//            Item item = new Item();
//            em.persist(item);
//            OrderItem orderItem = new OrderItem();
//            orderItem.setOrder(order);
//            orderItem.setItem(item);
//            em.persist(orderItem);
//
//            System.out.println(orderItem.getOrder().getId());
//            Order order1 = em.find(Order.class, order.getId());
//
//            for(OrderItem o : order1.getOrderItem()) {
//                System.out.println(o.getId());
//            }

            // 1:1 양방향 예제 실행
            Delivery delivery = new Delivery();
            Order order = new Order();
            em.persist(order);
            order.setDelivery(delivery);
            em.persist(delivery);

            tx.commit();
        }catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
