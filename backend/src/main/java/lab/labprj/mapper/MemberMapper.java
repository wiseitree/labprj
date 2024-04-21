package lab.labprj.mapper;

import lab.labprj.domain.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberMapper {

    void save(Member member);

    Optional<Member> findByEmail(String email);
}
