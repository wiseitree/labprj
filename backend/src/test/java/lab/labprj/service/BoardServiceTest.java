package lab.labprj.service;

import lab.labprj.domain.Board;
import lab.labprj.dto.BoardDTO;
import lab.labprj.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class BoardServiceTest {

    @Autowired
    BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void register() {
        //given
        String title = "title register test";
        String content = "content register test";
        String email = "user1@aaa.com";
        String writer = "user1";


        BoardDTO boardDTO = BoardDTO.builder()
                .title(title)
                .content(content)
                .email(email)
                .writer(writer)
                .build();

        boardDTO.setUploadFileNames(
                List.of(
                        UUID.randomUUID() + "_" + "Test1.jpg",
                        UUID.randomUUID() + "_" + "Test2.jpg"
                )
        );
        //when
        Long bno = boardService.register(boardDTO);

        //then
        Board board = boardRepository.findByBno(bno).get();
        log.info("board = {} ", board );
    }

    @Test
    public void getBoardDtl() {
        //given
        Long bno = 6L;

        //when
        BoardDTO boardDtl = boardService.getBoardDtl(bno);

        //then
        log.info("boardDtl = {} ", boardDtl);

    }


}