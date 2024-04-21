package lab.labprj.repository;

import lab.labprj.domain.Board;
import lab.labprj.domain.Member;
import lab.labprj.dto.BoardDTO;
import lab.labprj.dto.BoardSearchDTO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
    public void createDummy() {
        String email = "user1@aaa.com";

        for (int i = 1; i <= 135; i++) {
            Member member = memberRepository.findByEmail(email).get();
            Board board = Board.builder()
                    .title("user1 title")
                    .content("user1 content")
                    .member(member)
                    .build();

            boardRepository.save(board);
        }

    }

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

    @Test
    public void findBoardList() {
        //given
        int offset = 0;
        int limit = 10;

        BoardSearchDTO boardSearchDTO = BoardSearchDTO.builder()
                .title("title")
                .content("content")
                .keyword("test")
                .build();


        //when
        List<Board> boardList = boardRepository.findBoardList(offset, limit, boardSearchDTO);
        List<BoardDTO> boardDTOList = new ArrayList<>();


        for (Board board : boardList) {
            BoardDTO boardDTO = board.toDTO(board);
            boardDTOList.add(boardDTO);
        }

        //then
        log.info("#################### BoardRepositoryTest - findBoardList ##############################");
        for (BoardDTO boardDTO : boardDTOList) {
            log.info("boardDTO = {} ", boardDTO);
        }
        log.info("#################### BoardRepositoryTest - findBoardList ##############################");

        int totalCount = boardRepository.getTotalCount(boardSearchDTO);
        log.info("#################### totalCount = {}", totalCount);

    }

}
