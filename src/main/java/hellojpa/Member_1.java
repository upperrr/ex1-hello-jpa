package hellojpa;

import javax.persistence.*;

//연관관계 매핑 : @ManyToOne, @JoinColumn

//@Entity가 붙은 class는 JPA가 관리, 엔티티라 한다. (필수)
// : 기본생성자 필수, final, enum, interface, inner class 사용 X, 저장할 필드에 final 사용 X
@Entity  //기본값 : 기본적으로 table명은 class명, 다르다면 아래처럼 @Table로 명시해준다.
//@Table(name = "USER") //name, catalog, schema 등의 옵션.
//@SequenceGenerator(name = "member_seq_generator",
//      sequenceName = "member_seq")
//@TableGenerator(name = "MEMBER_SEQ_GENERATOR",
//      table = "MY_SEQUENCES",
//      pkColumnValue = "MEMBER_SEQ", allocationSize = 1)
// 이 전략 사용 시 seq table이 만들어진다. 운영에서는 조금 부담스럽긴 하다 db에서 이미 쓰는게 있을듯
//@TableGenerator(
//        name = "MEMBER_SEQ_GENERATOR",
//        table = "MY_SEQUENCES",
//        pkColumnValue = "MEMBER_SEQ", allocationSize = 50)  //SEQ전략 시
//이 전략 사용 시, 미리 50개씩 확보 하기 때문에 동시성 오류 해결도 된다. 미리 값을 올려두는 방식
public class Member_1 {

/** 초기셋팅
 *     @Id //PK를 알려주는 Annotation
 *     private Long id;
 *     @Column(name = "username") //Column명이 name이 아닐때, Colunm명을 지정해 줄 수도 있다.
 *     @Column(unique = true, length = 10) //이런식으로 컬럼의 제약 조건을 줄 수도 있다. nullable = false 등
 *     private String name;
 * */

/** JPA MAPPING
 *     @Id
 *     private Long id;
 *     @Column(name = "name", insertable = true, updatable = false) //등록, 변경 가능 여부 기본값true
 *     //nullable = false, length, columnDefinition = "varchar(100) defalut 'Empty'" 직접 입력 가능
 *     // unique = true(잘 안씀. 이름이 랜덤으로 만들어져서.) 대신 @Table의 옵션으로 uniqueConstraints = 이름까지 줄 수 있다.
 *     private String username;
 *     private Integer age;
 *     @Enumerated(EnumType.STRING) //DB에는 기본적으로 Enum Type이 없으니 어노테이션을 사용 : varchar로 매핑
 *     //Ordinal : 순서저장(기본), String : 값저장. Enum에 값이추가 되면서 순서가 변경되면 데이터 유일성 사라진다.
 *     // Enum시에는 무조건 String을 기본으로 써줘야 한다고 보면 된다.
 *     private RoleType roleType;
 *     @Temporal(TemporalType.TIMESTAMP) //날짜, 시간, 날짜시간 --> DB에는 나뉘어져 있어서 매핑 따로
 *     private Date createdDate;
 *     @Temporal(TemporalType.TIMESTAMP)
 *     private Date lastModifiedDate;
 *
 *     //아래 두가지 타입은 어노테이션 없이 사용 가능. Hibernate가 알아서 매필
 *     private LocalDate testLocalDate; //LocalDate : 년월
 *     private LocalDateTime testLocalDateTime; //LocalDateTime : 년월일
 *     @Lob //varchar를 넘어서는 큰 값 : clob, 나머지는 blob으로 mapping : byte같은 것
 *     private String description;
 *     @Transient //DB랑 관계없이 memory에서만 하고 싶을 때.
 *     private int temp;
 * */


/** 기본키 매핑
 * */
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO) //자동할당 : 방언에 맞춰서
    @GeneratedValue(strategy = GenerationType.IDENTITY) //like AutoIncrement by Mysql
    /*
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator") //like sequence by Oracle
    private Long id;
    //결국은 Long을 써야 합니다. Application 전체로 봤을때 Integer, Long의 성능 차이는 미비 하나, 만약 Integer --> Long 형변환 시에 문제가 더 복잡해 지기 때문에...
    */
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MEMBER_SEQ_GENERATOR")
    private Long id;

    @Column(name = "name", nullable = false)
    private String username;


    public Member_1() {} //생성자 만들었으면, 기본생성자 만들어야 하는건 기본이쥬?

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
