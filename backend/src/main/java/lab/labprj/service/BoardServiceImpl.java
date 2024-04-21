package lab.labprj.service;

import lab.labprj.domain.Board;
import lab.labprj.domain.Member;
import lab.labprj.dto.BoardDTO;
import lab.labprj.dto.BoardSearchDTO;
import lab.labprj.dto.page.PageRequestDTO;
import lab.labprj.dto.page.PageResponseDTO;
import lab.labprj.repository.BoardRepository;
import lab.labprj.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;


    @Override
    public Long register(BoardDTO boardDTO) {
        Member member = memberRepository.findByEmail(boardDTO.getEmail()).orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
        Board board = boardDTO.toEntity(boardDTO, member);
        Long bno = boardRepository.save(board);

        return bno;
    }

    @Override
    public BoardDTO getBoardDtl(Long bno) {
        Board board = boardRepository.findByBno(bno).orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
        BoardDTO boardDTO = board.toDTO(board);
        return boardDTO;
    }

    @Override
    public PageResponseDTO<BoardDTO> getPageResponse(PageRequestDTO pageRequestDTO, BoardSearchDTO boardSearchDTO) {
        int offset = 0;
        int limit = 0;
        int totalCount = 0;

        offset = (pageRequestDTO.getPage() - 1) * pageRequestDTO.getSize();
        limit = pageRequestDTO.getSize();
        totalCount = boardRepository.getTotalCount(boardSearchDTO);
        log.info("#################### BoardServiceImpl - totalCount = {}", totalCount);


        List<Board> boardList = boardRepository.findBoardList(offset, limit, boardSearchDTO);
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (Board board : boardList) {
            BoardDTO boardDTO = board.toDTO(board);
            boardDTOList.add(boardDTO);
        }

        PageResponseDTO<BoardDTO> pageResponseDTO = PageResponseDTO.<BoardDTO>builder()
                .dtoList(boardDTOList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(totalCount)
                .build();

        return pageResponseDTO;
    }
}
