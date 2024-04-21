package lab.labprj.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardSearchDTO {

    private String title;
    private String content;
    private String keyword;

    @Builder
    public BoardSearchDTO(String title, String content, String keyword) {
        this.title = title;
        this.content = content;
        this.keyword = keyword;
    }

}
