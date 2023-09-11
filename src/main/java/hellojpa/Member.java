package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
//public class Member extends BaseEntity{
public class Member {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    @Column(name = "USERNAME")
    private String name;

    //기간 Period
    @Embedded //세트로 써줘야 한다
    private Period workPeriod;

    //주소
    @Embedded
    private Address homeAddress;
    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOOD", joinColumns =
        @JoinColumn(name = "MEMBER_ID"))
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods = new HashSet<>();


//    //값타입 매핑. AddressEntity 만들기 이전.
//    @ElementCollection
//    @CollectionTable(name = "ADDRESS", joinColumns =
//        @JoinColumn(name = "MEMBER_ID"))
//    private List<Address> addressHistory = new ArrayList<>();

    //값타입보다 훨씬 사용하기 유리
    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID")
    private List<AddressEntity> addressHistory = new ArrayList<>();

    //주소
    //한 Entity에서 같은 이름의 값을 사용하려면 @AttributeOverrides 사용한다.
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city",
                    column = @Column(name = "WORK_CITY")),
            @AttributeOverride(name = "street",
                    column = @Column(name = "WORK_STREET")),
            @AttributeOverride(name = "zipcode",
                    column = @Column(name = "WORK_ZIPCODE"))
    })
    private Address workAddress;

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

    public Period getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Period workPeriod) {
        this.workPeriod = workPeriod;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }



    public List<AddressEntity> getAddressHistory() {
        return addressHistory;
    }

    public void setAddressHistory(List<AddressEntity> addressHistory) {
        this.addressHistory = addressHistory;
    }

    public Address getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(Address workAddress) {
        this.workAddress = workAddress;
    }

/*

    /**    //TODO 객체를 테이블에 맞춰 설계 했다. 왜래키를 그대로 사용함
     *     @Column(name = "TEAM_ID")
     *     private Long teamId;
     *

    /** //TODO 객체지향적 설계를 해보자.
     *
    //JPA에 어떤 관계인지 어노테이션으로 명시한다.

    //Member입장에서 Many to One
    @ManyToOne(fetch = FetchType.LAZY) //지연로딩 : LAZY로 설정할 경우, 프록시로 조회한다
//    @ManyToOne(fetch = FetchType.EAGER) //즉시로딩 : 조인쿼리로 한방쿼리로 진짜를 다 가져옴. 프록시 필요X . --> XXX
    @JoinColumn
//    @JoinColumn(name = "TEAM_ID") //join column까지 명시.
    private Team team; //Team객체를 통째로 가져온다.

    @OneToOne //ManyToOne  과 비슷하가.
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;




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

    public Team getTeam() {
        return team;
    }

//TODO setValue는 로직이 없는 단순할때만 하고,보통은 로직이 있으나까 changeTeam 같은 메서드 를 만들어 사용.
// 현재는 만들었다가 연관관계 편의 메소드를 Team.java안에 addMember() 메소드를 따로 만들었기에 다시 추가 해줬다.
// 아무튼 둘 중의 하나만 하는 것이 좋다. 두가지 다 방법임.

    public void setTeam(Team team) {
        this.team = team;
    }

//    public void changeTeam(Team team) {
//        this.team = team;
//        team.getMembers().add(this);
//    }


    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", team=" + team +
                '}';
    }

 */
}
