package hellojpa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A") //자식 클래스에서 사용 하는 DiscriminatorColumn, 디폴트가 Entity명이나 name은 변경가능.
public class Album extends Item {
    private String artist;
}
