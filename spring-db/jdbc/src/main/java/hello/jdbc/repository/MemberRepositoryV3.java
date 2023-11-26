package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

/**
 * JDBC - DataSource, JdbcUtils 사용
 */
@Slf4j
public class MemberRepositoryV3 {

    private final DataSource dataSource;

    public MemberRepositoryV3(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values (?, ?)";

        Connection con = null;
        PreparedStatement pstst = null;

        try {
            con = getConnection();
            pstst = con.prepareStatement(sql);
            pstst.setString(1, member.getMemberID());
            pstst.setInt(2, member.getMoney());
            pstst.executeUpdate();
            return member;
        } catch (SQLException e) {
            log.error("", e);
            throw e;
        } finally {
            close(con, pstst, null);
        }
    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";
        Connection con = null;
        PreparedStatement pstst = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstst = con.prepareStatement(sql);
            pstst.setString(1, memberId);
            rs = pstst.executeQuery();

            if (rs.next()) {
                Member member = new Member();
                member.setMemberID(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found memberID=" + memberId);
            }
        } catch (SQLException e) {
            log.error("", e);
            throw e;
        } finally {
            close(con, pstst, rs);
        }
    }

    public void update(String memberId, int money) throws SQLException {
        String sql = "update member set money=? where member_id = ?";

        Connection con = null;
        PreparedStatement pstst = null;

        try {
            con = getConnection();
            pstst = con.prepareStatement(sql);
            pstst.setInt(1, money);
            pstst.setString(2, memberId);
            int resultSize = pstst.executeUpdate();
            log.info("resultSize={}", resultSize);
        } catch (SQLException e) {
            log.error("", e);
            throw e;
        } finally {
            close(con, pstst, null);
        }
    }

    public void delete(String memberId) throws SQLException {
        String sql = "delete from member where member_id=?";
        Connection con = null;
        PreparedStatement pstst = null;
        try {
            con = getConnection();
            pstst = con.prepareStatement(sql);
            pstst.setString(1, memberId);
            pstst.executeUpdate();
        } catch (SQLException e) {
            log.error("", e);
            throw e;
        } finally {
            close(con, pstst, null);
        }
    }

    private void close(Connection con, Statement stat, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stat);
        JdbcUtils.closeConnection(con);
    }

    private Connection getConnection() throws SQLException {
        Connection con = dataSource.getConnection();
        log.info("get connection={}, class={}", con, con.getClass());
        return con;
    }
}
