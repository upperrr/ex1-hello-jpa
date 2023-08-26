package hellojpa;

import javax.persistence.*;

@Entity
public class Member {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    @Column(name = "USERNAME")
    private String name;

    /**    //TODO 객체를 테이블에 맞춰 설계 했다. 왜래키를 그대로 사용함
     *     @Column(name = "TEAM_ID")
     *     private Long teamId;
     * */

    /** //TODO 객체지향적 설계를 해보자.
     * */
    //JPA에 어떤 관계인지 어노테이션으로 명시한다.
    @ManyToOne //Member입장에서 Many to One
    @JoinColumn(name = "TEAM_ID") //join column까지 명시.
    private Team team; //Team객체를 통째로 가져온다.


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
}
