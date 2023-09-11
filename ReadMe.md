# JPA
member Entity CRUD used JPA

### 영속성 컨텍스트
"엔티티를 영구 저장하는 환경"<br>
Entity --> DB (X)<br>
Entity --> 영속성 컨텍스트 에 저장 (O)
<br>
### 엔티티의 생명주기
- 비영속 (new / transient)
- 영속 (managed)
- 준영속 (detached) - 분리 
- 삭제 (removed)

--> <br>
APPICATION <-> DB 사이에 중간 계층이 있는 것.
- 1차 캐시
- 동일성 보장 (identity)
- 트랜젝션을 지원하는 쓰기지연
- 변경감지 (dirty check)
- 지연로딩

### 쓰기지연 SQL 저장소 > Flush to DB
Flush ? <br>
- 영속성 컨텍스트를 비우지 않음
- 영속성 컨텍스트의 변경 내용을 데이터 베이스에 동기화 
- 트랜젝션이라는 작업 단위가 중요 -> 커밋 직전에만 동기화 되면 됨.


### 영속성 컨텍스트를 플러시 하는 방법
- em.flush() 직접호출
- transaction commit 자동호출
- JPQL쿼리실행 자동호출


## JPA Mapping
### 데이터베이스 스키마 자동생성
- DDL은 애플리케이션 실행 시점에 자동생성 (create문으로 )
- 테이블 중심 --> 객체중심
- 데이터베이스 방언을 활용해서 데이터베이스에 맞는 적절한 DDL생성
- 이렇게 생성된 DDL은 개발 장비에서만 사용 **
  (운영은 안돼요)
- 생성된 DDL은 운영서버에서 사용하지 않거나, 적절히 다듬은 후 사용
<br>
#### *운영장비에는 절대 create, create-drop, update사용하면 안된다. 
- 개발 초기 단계에는 create, update
- test 서버 에는 update, validate 
- 스테이징과 운영 서버는 validate, none



- - -
 

## 연관관계 Mapping
객체와 RDB의 패러다임 차이를 어떻게 극복할 것인가.

- 객체와 테이블 연관관계의 차이를 이해
- 객체의 참조와 테이블의 외래키를 매핑

<br>


- 방향(Direction) : 단방향, 양방향
- 다중성(Multiplicity) : 다대일, 일대다, 일대일, 다대다
- ** 연관관계의 주인(Owner) : 객체 양방향 연관관계는 관계의 주인이 필요

객체 : 참조 사용 <br>
테이블 : FK로 JOIN사용 <br>
이 두개의 패러다임의 차이를 이해 해야 한다.

테이블 연관관계 에서 단방향과 양방향은 변화가 없다. <br>
FK로 JOIN 하면 되기 때문에. 반대로도 가능한 것이다.

객체는 참조해야 하기 때문에 FK의 방식이 통하지 않는다.<br>
두 객체에 서로의 객체를 세팅 해줘야 양방향 참조가 가능하다.

객체는 가급적으로 단방향이 좋다. <br>
양방향은 생각할 것이 많다.

## *** mappedBy
객체와 테이블이 관계를 맺는 차이

- 객체 연관관계 = 2개 (단방향->, 단방향<-)
- 테이블 연관관계 = 1개 (양방향(JOIN))


- 누구를 주인으로 할것인가 ? FK가 있는곳이 주인.

- insert는 OWNER TABLE에서만 가능하다. 하지만,

- 순수 개체 상태를 고려해서 항상 양쪽에 값을 설정하자.
  (flush(), clear()하면 1차 캐시에서 못받아오기 때문에)


- setValue는 로직이 없는 단순할때만 하고,
  보통은 로직이 있으나까 changeTeam 같은 메서드 를 만들어 사용


- JPA에서의 설계란, 단방향 맵핑으로 이미 완료가 된 것.
- 양방향 매핑은 반대방향으로 조회(객체 그래프 탐색)기능이 추가된 것 뿐
- JPQL에서 역방향으로 탐색할 일이 많음(참조할 일,,)
- 단방향 매핑을 잘 하고 양방향은 필요할 때 추가해도 됨(테이블에 영향을 주지 않음, Entity에 코드만 몇줄 추가 하면 될 일)


- - -


### 다양한 연관관계 매핑

- 다중성
- 단방향, 양방향
- 연관관계의 주인


- 다대일 @ManyToOne
- 일대다 @OneToMany : XXX
- 일대일 @OneToOne
- 다대다 @ManyToMany : XXX


- 일대일 양방향 또는 다대일 양방향을 사용하자 (권장)
- 다대다 :
- - 애초애 RDB애서 표현 불가. 연결테이블(조인테이블)을 따로 두어 다대다 관계를 억지로 만들어 내야 함.
- - 객체는 collection을 사용해서 2개의 객체로 다대다 관계를 만들 수 있음.

- 웬만하면 PK는 의미 없는 값을 Generate Value로 사용해야 유연해 진다.


### 상속관계 매핑

- 관계형 데이터베이스는 상속관계 X
- 슈퍼타입, 서브타입 관계라는 모델링 기법이 객체의 상속과 유사
- 상속관계 매핑 : 객체의 상속구조 <-> DB의 슈퍼타입, 서브타입 관계를 매핑
- 전략 : 단일테이블 or 조인테이블

### @MappedSuperclass

- 공통 매핑 정보가 필요할 때 사용(id, name)
````
  ex).
  private String createdBy;
  private LocalDateTime createdDate;
  private String lastModifiedBy;
  private LocalDateTime lastModifiedDate;
````
- RDB에서 지속적으로 넣어줬던 이런 것들... 객체로 하나 빼서 해당 어노테이션으로 가눙...! 

- - -
## 프록시
- em.find() vs em.getReference()
- em.find() : 실제 Entity 조회
- em.getReference() : 실제 Entity를 상속한 가짜 Entity
- 영속성 컨텍스트를 통해서 초기화 요청 후 실제 Entity를 생성해서 조회 하는 것
- Proxy는 객체 그 자체가 아니고 통해서 가져오기 때문에 ==비교 불가, instanceof를 사용 해야 함





## 즉시로딩과 지연로딩
- 즉시로딩 : EAGER
- 지연로딩 : LAZY
- 실무에서는 즉시로딩 XX. 가급적 지연로딩만 사용.
- WHY?
- - 즉시로딩을 적용하면, 전혀 예상치 못한 SQL이 나간다. (무수한 JOIN이...!)
- - 즉시로딩은 JPQL에서 N+1의 문제를 일으킨다.
- TODO!
- - fetch join : runtime에 동적으로 선택하여 join (JPQL 추후설명)
- - Entity Graph, Annotaion  (추후 설명)
- - Batch size 등의 방법이 있다. (추후 설명)
- WARN!
- - @ManyToOne, @OneToOne 기본 : 즉시로딩 -->LAZY로 설정(필수)
- - @OneToMany, @ManyToMany(XX) 기본 : 지연로딩
- END..
- - "그냥 지연로딩으로 다 바르고 시작 해야 한다"


## 영속성전이 : CASCADE
- 연관관계 매핑과는 아무 관련이 없음
- ALL(모두적용) , PERSIST(영속 - 저장)
- Parent와 Child가 같은 Life Cycle일 때, 단일소유 일때 사용.

## 고아객체
- 부모 엔티티와 연관관계가 끊어진 자식 엔티티를 자동으로 삭제하는 기능
- orphanRemoval = true
- 참조하는 곳이 하나일 때 (단일소유) 사용 ! (기본적으로 삭제이기에 조심)
- 특정 엔티티가 개인소유할때 사용
- @OneToOne, @OneToMany만 사용
- CASCADE REMOVAL과 동일하게 작동. 부모가 없어지면 관련된 자식 전부 지워지기 때문

## 영속성전이+고아객체, 생명주기
- 스스로 생명주기를 관리하는 엔티티는 em.persist()로 영속화, em.remove()로 제거 --> 생명주기 관리
- 두 옵션 모두 활성화 하면 부모 엔티티를 통해, 자식의 생명주기도 관리 가능
- 도메인주도설계(DDD)의 Aggregate Root 개념 구현 시 유용 

- - -

# 값타입

## 엔티티타입
- 추적가능

## 값타입
- 추적불가 

### 기본값 타입
- 생명주기를 엔티티에 의존 
- 값타입은 공유하면 X
### 임베디드 타입 (복합 값타입)
- 좌표 = x+y
- 재사용
- 높은 응집도
### 값 타입
- 단순하고 안전하게 다루기 위해 사용한다.
- 임베디드 값 타입을 여러 엔티티에서 공유하면 위험함(side effect) **
- 수정이 안되게 막고, 통으로 복사 하고 변경할 것만 바꿔서 새로 만들어줘서 원천 차단 해야 한다.
### 컬렉션 값 타입 
- 컬렉션에 임베디드 값 타입 넣어 설정한 것 
- 값타입 컬렉션은 별도의 테이블이지만 라이프사이클이 속해있는 테이블과 함께 간다. 
- 넣은 적 없는데, 소속 되어 있는 엔티티가 persist할때 자동으로 update 된다. 
- 1:N 의 cascade orphan removal 킨것과 비슷 
