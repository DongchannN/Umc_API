package umcTask.umcAPI.restaurant;

import org.springframework.stereotype.Service;
import umcTask.umcAPI.JwtService;
import umcTask.umcAPI.restaurant.model.GetRestaurantReq;
import umcTask.umcAPI.restaurant.model.GetRestaurantRes;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantDao restaurantDao;
    private final JwtService jwtService;

    public RestaurantService(RestaurantDao restaurantDao, JwtService jwtService) {
        this.restaurantDao = restaurantDao;
        this.jwtService = jwtService;
    }

    public List<GetRestaurantRes> showRestaurant(GetRestaurantReq getRestaurantReq) {
        List<GetRestaurantRes> restaurantList = restaurantDao.showRestaurant(getRestaurantReq);

        return restaurantList;
    }
}
