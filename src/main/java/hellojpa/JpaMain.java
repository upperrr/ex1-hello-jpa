package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

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
   *             Member member = new Member();
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
   *             Member findMember = em.find(Member.class, 1L);
   *             System.out.println("findMember = " + findMember);
   *             System.out.println("findMember.getId() = " + findMember.getId());
   *             System.out.println("findMember.getName() = " + findMember.getName());
   *
   *             tx.commit();
   *         } catch (Exception e) {
   *             tx.rollback();
   *         } finally {
   *             em.close();
   *         }
   * */


  /** Delete
   *         try{
   *             Member findMember = em.find(Member.class, 1L);
   *             em.remove(findMember); //remove에 찾은값을 그대로 넣어주기만 하면 된다.
   *
   *             tx.commit();
   *         } catch (Exception e) {
   *             tx.rollback();
   *         } finally {
   *             em.close();
   *         }
   * */

  /** Update
   *         try{
   *             Member findMember = em.find(Member.class, 1L);
   *             findMember.setName("HelloJPA");
   *             //TODO 수정시에 em.persist(findMember);와 같이 따로 저장은 안해줘도 된다.
   *             //TODO JPA는 Transaction commit시점에 변경사항을 감지하고 update쿼리를 날린다.
   *
   *             tx.commit();
   *         } catch (Exception e) {
   *             tx.rollback();
   *         } finally {
   *             em.close();
   *         }
   * */

  /** Read with condition
   *   //Entity 객체를 대상으로 검색 하려면, 결국에는 조건을(JOIN)준 JPQL이 필요한 것.
   *         try{
   * //            Member findMember = em.find(Member.class, 1L);
   *             //JPQL 시에는 객체단위로 쿼리를 짠다.
   *             List<Member> result = em.createQuery("select m from Member as m", Member.class)
   *                     .setFirstResult(1)
   *                     .setMaxResults(8) //paging JAQL을 날렸을때, H2 vs Oracle 설정시 자동으로 방언으로 변환해준다.
   *                     .getResultList(); //객체를 대상으로 하기 때문에 가능한 것이다. 객체지향적 쿼리
   *
   *             for (Member member : result) {
   *                 System.out.println("member.name = " + member.getName());
   *             }
   *
   *             tx.commit();
   *         } catch (Exception e) {
   *             tx.rollback();
   *         } finally {
   *             em.close();
   *         }
   * */




  /** 영속성 컨텍스트의 생명주기
   *         try{
   *             //비영속
   *             Member member = new Member();
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
   *             Member findMember = em.find(Member.class, 101L);
   *             System.out.println("findMember.getId() = " + findMember.getId());
   *             System.out.println("findMember.getName() = " + findMember.getName());
   *
   *             tx.commit();
   *         } catch (Exception e) {
   *             tx.rollback();
   *         } finally {
   *             em.close();
   *         }
   * */

  /** 영속(managed), 비영속(new)
   *        try{
   *
   *             //영속
   *             Member findMember1 = em.find(Member.class, 101L);
   *             Member findMember2 = em.find(Member.class, 101L);
   *             //두번째 조회 시에는 쿼리가 날아가면 안된다. 두번째 부터는 1차 캐시에서 조회
   *
   *             System.out.println("result = " + (findMember1 == findMember2));
   *             //true
   *             // : JPA는 영속 Entity의 동일성을 보장해 준다. 마치 Java collection에서 꺼내서 비교한 것 처럼
   *             //1차캐시로 반복가능한 읽기(repeatable read)등급의 트랜젝션 격리 수준을 DB가 아닌 APP 차원에서 지원.
   *
   *             tx.commit();
   *         } catch (Exception e) {
   *             tx.rollback();
   *         } finally {
   *             em.close();
   *         }
   * */

  /** Flush
   *         try{
   *
   *             //영속
   *             Member member = new Member(200L, "member200");
   *             em.persist(member);
   *
   *             em.flush(); //강제로 영속성 컨텍스트를 flush to DB 했다.
   *             // flush하면 1차 캐시는 지워지지 않고 남아있다. 오직 쓰기지연 sql 저장소 , 변경감지 등이 DB에 반영 되는 것이다. 출
   *
   *             System.out.println("======================");
   *             tx.commit();
   *         } catch (Exception e) {
   *             tx.rollback();
   *         } finally {
   *             em.close();
   *         }
   * */

  /** 준영속(Detached) - 분리
   *         try{
   *
   *             //영속
   *             Member member = em.find(Member.class, 150L);
   *             member.setName("AAAAAA");
   *
   * //            em.detach(member); //1. 특정 엔티티만 준영속 상태로 전환하는 방법
   * //            em.clear(); //2. 영속성 컨텍스트를 완전히 초기화
   *             em.close(); //3. 영속성 컨텍스트를 종료
   *
   *             Member member2 = em.find(Member.class, 150L);
   *
   *             System.out.println("======================");
   *             tx.commit(); //아무일도 일어나지 않는다. JPA가 관리하는 영속성 컨텍스트에서 분리 되었기 때문
   *         } catch (Exception e) {
   *             tx.rollback();
   *         } finally {
   *             em.close();
   *         }
   * */


        try{

            Member member = new Member();
//            member.setId("ID_A");
            member.setUsername("C");

            System.out.println("=================");
            em.persist(member); //GenerationType.IDENTITY전략이기 때문에 insert쿼리를 바로 날리고 commit.PK값을 DB에 넣어야 알기 때문에
            System.out.println("================="); //SEQ 방식은 buffer기능 사용 해서 한번에 커밋. SEQ만 살짝 얻어외서


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
  
        emf.close();
    }
}
