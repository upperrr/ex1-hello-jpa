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