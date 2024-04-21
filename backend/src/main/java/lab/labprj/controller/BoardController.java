package lab.labprj.controller;

import jakarta.validation.Valid;
import lab.labprj.dto.BoardDTO;
import lab.labprj.dto.BoardSearchDTO;
import lab.labprj.dto.page.PageRequestDTO;
import lab.labprj.dto.page.PageResponseDTO;
import lab.labprj.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/{bno}")
    public BoardDTO getBoardDtl(@PathVariable Long bno) {
        return boardService.getBoardDtl(bno);
    }

    @GetMapping("/list")
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO, BoardSearchDTO boardSearchDTO) {
        log.info("#################### BoardController - /api/board/list");
        log.info("pageRequestDTO: {}", pageRequestDTO);
        log.info("boardSearchDTO: {}", boardSearchDTO);
        PageResponseDTO<BoardDTO> pageResponseDTO = boardService.getPageResponse(pageRequestDTO, boardSearchDTO);
        return pageResponseDTO;
    }

    @PostMapping("/")
    public ResponseEntity<?> register(@Valid @RequestBody BoardDTO boardDTO, BindingResult bindingResult) {
        Map<String, String> result = new HashMap<>();

        if (bindingResult.hasErrors()) {
            result.put("result", "error");
            return ResponseEntity.badRequest().body(result);
        }

        Long bno = boardService.register(boardDTO);
        result.put("bno", bno.toString());
//        Map<String, Long> map = Map.of("bno", bno);
        return ResponseEntity.ok(result);

    }


}
