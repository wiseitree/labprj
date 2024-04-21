package lab.labprj.mapper;

import lab.labprj.domain.Board;
import lab.labprj.domain.Member;
import lab.labprj.dto.BoardSearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {

    void save(Board board);

    Optional<Board> findByBno(Long bno);

    List<Board> findBoardList(@Param("offset") int offset, @Param("limit") int limit, @Param("boardSearchDTO")
    BoardSearchDTO boardSearchDTO);

    int getTotalCount(BoardSearchDTO boardSearchDTO);

}
