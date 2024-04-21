package lab.labprj.domain;

import lab.labprj.dto.BoardDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class Board {

    private static final Logger log = LoggerFactory.getLogger(Board.class);
    private Long bno;
    private String title;
    private String content;
    private Member member;
    private String writer;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;

    public void method(){
        log.info("#################### Board.method() is called ####################");
    }

    @Builder
    public Board(String title, String content, Member member, String writer) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.writer = writer;
    }

    public BoardDTO toDTO(Board board){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.bno)
                .title(board.title)
                .content(board.content)
                .email(board.getMember().getEmail())
                .writer(board.writer)
                .regTime(board.regTime)
                .updateTime(board.updateTime)
                .build();

        return boardDTO;
    }

}
