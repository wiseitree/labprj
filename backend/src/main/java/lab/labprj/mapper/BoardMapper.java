package lab.labprj.mapper;

import lab.labprj.domain.Board;
import lab.labprj.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface BoardMapper {

    void save(Board board);

    Optional<Board> findByBno(Long bno);

}
