// 상속관계 매핑
// 관계형 데이터베이스에는 상속 관계가 존재하지않는다.
// 객체 상속과 유사한 슈퍼타입 서브타입 관계라는 모델링 기법이 존재한다.
// 객체의 상속 구조와 DB의 슈퍼타입 서브타입 관계를 매핑

// 슈퍼타입 서브타입 논리 모델을 실제 물리 모델로 구현 하는 방법
// 1. @Inheritance(strategy=InheritanceType.JOINED) 조인 전략: 서브타입 테이블로 변환
//    장점: 테이블 정규화, 외래키 참조 무결성 제약조건 활용 가능, 저장공간 효율화
//    단점: 조회시 조인을 많이 사용(성능 저하), 조회 쿼리가 복잡합(join 사용), 데이터 저장시 INSERT SQL 2번 호출
// 2. @Inheritance(strategy=InheritanceType.SINGLE_TABLE) 단일 테이블 전략: 통합 테이블로 변환
//    장점: 조인이 필요 없으므로 일반적으로 조회 성능이 빠름, 조회쿼리 단순함
//    단점: 자식 엔티티가 매핑한 컬럼은 모두 null 허용해야 된다. 단일 테이블에 모든 것을 저장하므로 테이블이 커질 수 있다.
// 3. @Inheritance(strategy=InheritanceType.TABLE_PER_CLASS) 구현 클래스마다 테이블 전략: 각각 테이블로 변환
//    장점: 서브 타입을 명확하게 구분해서 처리할 때 효과적이다. not null 제약조건 사용 가능
//    단점: 여러 자식 테이블을 함께 조회할 때 성능이 느리다. 자식테이블을 통합해서 쿼리하기 어려움

// 네이밍 변경 @DiscriminatorColumn(name="") @DiscriminatorValue("")

// @MappedSuperclass
// 공통 매핑 정보가 필요할 때 사용 ex) id, name
// 상속관계 매핑이 아니고 엔티티도 아니다. 테이블과 매핑 하지 않는다.
// 부모 클래스를 상속 받는 자식 클래스에 공통으로 상요하는 매핑 정보만 제공 할 뿐이다.
// 조회, 검색 불가능하다 => 직접 생성해서 사용할 일이 없으므로 추상 클래스를 권장한다.
package step04;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import step04.domain.Book;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("persistence");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Book book = new Book();
            book.setPrice(30000);
            book.setIsbn("1000");
            book.setAuthor("오승빈");
            em.persist(book);

            tx.commit();
        }catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
