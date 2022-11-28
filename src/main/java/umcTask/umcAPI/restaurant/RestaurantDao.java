package umcTask.umcAPI.restaurant;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import umcTask.umcAPI.JwtService;
import umcTask.umcAPI.restaurant.model.GetRestaurantReq;
import umcTask.umcAPI.restaurant.model.GetRestaurantRes;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class RestaurantDao {

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetRestaurantRes> showRestaurant(GetRestaurantReq getRestaurantReq) {
        int page = getRestaurantReq.getPage();
        String getRestByPage = "SELECT restIdx, restName FROM Restaurant WITH(NOLOCK) " +
                "ORDER BY id ASC OFFSET " + page +
                " ROWS FETCH NEXT 5 ROWS ONLY";

        return this.jdbcTemplate.query(getRestByPage,
                                        (rs, rowNum) -> new GetRestaurantRes(rs.getInt("restIdx"),
                                                                             rs.getString("restName")));
    }


}
