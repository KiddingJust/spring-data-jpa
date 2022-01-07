package study.datajpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Member {

    //PK를 JPA가 알아서 찾아서 넣어줌
    @Id @GeneratedValue
    private Long id;
    private String username;

    //JPA 쓰려면 기본 생성자 필요
    protected Member(){
    }
    public Member(String username){
        this.username = username;
    }
    // 추후에 username 변경 필요 시
    public void changeUsername(String username){
        this.username = username;
    }
}
