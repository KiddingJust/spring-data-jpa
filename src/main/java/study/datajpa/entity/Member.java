package study.datajpa.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter
@ToString(of = {"id", "username", "age"}) //team은 하면 안됨. 무한루프 돌아감
public class Member extends JpaBaseEntity{

    //PK를 JPA가 알아서 찾아서 넣어줌
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")   //FK 명 지정
    private Team team;

    //JPA 쓰려면 기본 생성자 필요
    //@NoArgsConstructor(access = AccessLevel.PROTECTED)
    protected Member(){
    }
    public Member(String username, int age, Team team){
        this.username = username;
        this.age = age;
        if(team != null){
            changeTeam(team);
        }
    }

    public Member(String username, int age){
        this.username = username;
        this.age = age;
    }
    // 추후에 username 변경 필요 시
    public void changeUsername(String username){
        this.username = username;
    }

    //연관관계 매핑해주는 메서드
    public void changeTeam(Team team){
        //내 팀을 변경한 후에
        this.team = team;
        //team의 Member에 나를 넣어주면 끝.
        team.getMembers().add(this);
    }
}
