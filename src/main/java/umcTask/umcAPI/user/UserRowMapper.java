package umcTask.umcAPI.user;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import umcTask.umcAPI.user.model.GetUserRes;
import umcTask.umcAPI.user.model.PostUserReq;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRowMapper implements RowMapper<GetUserRes> {

    @Override
    public GetUserRes mapRow(ResultSet rs, int rowNum) throws SQLException {
        GetUserRes getUserRes = new GetUserRes();
        getUserRes.setUserIdx(rs.getInt("userIdx"));
        getUserRes.setUserId(rs.getString("userId"));
        getUserRes.setUserName(rs.getString("userName"));
        getUserRes.setUserPw(rs.getString("userPw"));
        getUserRes.setCreatedAt(rs.getTimestamp("createdAt"));
        getUserRes.setStatus(rs.getString("status"));
        return getUserRes;
    }
}