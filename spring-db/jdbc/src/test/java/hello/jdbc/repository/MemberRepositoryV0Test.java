package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {
    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        Member m1 = new Member("m1", 10000);
        repository.save(m1);

        Member findMember = repository.findById(m1.getMemberID());
        log.info("findMember={}", findMember);
        assertThat(findMember).isEqualTo(m1); // @Data 때문에 .equals()가 오버라이드 된 상태임
    
        repository.update(m1.getMemberID(), 20000);
        Member updatedMember = repository.findById(m1.getMemberID());
        assertThat(updatedMember.getMoney()).isEqualTo(20000);

        repository.delete(m1.getMemberID());
        Assertions.assertThatThrownBy(() -> repository.findById(m1.getMemberID()))
                .isInstanceOf(NoSuchElementException.class);
    }


}