package umcTask.umcAPI.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import umcTask.umcAPI.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUserIdx(rs.getInt("userIdx"));
        user.setUserId(rs.getString("userId"));
        user.setUserName(rs.getString("userName"));
        user.setUserPw(rs.getString("userPw"));
        user.setCreatedAt(rs.getDate("createdAt"));
        user.setStatus(rs.getString("status"));
        return user;
    }
}
