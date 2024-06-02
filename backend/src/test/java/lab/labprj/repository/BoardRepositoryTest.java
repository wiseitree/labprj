package lab.labprj.repository;

import lab.labprj.domain.Board;
import lab.labprj.domain.BoardImage;
import lab.labprj.domain.Member;
import lab.labprj.dto.BoardDTO;
import lab.labprj.dto.BoardSearchDTO;
import lab.labprj.mapper.BoardMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BoardMapper boardMapper;

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


        //2개의 이미지 파일 추가
        board.addImageString(UUID.randomUUID().toString() + "_" + "IMAGE1.jpg");
        board.addImageString(UUID.randomUUID().toString() + "_" + "IMAGE2.jpg");

        //when
        Long savedBoardBno = boardRepository.save(board);

        List<BoardImage> boardImageList = board.getImageList();
        if (boardImageList != null && boardImageList.size() > 0) {
            boardRepository.saveBoardImage(board);
        }

        //then

    }

    @Test
    public void findByBno()  {
        //given
        Long bno = 2L;

        //when
        Board board = boardRepository.findByBno(bno)
                .orElseThrow();

        //then
        log.info("board: {}", board);

    }

    @Test
    public void update(){
        //given
        Long bno = 3L;

        //when
        Board board = boardRepository.findByBno(bno)
                .orElseThrow();

        String email = "user1@aaa.com";
        String writer = "user1";
        String title = "update title";
        String content = "update content";
        LocalDateTime regTime = LocalDateTime.now();
        LocalDateTime updateTime = LocalDateTime.now();

        BoardDTO boardDTO = BoardDTO.builder()
                .writer(writer)
                .title(title)
                .content(content)
                .email(email)
                .regTime(regTime)
                .updateTime(updateTime)
                .build();

        boardMapper.update(bno, boardDTO);
        boardMapper.deleteBoardImage(bno);

        board.clearList();

        board.addImageString(UUID.randomUUID().toString() + "_" + "NEW_IMAGE1.jpg");
        board.addImageString(UUID.randomUUID().toString() + "_" + "NEW_IMAGE2.jpg");
        board.addImageString(UUID.randomUUID().toString() + "_" + "NEW_IMAGE3.jpg");

        boardRepository.saveBoardImage(board);

        //then

    }

    @Test
    public void findBoardList() {
        //given
        int offset = 0;
        int limit = 10;

        BoardSearchDTO boardSearchDTO = BoardSearchDTO.builder()
                .title("title")
                .content("content")
                .keyword("title")
                .build();


        //when
        List<Board> boardList = boardRepository.findBoardList(offset, limit, boardSearchDTO);
        List<BoardDTO> boardDTOList = new ArrayList<>();


//        for (Board board : boardList) {
//            log.info("##############################");
//            log.info("board: {}", board);
//            log.info("##############################");
//        }


        for (Board board : boardList) {
            BoardDTO boardDTO = board.toDTO(board);

            List<BoardImage> imageList = board.getImageList();

            List<String> uploadFileNames = new ArrayList<>();
            for (BoardImage boardImage : imageList) {
                String fileName = boardImage.getFileName();
                uploadFileNames.add(fileName);
            }

            boardDTO.setUploadFileNames(uploadFileNames);
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

    @Test
    public void listTest(){
        //given
        List<String> oldFileNames = new ArrayList<>();
        oldFileNames.add("user1");
        oldFileNames.add("user2");
        oldFileNames.add("user3");

        log.info("oldFileNames = {}", oldFileNames);
        //when
        log.info("oldFileNames.indexOf('user1') = {} ", oldFileNames.indexOf("asdzxc"));

        //then

    }

}
