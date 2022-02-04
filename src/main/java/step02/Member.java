package step02;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import net.bytebuddy.build.ToStringPlugin.Exclude;

// @Entity
// JPA가 관리 ,엔티티라 한다.
// 테이블과 매핑할 클래스는 @Entity가 필수이다.
// 주의
// 기본 생성자 필수(파라미터가 없는 public 또는 projected 생성자) JPA가 내부적으로 사용하기 때문이다.
// final 클래스, enum, interface, inner 클래스 사용하지 않는다.
// 저장할 필드에 final 사용하지 않는다.

// 속성
// name: 매핑할 테이블 이름, 기본값: 클래스이름
// catalog: 데이터베이스 catalog 매핑
// schema: 데이터베이스 schema 매핑
// uniqueConstraints: DDL 생성 시에 유니크 제약 조건 생성
@Entity(name="member2")
public class Member {

    // 기본 키 매핑
    // 권장하는 식별자 전략: 기본키 제약조건(not null, 유일, 변하면 안된다.)
    // 권장: Long형 + 대체키 + 키 생성전략

    // @Id: 직접 할당
    // @GeneratedValue: 자동 생성

    // IDENTITY: 데이터베이스에 위임, MYSQL, PostgreSQL등 JPA는 보통 트랜잭션 커밋 시점에 INSERT SQL 실행
    // AUTO_INCREMENT는 데이터베이스에 INSERT SQL을 실행한 이후에 ID값을 알 수 있으므로 em.persist() 시점
    // 즉시 INSERT SQL 실행하고 DB에서 식별자를 조회한다.

    // SEQUENCE: 데이터베이스 시퀀스 오브젝트사용, ORACLE (@SequenceGenerator 필요)
    // @SequenceGenerator
    // name: 식별자 생성기 이름
    // sequenceName: 데이터베이스에 등록되어 있는 시퀀스 이름, , 기본값 hibernate_sequences
    // initialValue: DDL 생성 시에만 사용됨, 시퀀스 DDL을 생성할 때 처음 시작하는 수를 지정한다. 기본값: 1
    // allocationSize: 시퀀스 한 번 호출에 증가하는 수(성능 최적화에 사용) 기본값: 50
    // catalog, schema: 데이터베이스 catalog, schema 이름
    // ex) @SequenceGenerator(
    //        name = “MEMBER_SEQ_GENERATOR",
    //        sequenceName = “MEMBER_SEQ",
    //        initialValue = 1, allocationSize = 1)

    // TABLE: 키 생성용 테이블 사용, 모든 DB에서 사용이 가능하다.
    // 키 생성 전용 테이블을 하나 만들어서 데이터베이스 시퀀스를 흉내내는 전략, 성능 저하
    // @TableGenerator
    // name: 식별자 생성기 이름
    // table: 키생성 테이블명, 기본값 hibernate_sequences
    // pkColumnName: 시퀀스 컬럼명, 기본값: sequence_name
    // valueColumnNa: 시퀀스 값 컬럼명, 기본값:  next_val
    // pkColumnValue: 키로 사용할 값 이름, 기본값: 엔티티 이름
    // initialValue: 초기 값, 마지막으로 생성된 값이 기준이다. 기본값: 0
    // allocationSize: 시퀀스 한 번 호출에 증가하는 수(성능 최적화에 사용) 기본값: 50
    // catalog, schema: 데이터베이스 catalog, schema 이름
    // uniqueConstraint: 유니크 제약 조건을 지정할 수 있다.
    
    // AUTO: 방언에 따라 자동 지정, 기본값
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // @Column: 컬럼 매핑
    // 속성
    // name: 필드와 매핑할 테이블의 컬럼 이름
    // insertable, updateable: 등록 변경 가능 여부
    // nullable: null 허용여부 설정 (not null 제약 조건)
    // unique: @Table uniqueConstraints 같지만 한 컬럼에 간단히 유니크 제약조건을 걸 때 사용
    // columnDefinition: 컬럼 정보를 직접 지정할 수 있다. ex) varchar(100)
    // length: 문자 길이 제약조건, String 타입에만 사용한다.
    // precision, scale: BigDecimal 타입에서 사용한다. precision은 소숫점을 포함한 전체 자릿수이고
    // scale은 소수의 자릿수다. double, float 타입에는 적용되지 않는다. 큰 숫자나 정밀한 소수를 다룰 때 사용
    @Column(name = "name")
    private String name;

    // @Enumerated: 자바 enum 타입 매핑
    // EnumType.ORDINAL: enum 순서를 데이터베이스에 저장
    // EnumType.STRING: enum 이름을 데이터베이스에 저장
    // 기본값 EnumType.ORDINAL
    // ORDINAL을 사용하는 것을 권장하지 않는다. 수정시 문제 발생
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    
    // @Temporal: 날짜 타입 매핑(java.util.Data, java.util.Calendar)
    // LocalDate, LocalDateTime을 사용할 때 생략 가능하다.
    // TemporalType.DATE: 날짜, 데이터베이스 date 타입과 매핑 ex) 2013-10-11
    // TemporalType.TIME: 시간, 데이터베이스 time 타입과 매핑 ex) 11:11:11
    // TemporalType.TIMESTAMP: 날짜와 시간, 데이터베이스 timestamp 타입과 매핑 ex) 2013-10-11 11:11:11
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    // @Lob: BLOB, CLOB 매핑
    // 매핑하는 필드 타입이 문자면 CLOB매핑, 나머지는 BLOB 매핑
    // CLOB: String, char[], java.sql.CLOB
    // BLOB: byte[], java.sql.BLOB
    @Lob
    private String description;

    // @Transient: 특정 필드를 컬럼에 매핑하지 않음(무시)
    // 메모리상에서만 임시로 어떤 값을 보관하고 싶을 때 사용한다.
    @Transient
    private int testId;

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

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
