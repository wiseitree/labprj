package lab.labprj.repository;

import lab.labprj.domain.Member;
import lab.labprj.mapper.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void save() {
        //given
        String email = "user4@aaa.com";
        String pw = "user4";
        String nickname = "user4";

        Member member = Member.builder()
                .email(email)
                .pw(pw)
                .nickname(nickname)
                .build();

        //when
        memberRepository.save(member);
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow();


        //then
        assertThat(email).isEqualTo(findMember.getEmail());

    }

    @Test
    public void findByEmail() {
        //given
        String email = "user3@aaa.com";

        //when
        Member member = memberRepository.findByEmail(email).orElseThrow();


        //then
        assertThat(email).isEqualTo(member.getEmail());

    }


}