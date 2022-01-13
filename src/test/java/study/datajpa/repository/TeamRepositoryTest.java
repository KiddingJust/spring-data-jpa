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
//            //dto에 @Data를 해서 @ToString이 들어갔기 때문에 문자열로 출력됨.
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
//        //페이지 계산 공식 -> 꽤 복잡함. 검색해보면 있음
//        //totalPage = totalCount / size ...
//        //마지막 페이지 ...
//        //최초 페이지 ...
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
//        //스프링 데이터 JPA는 페이지를 0부터 시작.
//        // 0페이지부터 3개 가져오라는 의미
//        PageRequest pageRequest = PageRequest.of(0, 3,
//                        Sort.by(Sort.Direction.DESC, "username"));
//
//        //when
//        //반환 타입을 Page로 하면, totalCount 쿼리를 알아서 날림! (와)
//        Page<Member> page = memberRepository.findByAge(age, pageRequest);
//
//        /**
//         * API 사용 시 DTO로 변환
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
//        //when - 영속성 컨텍스트 완전히 비운 상태에서 조회해보기
//        List<Member> members = memberRepository.findAll();
////        List<Member> members = memberRepository.findMemberFetchJoin();
//
//        //N+1 문제. Member 1 & Team N
//        for(Member member : members){
//            System.out.println("member = " + member.getUsername());
//            //조회하면 HibernateProxy로 Team 클래스가 조회됨. 가짜 객체를 만들어둠
//            System.out.println("member.teamClass = " + member.getTeam().getClass());
//            //이때 DB에 쿼리를 날라서 Team 객체에 값을 채움
//            System.out.println("member.team = " + member.getTeam().getName());
//        }
//    }
//
//    @Test
//    public void queryHint(){
//        Member member1 = new Member("member1", 10);
//        memberRepository.save(member1);
//        em.flush(); //1차 캐시에 있는 결과를 DB에 날리는 것
//        em.clear(); //1차 캐시를 clear
//
//        //when
//        Member findMember = memberRepository.findById(member1.getId()).get();
//        findMember.setUsername("member2");
//
//        em.flush(); //이때 더티체킹 동작하여 db에 update 쿼리 나감
//        //그런데 더티체킹은 단점이 있음. 바로 원본이 있어야 한다는 것.
//        //즉, 객체를 2개를 가지고 있는 것. 비효율적. 메모리는 더 낭비함. 데이터 변경도 체크하고
//        //--> 변경 예정 없고, 단순 조회용이라고 명시해주면? 최적화 가능. readOnly!
//        Member findMember1 = memberRepository.findReadOnlyByUsername("member1");
//        findMember.setUsername("member2");
//
//        em.flush(); //그래서 더티체킹을 하지 않고, 데이터 변경도 되지 않음.
//    }
//
//    @Test
//    public void lock(){
//        Member member1 = new Member("member1", 10);
//        memberRepository.save(member1);
//        em.flush(); //1차 캐시에 있는 결과를 DB에 날리는 것
//        em.clear(); //1차 캐시를 clear
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