package umcTask.umcAPI.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import umcTask.umcAPI.user.model.GetUserRes;
import umcTask.umcAPI.user.model.PatchUserReq;
import umcTask.umcAPI.user.model.PostUserReq;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
@Repository
public class UserDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final UserRowMapper userRowMapper;

    public UserDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate, UserRowMapper userRowMapper) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.userRowMapper = userRowMapper;
    }
    @Autowired
    public void setDataSource(DataSource dataSource){
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    UserSql s = new UserSql();


    public List<GetUserRes> findList(){

        return namedParameterJdbcTemplate.query(s.SELECT
                , EmptySqlParameterSource.INSTANCE
                , this.userRowMapper);
    }

    public List<GetUserRes> findByStatusCode(String status) {
        String qry = UserSql.SELECT + UserSql.STATUS_CODE;
        SqlParameterSource parameterSource = new MapSqlParameterSource("status", status);
        return namedParameterJdbcTemplate.query(qry, parameterSource, this.userRowMapper);
    }

    public PostUserReq createUser(PostUserReq postUserReq) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource("userId", postUserReq.getUserId())
                                                            .addValue("userName", postUserReq.getUserName())
                                                            .addValue("userPw", postUserReq.getUserPw());
        int affectedRows = namedParameterJdbcTemplate.update(UserSql.INSERT, parameterSource, keyHolder);
        log.debug("{} inserted, new id = {}", affectedRows, keyHolder.getKey());

        return postUserReq;
    }

    public Integer deleteByIdx(Integer userIdx) {

        SqlParameterSource parameterSource = new MapSqlParameterSource("userIdx", userIdx);

        return namedParameterJdbcTemplate.update(UserSql.DELETE + UserSql.ID_CONDITION, parameterSource);
    }

    public Integer updateByIdx(PatchUserReq patchUserReq) {
        String qry = UserSql.UPDATE + UserSql.ID_CONDITION;

        SqlParameterSource parameterSource = new MapSqlParameterSource("userIdx", patchUserReq.getUserIdx())
                                                            .addValue("userName", patchUserReq.getUserName())
                                                            .addValue("userPw", patchUserReq.getUserPw());
        return namedParameterJdbcTemplate.update(qry , parameterSource);
    }
}
