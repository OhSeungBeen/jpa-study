// 객체지향 쿼리 언어
// JPA는 다양한 쿼리 방법 지원한다.
// 1. JPQL
//   JPA를 사용하면 엔티티 객체를 중심으로개발 => 문제는 검색 쿼리
//   모든 DB 데이터를 객체로 변환해서 검색하는 것은 불가능하다.
//   애플리케이션이 필요한 데이터만 DB에서 불러오려면 결국 검색 조건이 포함된 SQL이 필요하다.
//   JPA는 SQL을 추상화한 JPQL이라는 객체 지향 쿼리 언어를 제공한다. SQL 문법과 유사
//   JPQL은 엔티티 객체를 대상으로 쿼리, SQL은 데이터베이스 테이블을 대상으로 쿼리
//   테이블이 아닌 객체를 대상으로 검색하는 객체 지향 쿼리, 특정 데이터베이스 SQL에 의존하지 않는다.
//   JPQL은 결국 SQL로 변환된다.
// - criteria
//   문자가 아닌 자바코드로 JPQL을 작성할 수 있다. JPQL 빌더 역할, JPA 공식 기능
//   단점: 너무 복잡하고 실용성이 없음 => QueryDSL 사용 권장
// 2. QueryDSL
//   문자가 아닌 자바코드로 JPQL을 작성할 수 있다 JPQL 빌더 역할,
//   컴파일 시점에 문법 오류를 찾을 수 있음
//   동적 쿼리 작성이 편리함, 단순하고 쉬움 => 실무 사용 권장
// 3. 네이티브 SQL
//   JPA가 제공하는 SQL을 직접 사용하는 기능
//   JPQL로 해결할 수 없는 특정 데이터베이스에 의존적인 기능일 때 사용
// 4. JDBC API 직접사용, MyBatis, SpringjdbcTemplate 사용
//   영속성 컨텍스트를 적절한 시점에 강제로 플러시 해야된다.

// JPQL 문법
// select_문 :: =
// select_절
// from_절
// [where_절]
// [groupby_절]
// [having_절]
// [orderby_절]
// update_문 :: = update_절 [where_절]
// delete_문 :: = delete_절 [where_절]
// 엔티티와 속성은 대소문자 구분 한다.
// JPQL 키워드는 대소문자 구분하지 않는다.
// 엔티티 이름 사용, 테이블 이름이 아니다.
// 별칭은 필수 (as는 생략 가능하다.)

// 집합
// select
//   COUNT(m),   // 회원 수
//   SUM(m.age), // 나이 합
//   AVG(m.age), // 평균 나이
//   MAX(m.age), // 최대 나이
//   MIN(m.age), // 최소 나이
// from Member m

// 정렬
// GROUP BY, HAVING, ORDER BY

// TypeQuery, Query
// TypeQuery: 반환 타입이 명확할 때 사용, Query: 반환 타입이 명확하지 않을 때 사용

// 결과 조회 API
// query.getResultList(): 결과가 하나 이상일 때, 리스트 반환
// 결과가 없으면 빈리스트 반환
// query.getSingleResult(): 결과가 정확히 하나, 단일 객체 반환
// 결과가 없으면: javax.persistence.NoResultException
// 둘 이상이면: javax.persistence.NonUniqueResultException

// 파라미터 바인딩 - 이름 기준, 위치 기준
// SELECT m FROM Member m where m.username=:username
// query.setParameter("username", usernameParam);
// SELECT m FROM Member m where m.username=?1
// query.setParameter(1, usernameParam);

// 프로젝션
// SELECT 절에 조회할 대상을 지정하는 것
// 프로젝션 대상: 엔티티, 임베디드 타입, 스칼라 타입(숫자, 문자등 기본데이터 타입)
// 엔티티 프로젝션: SELECT m FROM Member m, SELECT m.team FROM Member m
// 임베디드 타입 프로젝션: SELECT m.address FROM Member m
// 스칼라 타입 프로젝션: SELECT m.username, m.age FROM member m
// 여러값 조회
// 1. Query 타입으로 조회
// 2. Object[] 타입으로 조회
// 3. new 명령어로 조회
// 단순 값을 DTO로 바로 조회 : SELECT new 패키지명.UserDTO(m.username, m.age) FROM Member m
// 패키지명을 포함한 전체 클래스 명 입력, 순서와 타입이 일치하는 생성자 필요

// 페이징 API
// JPA는 페이징을 다음 두 API로 추상화되어 있다.
// setFirstResult(int startPosition): 조회 시작 위치
// setMaxResults(int maxResult): 조회할 데이터 수

// 조인
// 내부 조인: SELECT m FROM Member m [INNER] JOIN m.team t
// 외부 조인: SELECT m FROM Member m LEFT [OUTER] JOIN m.team t
// 세타 조인: SELECT COUNT(m) from Member m, Team t WHERE m.name = t.name
// ON절을 활용한 조인
// 1. 조인 대상 필터링
// ex) JPQL: SELECT m, t FROM Member m LEFT JOIN m.team t on t.name = 'A'
//     SQL: SELECT m.*, t.* FROM Member m LEFT JOIN Team t ON m.team_id=t.id and t.name = 'A'
// 2. 연관관계가 없는 엔티티 외부 조인
// ex) JPQL: SELECT m, t FROM Member m LEFT JOIN TEAM t on m.name = t.name
//     SQL: SELECT m.*, t.* FROM Member m LEFT JOIN Team t ON m.name = t.name

// 서브쿼리
// ex) SELECT m from Member m WHERE m.age > (SELECT AVG(m2.age) FROM Member m2)
// 지원 함수
// [NOT] EXISTS (subquery): 서브쿼리에 결과가 존재하면 참
// {ALL | ANY | SOME} ALL: 모두 만족하면 참, ANY, SOME: 같은의미, 조건을 하나라도 만족하면 참
// [NOT] IN (subquery): 서브쿼리의 결과 중 하나라도 같은 것이 있으면 참
// JPA는 WHERE, HAVING 절에서만 서브 쿼리 사용 가능
// 하이버네이트에서 SELECT절도 가능
// FROM 절의 서브 쿼리는 현재 JPQL에서 불가능 => 조인으로 풀 수 있으면 풀어서 해결한다.

// JPQL 타입 표현
// 문자: 'HELLO'
// 숫자 10L(LONG) 10D(Double), 10F(Float)
// Boolean: TRUE, FALSE
// ENUM: 패키지명.Enum
// 엔티티 타입: EntityClass
// 기타: EXISTS, IN, AND, OR, NOT, =, >, >=, <, <=, <>, BETWEEN, LIKE, IS NULL

// CASE 식
// 1. 기본 CASE 식
// SELECT
//   CASE WHEN m.age <= 10 then '학생요금'
//        WHEN m.age >= 60 then '경로요금'
//        ELSE '일반요금'
//   END
// FROM member m
// 2. 단순 CASE 식
// SELECT
//   CASE t.name
//        WHEN '팀A' then '인센티브110%'
//        WHEN '팀B' then '인센티브120%'
//        ELSE '인센티브105%'
//   END
// FROM Team t
// 3. COALESCE
// 하나씩 조회해서 null이 아니면 반환
// SELECT COALESCE(m.name, '이름 없는 회원') from Member m
// 4. NULLIF
// 두 값이 같으면 null 반환, 다르면 첫번째 값 반환
// SELECT NULLIF(m.name, '관리자') from Member m

// JPQL 기본 함수
// CONCAT, SUBSTRING, TRIM, LOWER, UPPER, LENGTH, LOCATE, ABS, SQRT, MOD, SIZE, INDEX
// 사용자 정의 함수
// 하이버네이트는 사용전 방언에 추가해야 한다. 사용하는 DB방언을 상속받고, 사용자 정의 함수를 등록한다.

package step07;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import step07.domain.Address;
import step07.domain.Member;
import step07.domain.MemberDTO;
import step07.domain.Team;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("persistence");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

//            // JPQL
//            List<Member> resultList = em.createQuery(
//                    "select m From Member m where m.name like '%kim%'", Member.class)
//                .getResultList();
//
//            for(Member m : resultList) {
//                System.out.println(m.getName());
//            }
//
//            // Criteria
//            CriteriaBuilder cb = em.getCriteriaBuilder();
//            CriteriaQuery<Member> query = cb.createQuery(Member.class);
//
//            Root<Member> m = query.from(Member.class);
//            CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("name"), "kim"));
//            List<Member> resultList2 = em.createQuery(cq).getResultList();
//
//            // 네이티브 SQL
//            em.createNativeQuery("select id, city, street, zipcode, from member").getResultList();

//            // JPQL
//            // TypeQuery vs Query
//            // 반환 타입이 명확할때와 아닐 때
//            TypedQuery<Member> typeQuery = em.createQuery("select m from Member m",
//                Member.class);
//            // 결과 조회 API
//            List<Member> resultList = typeQuery.getResultList();
//
//            Query query = em.createQuery("select m.name, m.age from Member m");

//            // 파라미터 바인딩
//            Member result = em.createQuery("select m from Member m where m.name = :name", Member.class).
//            setParameter("name", "member1").getSingleResult();
//            System.out.println(result.getName());

//            // 프로젝션
//            Member member = new Member();
//            member.setName("오승빈");
//            member.setAge(10);
//            em.persist(member);
//
//            em.flush();
//            em.clear();
//
//           // 프로젝션의 결과는 영속성 컨텍스트에서 전부 다 관리된다.
//           // 엔티티 프로젝션
//           List<Member> result = em.createQuery("select m from Member m",
//                Member.class).getResultList();
//
//            Member findMember = result.get(0);
//            findMember.setAge(20);
//
//            // 임베디드 타입 프로젝션
//            em.createQuery("select o.address from Order o", Address.class).getResultList();
//            // 스칼라 타입 프로젝션
//            List resultList = em.createQuery("select distinct m.name, m.age from Member m")
//                .getResultList();
//            Object o = resultList.get(0);
//            Object[] oArray = (Object[]) o;
//            System.out.println("name = " + oArray[0]);
//            System.out.println("age = " + oArray[1]);
//
//            List<MemberDTO> result2 = em.createQuery(
//                    "select new step07.domain.MemberDTO(m.name, m.age) from Member m", MemberDTO.class)
//                .getResultList();
//            MemberDTO memberDTO = result2.get(0);
//            System.out.println(memberDTO.getName());
//            System.out.println(memberDTO.getAge());

//            // 페이징 API
//            for(int i = 0; i < 100; i++) {
//                Member member = new Member();
//                member.setName("오승빈" + i);
//                member.setAge(i);
//                em.persist(member);
//            }
//
//            em.flush();
//            em.clear();
//
//            List<Member> result = em.createQuery("select m from Member m order by m.age desc",
//                    Member.class)
//                .setFirstResult(1)
//                .setMaxResults(10)
//                .getResultList();
//            System.out.println("result.size = " + result.size());
//            for(Member member : result) {
//                System.out.println(member.getName() + " " + member.getAge());
//            }

            // 조인
            Team team = new Team();
            team.setName("A");
            em.persist(team);

            Member member = new Member();
            member.setName("오승빈");
            member.setAge(10);
            member.changeTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            // inner
            String query = "select m from Member m inner join m.team t";
            // left outer
            String query2 = "select m from Member m left outer join m.team t";
            // theta
            String query3 = "select m from Member m, Team t where m.name = t.name";
            // 조인 대상 필터링
            String query4 = "select m from Member m left join m.team t on t.name = 'A'";
            // 연관관계 없는 엔티티 외부 조인
            String query5 = "select m from Member m left join Team t on m.name = t.name";
            // CASE 식
            String query6 = "select "
                                + "case when m.age <= 10 then '학생요금' "
                                + "     when m.age >= 60 then '경로요금' "
                                + "     else '일반요금' "
                                + "end "
                            + "from Member m";
            // COALESCE
            String query7 = "select coalesce(m.name, '이름없는 회원') from Member m";
            // NULLIF
            String query8 = "select nullif(m.name, '관리자') from Member m";
            List<String> resultList = em.createQuery(query8, String.class).getResultList();
            System.out.println(resultList.get(0));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}

