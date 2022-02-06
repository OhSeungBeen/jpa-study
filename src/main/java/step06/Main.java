package step06;

// 값 타입
// JPA의 데이터 타입 분류
// 1. 엔티티 타입
//    @Entity로 정의하는 객체
//    데이터가 변해도 식별자로 지속해서 추적 가능 ex) 회우너 엔티티의 키나 나이값을 변경해도 인식 가능
// 2. 값 타입
//    int, Integer String 처럼 단순히 값으로 사용하는 자바 기본 타입이나 객체
//    식별자가 없고 값만 있으므로 변경시 추적 불가 ex) 숫자 100을 200으로 변경하면 다른 값으로 대체
//    값 타입은 공유하면 안된다.
//    분류
//    1. 기본 값 타입 - 자바 기본 타입, 래퍼 클래스, String
//      생명주기를 엔티티의 의존, 항상 값을 복사함
//    2. 임베디드 타입(복합 값 타입)
//    3. 컬렉션 값 타입

// 임베디드 타입
// 새로운 값 타입을 직접 정의 (JPA는 임베디드 타입이라고한다.)
// 주로 기본 값 int String과 같은 값 타입을 모아서 만들어서 복합 값 타입이라고도 한다.
// 둘 중 하나만 선언해도 동작 한다.
// 임베디드 타입은 엔티티의 값일 뿐이다.
// 임베디드 타입을 사용하기 전과 후에 매핑하는 테이블은 같다.
// 객체와 테이블을 아주 세밀하게 매핑하는 것이 가능해진다.
// 임베디드타입은 임베디트타입, 엔티티를 가질 수 있다.
// 잘 설계한 ORM 애플리케이션은 매핑한 테이블의 수보다 클래스의 수가 더 많다.
// 장점: 재사용, 높은 응집도, 값 타입만 사용하는 의미 있는 메서드를 만들 수 있음
// @Embeddable: 값 타입을 정의하는곳에 표시, 기본생성자 필수
// @Embedded: 값 타입을 사용하는 곳에 표시

// 값 타입 공유 참조
// 항상 값을 복사해서 사용하면 공유 참조로 인해 발생하는 부작용을 피할 수 있지만 임베디드
// 타입 처럼 직접 정의한 값 타입은 자바의 기본 타입이 아니라 객체 타입이므로 값을 직접 대입하는
// 것을 막을 방법이 없다. 객체의 공유 참조는 피할 수 없다.
// 임베디드 타입 같은 값 타입을 여러 엔티티에서 공유하면 위험하다. 부작용 발생
// => 객체 타입을 수정할 수 없게 만들면 부작용을 원천 차단
// 값 타입은 불변 객체(immutable object)로 설계해야 한다.
// 불변 객채: 생성 시점 이후 절대 값을 변경할 수 없는 객체 => 생성자로만 값을 설정하고 수정자(Setter)를 만들지 않는다.

// 값 타입의 비교
// 임베디드 타입의 비교는 동등성 비교 equals()를 재정의하여 를 사용해야 한다.

// 값 타입 컬렉션
// 값 타입을 하나 이상 저장할 때 사용한다.
// @ElementCollection, @CollectionTable을 사용한다.
// 데이터베이스는 컬렉션을 같은 테이블에 저장할 수 없다.
// 컬렉션을 저장하기 위한 별도의 테이블이 필요하다.
// 값 타입 컬렉션에 변경 사항이 발생하면, 주인 엔티티와 연관된 모든 데이터를 삭제하고, 값타입 컬렉션에 있는
// 현재 값을 모두 다시 저장한다. 값 타입 컬렉션을 메핑하는 테이블은 모든 컬럼을 묶어서 기본 키를 구성해야 된다.
// NULL 입력안되고, 중복 저장안된다.
// => 값 타입 컬렉션 대안: 실무에서 상황에 따라 값 타입 컬렉션 대신에 일대다 관계를 고려한다.
// 일대다 관계를 위한 엔티티를 만들고 값 타입을 사용, 영속성 전이 + 고아 객체 제거를 사용해서 값타입 컬렉션 처럼 사용한다.

// 값 타입은 정말 값타입이라 판단될때만 사용한다!!
// 식별자가 필요하고, 지속해서 값을 추적, 변경해야 한다면 그것은 값타입이 아닌 엔티티이다!

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import step06.domain.Address;
import step06.domain.AddressEntity;
import step06.domain.Member;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("persistence");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//            // 임베디드 타입
//            Address address = new Address("city", "street", "zipcode");
//            Member member1 = new Member();
//            member1.setName("오승빈");
//            member1.setHomeAddress(address);
//            member1.setPeriod(null); // 임베디드 타입값이 null 이면 매핑한 컬럼 값은 모두 null이다.
//            em.persist(member1);
//
//            Member member2 = new Member();
//            member2.setName("홍길동");
//            member2.setHomeAddress(address);
//            member2.setPeriod(null); // 임베디드 타입값이 null 이면 매핑한 컬럼 값은 모두 null이다.
//            em.persist(member2);
//
//            // member1, member2 둘다 newcity로 Update 된다.
//            // member1.getHomeAddress().setCity("newCity"); // 임베디드 타입을 여러 엔티티에서 공유하면 부작용 발생
//            // 원천 차단 => 불변객체로 만든다 setter 삭제.
//            // 값을 바꾸고 싶을 때 새로 만들어서(또는 복사)해서 사용한다.
//            Address copyAddress = new Address("newcity", address.getStreet(), address.getZipcode());
//            member1.setHomeAddress(copyAddress);

//            // 값 타입 컬렉션
//            Member member = new Member();
//            member.setName("오승빈");
//            member.setHomeAddress(new Address("city", "street", "zipcode"));
//            // 값 타입 컬렉션은 영속성 전이(CASCADE) + 고아 객체 제거 기능을 필수로 가진다고 볼 수 있다.
//            member.getAddressHistory().add(new Address("city1", "street", "zipcode"));
//            member.getAddressHistory().add(new Address("city2", "street", "zipcode"));
//
//            em.persist(member);
//
//            em.flush();
//            em.clear();
//            // 값 타입 컬렉션은 기본값으로 지연로딩전략을 사용한다.
//            Member findMember = em.find(Member.class, member.getId()); // addressHistory빼고 member만 SELECT된다.
//            Address address = findMember.getHomeAddress();
//            findMember.setHomeAddress(new Address("newCity", address.getStreet(), address.getZipcode()));

//            // 주인 엔티티와 연관된 모든 데이터를 삭제하고, 값타입 컬렉션에 있는 현재 값을 모두 다시 저장한다
//            findMember.getAddressHistory().remove(new Address("city1", "street", "zipcode"));
//            findMember.getAddressHistory().add(new Address("newCity1", "street", "zipcode"));


            // 값 타입 컬렉션 -> 엔티티로 전환
            Member member = new Member();
            member.setName("오승빈");
            member.setHomeAddress(new Address("city", "street", "zipcode"));

            member.getAddressHistory().add(new AddressEntity("city1", "street", "zipcode"));
            member.getAddressHistory().add(new AddressEntity("city2", "street", "zipcode"));

            em.persist(member);

            tx.commit();
        }catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
