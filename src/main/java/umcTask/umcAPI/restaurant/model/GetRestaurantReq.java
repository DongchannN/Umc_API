package umcTask.umcAPI.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetRestaurantReq {
    private int userIdx;
    private String jwt;
    private int page;
}
