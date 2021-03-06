//package study.datajpa.repository;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.test.annotation.Rollback;
//import study.datajpa.dto.MemberDto;
//import study.datajpa.entity.Member;
//import study.datajpa.entity.Team;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.transaction.Transactional;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//@Rollback(false)
//class TeamRepositoryTest {
//
//    @Autowired MemberJpaRepository memberJpaRepository;
//    @Autowired MemberRepository memberRepository;
//    @Autowired TeamRepository teamRepository;
//    @PersistenceContext
//    EntityManager em;
//
//    @Test
//    public void basicCRUD(){
//
//        Team teamA = new Team("teamA");
//        Team teamB = new Team("teamB");
//        teamRepository.save(teamA);
//        teamRepository.save(teamB);
//
//        Member member1 = new Member("member1", 10, teamA);
//        Member member2 = new Member("member2", 20, teamB);
//        memberRepository.save(member1);
//        memberRepository.save(member2);
//
//        Member findMember1 = memberRepository.findById(member1.getId()).get();
//        Member findMember2 = memberRepository.findById(member2.getId()).get();
//
//        Assertions.assertThat(findMember1).isEqualTo(member1);
//        Assertions.assertThat(findMember2).isEqualTo(member2);
//
//        findMember1.setUsername("member change!");
//    }
//
//    @Test
//    public void findByUsernameAndAgeGreaterThan(){
//        Member m1 = new Member("AAA", 10);
//        Member m2 = new Member("AAA", 20);
//        memberRepository.save(m1);
//        memberRepository.save(m2);
//
//        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);
//        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
//        assertThat(result.get(0).getAge()).isEqualTo(20);
//        assertThat(result.size()).isEqualTo(1);
//    }
//
//    @Test
//    public void testQuery(){
//        Member m1 = new Member("AAA", 10);
//        Member m2 = new Member("AAA", 20);
//        memberRepository.save(m1);
//        memberRepository.save(m2);
//
//        List<Member> result = memberRepository.findUser("AAA", 10);
//        assertThat(result.get(0)).isEqualTo(m1);
//    }
//
//    @Test
//    public void findMemberDto(){
//        Team teamA = new Team("teamA");
//        Team teamB = new Team("teamB");
//        teamRepository.save(teamA);
//        teamRepository.save(teamB);
//
//        Member member1 = new Member("member1", 10, teamA);
//        Member member2 = new Member("member2", 20, teamB);
//        memberRepository.save(member1);
//        memberRepository.save(member2);
//
//        List<MemberDto> memberDto = memberRepository.findMemberDto();
//        for (MemberDto dto : memberDto){
//            //dto??? @Data??? ?????? @ToString??? ???????????? ????????? ???????????? ?????????.
//            System.out.println("dto = " + dto);
//        }
//    }
//
//    @Test
//    public void findByNames(){
//        Member m1 = new Member("AAA", 10);
//        Member m2 = new Member("BBB", 20);
//        memberRepository.save(m1);
//        memberRepository.save(m2);
//
//        List<Member> result = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));
//        for(Member member : result){
//            System.out.println("member = " + member);
//        }
//    }
//
//    @Test
//    public void paging(){
//        memberJpaRepository.save(new Member("member1", 10));
//        memberJpaRepository.save(new Member("member2", 10));
//        memberJpaRepository.save(new Member("member3", 10));
//        memberJpaRepository.save(new Member("member4", 10));
//        memberJpaRepository.save(new Member("member5", 10));
//
//        int age = 10;
//        int offset = 0;
//        int limit = 3;
//
//        //when
//        List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
//        long totalCount = memberJpaRepository.totalCount(age);
//
//        //????????? ?????? ?????? -> ??? ?????????. ??????????????? ??????
//        //totalPage = totalCount / size ...
//        //????????? ????????? ...
//        //?????? ????????? ...
//
//        //then
//        assertThat(members.size()).isEqualTo(3);
//        assertThat(totalCount).isEqualTo(5);
//    }
//
//    @Test
//    public void pagingJpaData(){
//        memberRepository.save(new Member("member1", 10));
//        memberRepository.save(new Member("member2", 10));
//        memberRepository.save(new Member("member3", 10));
//        memberRepository.save(new Member("member4", 10));
//        memberRepository.save(new Member("member5", 10));
//
//        int age = 10;
//        //????????? ????????? JPA??? ???????????? 0?????? ??????.
//        // 0??????????????? 3??? ??????????????? ??????
//        PageRequest pageRequest = PageRequest.of(0, 3,
//                        Sort.by(Sort.Direction.DESC, "username"));
//
//        //when
//        //?????? ????????? Page??? ??????, totalCount ????????? ????????? ??????! (???)
//        Page<Member> page = memberRepository.findByAge(age, pageRequest);
//
//        /**
//         * API ?????? ??? DTO??? ??????
//         */
//        Page<MemberDto> toMap = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));
//
//
//        //then
//        List<Member> content = page.getContent();
//        long totalElements = page.getTotalElements();
//        for(Member member : content){
//            System.out.println("member = " + member);
//        }
//        System.out.println("totalElements = " + totalElements);
//    }
//
//    @Test
//    public void bulkUpdate() {
//        memberRepository.save(new Member("member1", 20));
//        memberRepository.save(new Member("member2", 30));
//        memberRepository.save(new Member("member3", 40));
//
//        int resultCount = memberRepository.bulkAgePlus(20);
//        System.out.println("resultCount ==> " + resultCount);
//        em.flush();
//        em.clear();
//
//        List<Member> result = memberRepository.findByUsername("member3");
//        Member member3 = result.get(0);
//        System.out.println("member3 = " + member3);
//    }
//
//    @Test
//    public void findMemberLazy(){
//        Team teamA = new Team("teamA");
//        Team teamB = new Team("teamB");
//        teamRepository.save(teamA);
//        teamRepository.save(teamB);
//        Member member1 = new Member("member1", 10, teamA);
//        Member member2 = new Member("member2", 20, teamB);
//        memberRepository.save(member1);
//        memberRepository.save(member2);
//
//        em.flush();
//        em.clear();
//
//        //when - ????????? ???????????? ????????? ?????? ???????????? ???????????????
//        List<Member> members = memberRepository.findAll();
////        List<Member> members = memberRepository.findMemberFetchJoin();
//
//        //N+1 ??????. Member 1 & Team N
//        for(Member member : members){
//            System.out.println("member = " + member.getUsername());
//            //???????????? HibernateProxy??? Team ???????????? ?????????. ?????? ????????? ????????????
//            System.out.println("member.teamClass = " + member.getTeam().getClass());
//            //?????? DB??? ????????? ????????? Team ????????? ?????? ??????
//            System.out.println("member.team = " + member.getTeam().getName());
//        }
//    }
//
//    @Test
//    public void queryHint(){
//        Member member1 = new Member("member1", 10);
//        memberRepository.save(member1);
//        em.flush(); //1??? ????????? ?????? ????????? DB??? ????????? ???
//        em.clear(); //1??? ????????? clear
//
//        //when
//        Member findMember = memberRepository.findById(member1.getId()).get();
//        findMember.setUsername("member2");
//
//        em.flush(); //?????? ???????????? ???????????? db??? update ?????? ??????
//        //????????? ??????????????? ????????? ??????. ?????? ????????? ????????? ????????? ???.
//        //???, ????????? 2?????? ????????? ?????? ???. ????????????. ???????????? ??? ?????????. ????????? ????????? ????????????
//        //--> ?????? ?????? ??????, ?????? ?????????????????? ???????????????? ????????? ??????. readOnly!
//        Member findMember1 = memberRepository.findReadOnlyByUsername("member1");
//        findMember.setUsername("member2");
//
//        em.flush(); //????????? ??????????????? ?????? ??????, ????????? ????????? ?????? ??????.
//    }
//
//    @Test
//    public void lock(){
//        Member member1 = new Member("member1", 10);
//        memberRepository.save(member1);
//        em.flush(); //1??? ????????? ?????? ????????? DB??? ????????? ???
//        em.clear(); //1??? ????????? clear
//
//        //when
//        List<Member> result = memberRepository.findLockByUsername("member1");
//    }
//
//    @Test
//    public void callCustom(){
//        List<Member> result = memberRepository.findMemberCustom();
//    }
//
//}