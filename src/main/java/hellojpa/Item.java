package hellojpa;

import javax.persistence.*;

@Entity
//JOIN 전략
@Inheritance(strategy = InheritanceType.JOINED) //작성X시, 기본값은 SingleTable전략 (단일테이블)
@DiscriminatorColumn //Join전략 시, 어떤 Entity가 들어 있는지 DTYPE을 명시 해 준다. Join전략시 유추가 가능하나, 싱글테이블시 필수적.

//SINGLE_TABLE 전략
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //Single Table 전략. 한 테이블에 전부 들어감.
//Sigle table전략 시, @DiscriminatorColumn 을 쓰지 않아도, DTYPE이 필수적으로 들어간다. 유추가 불가 하기 때문에 자동으로 넣어주는 것이다. 운영상 별도로 써주는 것이 좋다.

//TABLE_PER_CLASS 전략 (쓰지마슈) XXXX
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) //이 전략은 @DiscriminatorColumn 필요XX. 구분할 이유가 없으니까. 전부 다 중복으로 들어 있기 때문
//insert는 편할 수 있지만, select의 경우, union all을 쳐서 굉장히 복잡하다. Join전략은 부모의 관계이기 때문에 조회가 쉽다.
//전략 JOINED, SINGLE_TABLE 어떤 것이든, 전략을 변경 할 때에는 어노테이션만 변경할 뿐이지, 다른 소스코드를 변경할 일은없다 ! (장점)
public abstract class Item { //TABLE_PER_CLASS를 위해 abstract로 진행. 원래 abstract로 해야 한다고 함.

    @Id @GeneratedValue
    private Long id;
    private String name;
    private int price;


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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
