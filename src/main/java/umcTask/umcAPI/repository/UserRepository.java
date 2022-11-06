package umcTask.umcAPI.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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
}
