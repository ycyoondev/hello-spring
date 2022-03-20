package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest // Spring컨테이너를 사용한다. Autowired를 작동하게 한다.
@Transactional // Test는 기본적으로 롤백한다
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Rollback(value = false) // 디폴트는 DB 커밋하지 않음(Rollback true).
    public void 회원가입() throws Exception {
        Member member = new Member();
        member.setName("홍길동");

        Long savedId = memberService.join(member);

        Assertions.assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class) // 이 예외가 나오면 성공
    public void 중복_회원_예외() throws Exception {
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");

        memberService.join(member1);
        memberService.join(member2); // 이 과정에서 예외발생
        /*
        try {
            memberService.join(member2); // 이 과정에서 예외발생
        } catch (IllegalStateException e) {
            return;
        }
        */
        fail("예외가 발생해야 한다."); // 이걸만나면 Test는 무조건 실패
    }
}