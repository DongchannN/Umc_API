package umcTask.umcAPI.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import umcTask.umcAPI.model.User;

import java.util.List;

@Slf4j
@Repository
public class UserRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final UserRowMapper userRowMapper;
    public UserRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, UserRowMapper userRowMapper) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.userRowMapper = userRowMapper;
    }

    UserSql s = new UserSql();
    public List<User> findList(){
        log.debug("findList query : {}", s.SELECT);

        return namedParameterJdbcTemplate.query(s.SELECT
                , EmptySqlParameterSource.INSTANCE
                , this.userRowMapper);
    }

    public List<User> findByStatusCode(String status) {
        String qry = UserSql.SELECT + UserSql.STATUS_CODE;
        SqlParameterSource parameterSource = new MapSqlParameterSource("status", status);
        return namedParameterJdbcTemplate.query(qry, parameterSource, this.userRowMapper);
    }

    public User insert(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource("userId", user.getUserId())
                                                            .addValue("userName", user.getUserName())
                                                            .addValue("userPw", user.getUserPw());
                                                            //.addValue("status", user.getStatus())
                                                            //.addValue("createdAt", user.getCreatedAt());
        int affectedRows = namedParameterJdbcTemplate.update(UserSql.INSERT, parameterSource, keyHolder);
        log.debug("{} inserted, new id = {}", affectedRows, keyHolder.getKey());

        return user;
    }

    public Integer deleteByIdx(Integer userIdx) {

        SqlParameterSource parameterSource = new MapSqlParameterSource("userIdx", userIdx);

        return namedParameterJdbcTemplate.update(UserSql.DELETE + UserSql.ID_CONDITION, parameterSource);
    }

    public Integer updateByIdx(User user) {
        String qry = UserSql.UPDATE + UserSql.ID_CONDITION;

        SqlParameterSource parameterSource = new MapSqlParameterSource("userIdx", user.getUserIdx())
                                                            .addValue("userName", user.getUserName())
                                                            .addValue("userId", user.getUserId())
                                                            .addValue("userPw", user.getUserPw());
        return namedParameterJdbcTemplate.update(qry , parameterSource);
    }
}
