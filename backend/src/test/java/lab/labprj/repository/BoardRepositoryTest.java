package lab.labprj.repository;

import lab.labprj.domain.Board;
import lab.labprj.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void save()  {
        //given
        String email = "user1@aaa.com";
        Member member = memberRepository.findByEmail(email)
                .orElseThrow();

        String title = "title test";
        String content = "content test";

        Board board = Board.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();

        //when
        Long savedBoardBno = boardRepository.save(board);

        //then
        log.info("savedBoardBno: {}", savedBoardBno);

    }

    @Test
    public void findByBno()  {
        //given
        Long bno = 1L;

        //when
        Board board = boardRepository.findByBno(1L)
                .orElseThrow();

        //then
        assertThat(board.getBno()).isEqualTo(bno);

    }

}
