package umcTask.umcAPI.restaurant;

import org.springframework.web.bind.annotation.*;
import umcTask.umcAPI.JwtService;
import umcTask.umcAPI.baseResponse.BaseException;
import umcTask.umcAPI.baseResponse.BaseResponse;
import umcTask.umcAPI.baseResponse.BaseResponseStatus;
import umcTask.umcAPI.restaurant.model.GetRestaurantReq;
import umcTask.umcAPI.restaurant.model.GetRestaurantRes;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantDao restaurantDao;
    private final RestaurantService restaurantService;
    private final JwtService jwtService;

    public RestaurantController(RestaurantDao restaurantDao, RestaurantService restaurantService, JwtService jwtService) {
        this.restaurantDao = restaurantDao;
        this.restaurantService = restaurantService;
        this.jwtService = jwtService;
    }

    /**
     * 레스토랑 목록 조회
     */
    @GetMapping("/{userIdx}")
    public BaseResponse<List<GetRestaurantRes>> showRestaurant(@PathVariable("userIdx")int userIdx,@RequestParam(value = "page") int page, GetRestaurantReq getRestaurantReq) {
        String jwt = getRestaurantReq.getJwt();
        try {
            int userIdxByJwt = jwtService.getUserIdx();

            if (userIdxByJwt != userIdx) {
                return new BaseResponse<>(BaseResponseStatus.RE_LOGIN);
            }
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

        List<GetRestaurantRes> restaurantList = restaurantService.showRestaurant(getRestaurantReq);

        return new BaseResponse<>(restaurantList);
    }
}
