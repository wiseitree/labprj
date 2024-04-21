package lab.labprj.service;

import lab.labprj.domain.Board;
import lab.labprj.dto.BoardDTO;
import lab.labprj.dto.BoardSearchDTO;
import lab.labprj.dto.page.PageRequestDTO;
import lab.labprj.dto.page.PageResponseDTO;

import java.util.Optional;

public interface BoardService {

    Long register(BoardDTO boardDTO);

    BoardDTO getBoardDtl(Long bno);

    PageResponseDTO<BoardDTO> getPageResponse(PageRequestDTO pageRequestDTO, BoardSearchDTO boardSearchDTO);

}
