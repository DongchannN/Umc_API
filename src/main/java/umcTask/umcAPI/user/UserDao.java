package umcTask.umcAPI.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import umcTask.umcAPI.user.model.*;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class UserDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final UserRowMapper userRowMapper;

    private final JdbcTemplate jdbcTemplate;

    public UserDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate, UserRowMapper userRowMapper, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.userRowMapper = userRowMapper;
        this.jdbcTemplate = jdbcTemplate;
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

    public int createUser(PostUserReq postUserReq) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource("userId", postUserReq.getUserId())
                                                            .addValue("userName", postUserReq.getUserName())
                                                            .addValue("userPw", postUserReq.getUserPw());
        int affectedRows = namedParameterJdbcTemplate.update(UserSql.INSERT, parameterSource, keyHolder);
        log.debug("{} inserted, new id = {}", affectedRows, keyHolder.getKey());
        String lastInsertIdQuery = "select last_insert_id()";

        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
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

    public GetUserRes getUserIdPw(PostLoginReq postLoginReq) {
        String qry = UserSql.SELECT + UserSql.USER_ID;
        String userId = postLoginReq.getUserId();
        User user = new User();
        Map<String, String> params = Collections.singletonMap("userId", userId);
        SqlParameterSource namedParameters = new MapSqlParameterSource("userId", userId);

        return namedParameterJdbcTemplate.queryForObject(qry, params, this.userRowMapper);
    }
}
