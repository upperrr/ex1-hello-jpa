package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class JpaMain {
    public static void main(String[] args) {
        //emf는 DB당 하나만 생성해서 application전체에서 공유.
        //emf는 쓰레드간의 공유X (사용하고 버려야 한다.)
        //JPA의 모든 데이터 변경은 트랜젝션 안에서 실행한다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction(); //Transaction을 받아야 DB에 연결이 됩니다.
        tx.begin(); //  Transaction 시작.
  
  /** Create
   *        try {
   *             Member_1 member = new Member_1();
   *             member.setId(2L); //Long값이어서 L을 붙임.
   *             member.setName("HelloB");
   *
   *             em.persist(member); // member를 JPA에 저장한다. JPA가 mapping정보를 보고 자동으로 넣어줌.
   *
   *             tx.commit(); //Transaction commit.
   *         } catch (Exception e) {
   *             tx.rollback();
   *         } finally {
   *             em.close();
   *         }
   * //try~catch~finally로 예외를 잡아 rollback까지 해주게 하는 정석 코드 지만, 실무에서는 spring이 자동으로 해주는 코드를 사용한다.
   * */      

  /** Read
   *         try{
   *             Member_1 findMember = em.find(Member_1.class, 1L);
   *             System.out.println("findMember = " + findMember);
   *             System.out.println("findMember.getId() = " + findMember.getId());
   *             System.out.println("findMember.getName() = " + findMember.getName());
   *
   *             tx.commit();
   * */


  /** Delete
   *         try{
   *             Member_1 findMember = em.find(Member_1.class, 1L);
   *             em.remove(findMember); //remove에 찾은값을 그대로 넣어주기만 하면 된다.
   *
   *             tx.commit();
   * */

  /** Update
   *         try{
   *             Member_1 findMember = em.find(Member_1.class, 1L);
   *             findMember.setName("HelloJPA");
   *             //TODO 수정시에 em.persist(findMember);와 같이 따로 저장은 안해줘도 된다.
   *             //TODO JPA는 Transaction commit시점에 변경사항을 감지하고 update쿼리를 날린다.
   *
   *             tx.commit();
   * */

  /** Read with condition
   *   //Entity 객체를 대상으로 검색 하려면, 결국에는 조건을(JOIN)준 JPQL이 필요한 것.
   *         try{
   * //            Member_1 findMember = em.find(Member_1.class, 1L);
   *             //JPQL 시에는 객체단위로 쿼리를 짠다.
   *             List<Member_1> result = em.createQuery("select m from Member_1 as m", Member_1.class)
   *                     .setFirstResult(1)
   *                     .setMaxResults(8) //paging JAQL을 날렸을때, H2 vs Oracle 설정시 자동으로 방언으로 변환해준다.
   *                     .getResultList(); //객체를 대상으로 하기 때문에 가능한 것이다. 객체지향적 쿼리
   *
   *             for (Member_1 member : result) {
   *                 System.out.println("member.name = " + member.getName());
   *             }
   *
   *             tx.commit();
   * */




  /** 영속성 컨텍스트의 생명주기
   *         try{
   *             //비영속
   *             Member_1 member = new Member_1();
   *             member.setId(101L);
   *             member.setName("HelloJpa");
   *
   *             //영속
   *             System.out.println("==== BEFORE ====");
   *             em.persist(member); //1차 캐시에 저장
   * //            em.detach(member); //영속성 컨텍스트에서 지우는 것. //영구삭제는 DB에서 지우는 것.
   *             System.out.println("==== AFTER ====");
   *             //JPQL 쿼리는 이 이후에 찍힌다. 즉 DB에 쿼리는 commit시점에 날아간다.
   *
   *             Member_1 findMember = em.find(Member_1.class, 101L);
   *             System.out.println("findMember.getId() = " + findMember.getId());
   *             System.out.println("findMember.getName() = " + findMember.getName());
   *
   *             tx.commit();
   * */

  /** 영속(managed), 비영속(new)
   *        try{
   *
   *             //영속
   *             Member_1 findMember1 = em.find(Member_1.class, 101L);
   *             Member_1 findMember2 = em.find(Member_1.class, 101L);
   *             //두번째 조회 시에는 쿼리가 날아가면 안된다. 두번째 부터는 1차 캐시에서 조회
   *
   *             System.out.println("result = " + (findMember1 == findMember2));
   *             //true
   *             // : JPA는 영속 Entity의 동일성을 보장해 준다. 마치 Java collection에서 꺼내서 비교한 것 처럼
   *             //1차캐시로 반복가능한 읽기(repeatable read)등급의 트랜젝션 격리 수준을 DB가 아닌 APP 차원에서 지원.
   *
   *             tx.commit();
   * */

  /** Flush
   *         try{
   *
   *             //영속
   *             Member_1 member = new Member_1(200L, "member200");
   *             em.persist(member);
   *
   *             em.flush(); //강제로 영속성 컨텍스트를 flush to DB 했다.
   *             // flush하면 1차 캐시는 지워지지 않고 남아있다. 오직 쓰기지연 sql 저장소 , 변경감지 등이 DB에 반영 되는 것이다. 출
   *
   *             System.out.println("======================");
   *             tx.commit();
   * */

  /** 준영속(Detached) - 분리
   *         try{
   *
   *             //영속
   *             Member_1 member = em.find(Member_1.class, 150L);
   *             member.setName("AAAAAA");
   *
   * //            em.detach(member); //1. 특정 엔티티만 준영속 상태로 전환하는 방법
   * //            em.clear(); //2. 영속성 컨텍스트를 완전히 초기화
   *             em.close(); //3. 영속성 컨텍스트를 종료
   *
   *             Member_1 member2 = em.find(Member_1.class, 150L);
   *
   *             System.out.println("======================");
   *             tx.commit(); //아무일도 일어나지 않는다. JPA가 관리하는 영속성 컨텍스트에서 분리 되었기 때문
   * */

  /** Entity Option과 전략
   *        try{
   *
   *             Member member = new Member();
   * //            member.setId("ID_A");
   *             member.setUsername("C");
   *
   *             System.out.println("=================");
   *             em.persist(member); //GenerationType.IDENTITY전략이기 때문에 insert쿼리를 바로 날리고 commit.PK값을 DB에 넣어야 알기 때문에
   *             System.out.println("================="); //SEQ 방식은 buffer기능 사용 해서 한번에 커밋. SEQ만 살짝 얻어외서
   *
   *
   *             tx.commit();
   * */
  
  /** 연관관계 맵핑 예시1 : 객체를 테이블에 맞춰 설계 했다. 왜래키를 그대로 사용함. 객체지향스럽지 않은 모습 --> 객체로 코드짠 모습
   *        try{
   *             //저장
   *             Team team = new Team();
   *             team.setName("TeamA");
   *             em.persist(team);
   *
   *             Member member = new Member();
   *             member.setName("member1");
   * //            member.setTeamId(team.getId()); //BEFORE : 객체지향 스럽지 않네요
   *             member.setTeam(team);             //AFTER : 바로 넣어주면, JPA가 알아서 Team에서 PK꺼내서 FK로 insert
   *             em.persist(member);
   *
   *             em.flush(); //영속성컨텍스트 날리고
   *             em.clear(); //영속성컨텍스트 초기화 하고
   *             //완전히 새롭게 SELECT 해서 가져와 보자 : 이렇게 하면 insert 하고 join해서 select query 날린다.
   *
   *             Member findMember = em.find(Member.class, member.getId()); //영속성 컨텍스트니까 1차 개시에서 가지고 온 것 !
   * //            Long findTeamId = findMember.getTeamId();        //BEFORE
   * //            Team findTeam = em.find(Team.class, findTeamId); //연관관계가 없어서 상쇄적으로 계속 꺼내야 한다. 객체지향스럽지 않은 모습
   *             Team findTeam = findMember.getTeam();              //AFTER
   *             System.out.println("findTeam.getName() = " + findTeam.getName());
   *
   *             tx.commit();
   * */

  /** Controller에서 Entity를 반환하면 절대 안됨
   *
   *         try{
   *             //저장
   *             Team team = new Team();
   *             team.setName("TeamA");
   *             em.persist(team);
   *
   *             Member member = new Member();
   *             member.setName("member1");
   * //            member.changeTeam(team); //**
   *             em.persist(member);
   *
   *             team.addMember(member);
   *
   *
   *             em.flush();
   *             em.clear();  //이 두 과정 중요. DB에서 깔끔하게 값을 가져온 것.
   *
   *
   * //            Member findMember = em.find(Member.class, member.getId()); //영속성 컨텍스트니까 1차 개시에서 가지고 온 것 !
   * //            List<Member> members = findMember.getTeam().getMembers();
   * //
   * //            for(Member m : members) {
   * //                System.out.println("m" + m.getName());
   * //            }
   *
   *             Team findTeam = em.find(Team.class, team.getId()); //1차캐시
   *             List<Member> members = findTeam.getMembers();
   *
   *             //TODO Member객체와 Team객체에서 각자 만든 toString 메서드 안에 둟의 객체가 둘 다 들어있음.
   *             // SteakOverFlow err 를 발생. 양방향 매핑시에 무한 루프가 생겨버렸다. (toString(), lombok, JSON 생성 라이브러리)
   *             // Controller에서 Entity를 반환하면 절대 안됨. (1. 무한루프, 2. Entity 변경/추가 시 API spec이 바뀌어버림. 갖다 쓰는 입장에서 황당한 일) runTime까지 안가고 Compile단계에서 err 내보냄
   *             // DTO로 반환만 해줘야 한다.
   *             System.out.println("findTeam = " + findTeam);
   *
   *             tx.commit();
   * */

  /** 프록시 관련 예제
   *         try{
   *
   *             Member member = new Member();
   *             member.setName("hello");
   *
   *             em.persist(member);
   *
   *             em.flush();
   *             em.clear();
   *
   *
   * //            printMemberAndTeam(member);
   * //            printMember(member);
   *
   *
   * //            /** 1 단계 : 단순 select 할 경우 select 쿼리야 당연히 나간다
   * //             * Member findMember = em.find(Member.class, member.getId());
   * //             * System.out.println("findMember.getId() = " + findMember.getId());
   * //             * System.out.println("findMember.getName() = " + findMember.getName());
   * //             *
   *
   *
   * //            /** 2단계 : getReference 했을경우, 호출 시점에 DB에 쿼리를 날린다.
   * //             *
   * //             *             Member findMember = em.getReference(Member.class, member.getId());
   * //             *             //select 쿼리가 안나감.
   * //             *
   * //             *             //Member가 아닌, Proxy클래스. Hibernate가 만든 가짜 Class가 출력 되는 것을 볼수 있다.
   * //             *             System.out.println("findMember.getClass() = " + findMember.getClass()); //class hellojpa.Member$HibernateProxy$uk9Y77xu
   * //             *             System.out.println("findMember.getId() = " + findMember.getId());   //실제 reference를 가지면서, 실제 값을 가져옴
   * //             *             System.out.println("findMember.getName() = " + findMember.getName());
   * //             *             //실제 사용 하려고 하면, select 쿼리가 나간다 !!
   * //             *
   * //             *             //Proxy는 객체 그 자체가 아니고 통해서 가져오기 때문에 ==비교 불가, instanceof를 사용 해야 함
   * //             *
   * */


  /** 즉시로딩과 지연로딩
   *
   Team team = new Team();
   team.setName("teamA");
   em.persist(team);

   Member member1 = new Member();
   member1.setName("member1");
   member1.setTeam(team);
   em.persist(member1);


   em.flush();
   em.clear();

   Member m = em.find(Member.class, member1.getId());

   System.out.println("m.getTeam().getClass() = " + m.getTeam().getClass());
   //class hellojpa.Team$HibernateProxy$cODrFl6c --> member를 조회한 쿼리는 나가지만, team은 프록시로 조회하고

   System.out.println("=================");
   m.getTeam().getName(); //초기화 --> 실제 Team을 사용 할때, 비로소 team에 대한 DB 조회 쿼리가 나간다. LAZY LOADING
   System.out.println("m.getTeam().getName() = " + m.getTeam().getName()); //teamA --> 실제DB값. 진짜가 나온다.
   System.out.println("=================");


   //Member, Team 조회쿼리가 계속 두번 나갈 필요가 없다. 네트워크 두번 따로 타고. 성능상 손해.
   * */

  /** 고아객체
   *             Child child1 = new Child();
   *             Child child2 = new Child();
   *
   *             Parent parent = new Parent();
   *             parent.addChild(child1);
   *             parent.addChild(child2);
   *
   *             em.persist(parent); //cascade로 child객체를 연관 지었을때, 3번써주는 것 처럼 3개 다 불러온다. 대댓글 알지?
   * //            em.persist(child1);
   * //            em.persist(child2); //3번 영속성을 불러주는 것 비효율적이고 귀찮다.
   *
   *             em.flush();
   *             em.clear();
   *
   *             Parent findParent = em.find(Parent.class, parent.getId());
   *             findParent.getChildList().remove(0);
   *             //Delete Query 날라갑니다.
   *
   * */

        try{

            Member member = new Member();
            member.setName("member1");
            member.setHomeAddress(new Address("homeCity", "street", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

//            member.getAddressHistory().add(new Address("old1", "street", "10000"));
//            member.getAddressHistory().add(new Address("old2", "street", "10000"));

            member.getAddressHistory().add(new AddressEntity("old1", "street", "10000"));
            member.getAddressHistory().add(new AddressEntity("old2", "street", "10000"));


            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("======== START ========");
            Member findMember= em.find(Member.class, member.getId());


            //List와 Set은 기본값 Lazy 이기에 아래처럼 선택 해줘야 나오고, 위의 member는 embedded 이기에 그냥 나옴
//            List<Address> addressHistory = findMember.getAddressHistory();
//            for(Address address : addressHistory) {
//                System.out.println("address.getCity() = " + address.getCity());
//            }
            
            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for (String favoriteFood : favoriteFoods) {
                System.out.println("favoriteFood = " + favoriteFood);
            }

            //수정해보기 homeCity --> newCity
//            findMember.getHomeAddress().setCity("newCity"); //이렇게 하면 절대  XXXX
            Address a = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode())); //가져와서 하나만 바꿔주기

            //치킨 --> 한식
            findMember.getFavoriteFoods().remove("치킨"); //값타입 이어서 지우고 다시 해줘야 함;
            findMember.getFavoriteFoods().add("한식"); //컬렉션의 값만 바꾸어 줬는데 어떤 엔티티인지 JPA가 알아서 바꿔준다.

            //주소 바꿔보기
//            findMember.getAddressHistory().remove(new Address("old1","street", "10000")); //equals, hashCode제대로 안되있으면 안먹힌다!
//            findMember.getAddressHistory().add(new Address("newCity1", "street", "10000"));
//TODO 동작방식이 치킨 --> 한식과는 다르다. 주소 바꿔보기는 WHERE MEMBER_ID 를 DELETE 하고 INSERT 한다.
//TODO 값타입 컬렉션에 변경사항이 발생하면, 주인 엔티티와 연관된 모든 데이터를 삭제 하고, 값타입 컬랙션에 있는 현재 값을 모두 다시 저장한다.



            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
  
        emf.close();
    }

//    private static void printMember(Member member) {
//        System.out.println("member.getName() = " + member.getName());
//    }

    /** Team + Member 함께 가져오는 Method
     * */
//    private static void printMemberAndTeam(Member member) {
//        String username = member.getName();
//        System.out.println("username = " + username);
//
//        Team team = member.getTeam();
//        System.out.println("team.getName() = " + team.getName());
//    }


}
