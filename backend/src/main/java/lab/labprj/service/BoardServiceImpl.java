package lab.labprj.service;

import lab.labprj.domain.Board;
import lab.labprj.domain.BoardImage;
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
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
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

        Board board = dtoToEntity(boardDTO, member);

        Long bno = boardRepository.save(board);

        List<BoardImage> boardImageList = board.getImageList();
        if (boardImageList != null && boardImageList.size() > 0) {
            boardRepository.saveBoardImage(board);
        }

        return bno;
    }

    @Override
    public BoardDTO getBoardDtl(Long bno) {
        Board board = boardRepository.findByBno(bno).orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));

        BoardDTO boardDTO = entityToDTO(board);

        return boardDTO;
    }

    @Override
    public void modify(Long bno, BoardDTO boardDTO) {
        // isSameMember

        // isSameMember에 포함
        Board board = boardRepository.findByBno(boardDTO.getBno())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));

        boardDTO.setUpdateTime(LocalDateTime.now());
        boardRepository.update(bno, boardDTO);

        List<String> uploadFileNames = boardDTO.getUploadFileNames();

        if (uploadFileNames != null && uploadFileNames.size() > 0){
            board.clearList();
            boardRepository.deleteBoardImage(bno);

            uploadFileNames.stream().forEach(uploadFileName -> {
                board.addImageString(uploadFileName);
            });
            boardRepository.saveBoardImage(board);
        }
    }

    @Override
    public void remove(Long bno) {
        boardRepository.deleteByBno(bno);
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
            List<BoardImage> imageList = board.getImageList();

            List<String> uploadFileNames = createUploadFileNames(imageList);

            boardDTO.setUploadFileNames(uploadFileNames);
            boardDTOList.add(boardDTO);
        }

        PageResponseDTO<BoardDTO> pageResponseDTO = PageResponseDTO.<BoardDTO>builder()
                .dtoList(boardDTOList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(totalCount)
                .build();

        return pageResponseDTO;
    }

    private static List<String> createUploadFileNames(List<BoardImage> imageList) {
        List<String> uploadFileNames = new ArrayList<>();

        for (BoardImage boardImage : imageList) {
            String fileName = boardImage.getFileName();
            uploadFileNames.add(fileName);
        }

        return uploadFileNames;
    }

    private Board dtoToEntity(BoardDTO boardDTO, Member member){
        Board board = boardDTO.toEntity(boardDTO, member);

        List<String> uploadFileNames = boardDTO.getUploadFileNames();

        if (uploadFileNames == null)
            return board;

        for (String uploadFileName : uploadFileNames) {
            board.addImageString(uploadFileName);
        }

        return board;
    }

    private BoardDTO entityToDTO(Board board){
        BoardDTO boardDTO = board.toDTO(board);
        List<BoardImage> imageList = board.getImageList();

        if (imageList == null || imageList.size() == 0){
            return boardDTO;
        }


        List<String> fileNameList = imageList.stream().map(boardImage ->
                boardImage.getFileName()).toList();

        boardDTO.setUploadFileNames(fileNameList);

        return boardDTO;
    }



}
