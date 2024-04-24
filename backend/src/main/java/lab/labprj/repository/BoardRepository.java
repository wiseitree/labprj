package lab.labprj.repository;

import lab.labprj.domain.Board;
import lab.labprj.domain.Member;
import lab.labprj.dto.BoardDTO;
import lab.labprj.dto.BoardSearchDTO;
import lab.labprj.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final BoardMapper boardMapper;

    public Long save(Board board) {
        boardMapper.save(board);
        return board.getBno();
    }

    public Optional<Board> findByBno(Long bno){
        Optional<Board> optionalBoard = boardMapper.findByBno(bno);
        return optionalBoard;
    }

    public void update(Long bno, BoardDTO boardDTO){
        boardMapper.update(bno, boardDTO);
    }

    public void deleteByBno(Long bno){
        boardMapper.deleteByBno(bno);
    }

    public void saveBoardImage(Board board){
        boardMapper.saveBoardImage(board);
    }

    public void deleteBoardImage(Long bno){
        boardMapper.deleteBoardImage(bno);
    }

    public List<Board> findBoardList(int offset, int limit, BoardSearchDTO boardSearchDTO){
        return boardMapper.findBoardList(offset, limit, boardSearchDTO);
    }

    public int getTotalCount(BoardSearchDTO boardSearchDTO){
        return boardMapper.getTotalCount(boardSearchDTO);
    }

}
