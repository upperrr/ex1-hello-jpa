package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private  Long id;
    private String name;

    //Team(FK)에서 Member(PK)로의 이동은 Team의 입장에서 1:N 의 관계일 것이다.
    //mappedBy기 때문에 FK일것. update insert는 불가. select는 가능하다,.
//    @OneToMany(mappedBy = "team") //어디와의 관계인지 명시. team으로 mapping이 되어있다.
//    private List<Member> members = new ArrayList<>(); //ArrayList로 초기화 해서 list를 넣는것은 관례. null point나지 않기 위함.


//    public void addMember(Member member) {
//        member.setTeam(this);
//        members.add(member);
//    }
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

//    public List<Member> getMembers() {
//        return members;
//    }

//    public void setMembers(List<Member> members) {
//        this.members = members;
//    }


//    @Override
//    public String toString() {
//        return "Team{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", members=" + members +
//                '}';
//    }
}
