package hello.jdbc.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Member {
    private String memberID;
    private int money;

    public Member(String memberID, int money) {
        this.memberID = memberID;
        this.money = money;
    }
}
