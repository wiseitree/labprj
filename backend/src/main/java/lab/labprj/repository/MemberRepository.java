package lab.labprj.repository;

import lab.labprj.domain.Member;
import lab.labprj.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final MemberMapper memberMapper;

    public Member save(Member member){
        memberMapper.save(member);
        return member;
    }

    public Optional<Member> findByEmail(String email){
        Optional<Member> optionalMember = memberMapper.findByEmail(email);
        return optionalMember;
    }

}
