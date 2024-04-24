package lab.labprj.controller;

import jakarta.validation.Valid;
import lab.labprj.dto.BoardDTO;
import lab.labprj.dto.BoardSearchDTO;
import lab.labprj.dto.page.PageRequestDTO;
import lab.labprj.dto.page.PageResponseDTO;
import lab.labprj.service.BoardService;
import lab.labprj.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/board")
public class BoardController {

    private final CustomFileUtil fileUtil;
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
    public ResponseEntity<?> register(@Valid  BoardDTO boardDTO, BindingResult bindingResult) {
        Map<String, Long> result = new HashMap<>();

        if (bindingResult.hasErrors()) {
//            result.put("result", "error");
            return ResponseEntity.badRequest().body(Map.of("result", "error"));
        }

        log.info("#####BoardController - /api/board/register - BoardDTO {}", boardDTO);
        List<MultipartFile> files = boardDTO.getFiles();
        List<String> uploadFileNames = fileUtil.saveFiles(files);
        boardDTO.setUploadFileNames(uploadFileNames);
        log.info("#####BoardController - /api/board/register - uploadFileNames {}", uploadFileNames);

        Long bno = boardService.register(boardDTO);

        result.put("result", bno);

//        Long bno = boardService.register(boardDTO);
//        result.put("bno", bno.toString());
//        Map<String, Long> map = Map.of("bno", bno);
        return ResponseEntity.ok().body(result);

    }

    @PutMapping("/{bno}")
    public ResponseEntity<Map<String, String>> modify(@PathVariable Long bno, @Valid BoardDTO boardDTO, BindingResult bindingResult) {
        Map<String, String> result = new HashMap<>();

        if (bindingResult.hasErrors()) {
            result.put("result", "error");
            return ResponseEntity.badRequest().body(result);
        }

        BoardDTO oldBoardDTO = boardService.getBoardDtl(bno);

        List<String> oldFileNames = oldBoardDTO.getUploadFileNames();

        List<MultipartFile> files = boardDTO.getFiles();
        log.info("##########BoardController-modify files = {} ", files);

        List<String> currentUploadFileNames = fileUtil.saveFiles(files);
        log.info("##########BoardController-modify currentUploadFileNames = {} ", currentUploadFileNames);

        List<String> uploadFileNames = boardDTO.getUploadFileNames();

        if (currentUploadFileNames != null && currentUploadFileNames.size() > 0)
            uploadFileNames.addAll(currentUploadFileNames);

        //수정 작업
        boardService.modify(bno, boardDTO);

        if (oldFileNames != null && oldFileNames.size() > 0){
            List<String> removeFiles = oldFileNames
                    .stream()
                    .filter(fileName -> uploadFileNames.indexOf(fileName) == -1)
                    .collect(Collectors.toList());

            fileUtil.deleteFiles(removeFiles);
        }

        result.put("result", "success");
        return ResponseEntity.ok().body(result);

    }

    @DeleteMapping("/{bno}")
    public ResponseEntity<Map<String, String>> remove(@PathVariable Long bno){
        HashMap<String, String> result = new HashMap<>();

        List<String> oldFileNames = boardService.getBoardDtl(bno).getUploadFileNames();
        boardService.remove(bno);
        fileUtil.deleteFiles(oldFileNames);

        result.put("result", "success");
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGet(@PathVariable String fileName){
        return fileUtil.getFile(fileName);
    }

}
