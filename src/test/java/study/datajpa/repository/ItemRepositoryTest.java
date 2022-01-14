package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.datajpa.entity.Item;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemRepositoryTest {

    @Autowired ItemRepository itemRepository;
    @Autowired EntityManager em;
    @Autowired MemberRepository memberRepository;


    @Test
    public void projections(){
        //given
        Team teamA = new Team("teamA");
        em.persist(teamA);

        Member m1 = new Member("m1", 0, teamA);
        Member m2 = new Member("m2", 0, teamA);
        em.persist(m1);
        em.persist(m2);

        em.flush(); em.clear();

        //when
        List<NestedClosedProjections> result = memberRepository.findProjectionsByUsername("m1", NestedClosedProjections.class);

        for(NestedClosedProjections nestedClosedProjections : result){
            String username = nestedClosedProjections.getUsername();
            System.out.println("usernameOnly = " + username);
            String teamName = nestedClosedProjections.getTeam().getName();
            System.out.println("teamName = " + teamName);
        }
    }
}