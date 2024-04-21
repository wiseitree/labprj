package lab.labprj.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class Member {

    private String email;
    private String pw;
    private String nickname;


    @Builder
    public Member(String email, String pw, String nickname){
        this.email = email;
        this.pw = pw;
        this.nickname = nickname;
    }

}
