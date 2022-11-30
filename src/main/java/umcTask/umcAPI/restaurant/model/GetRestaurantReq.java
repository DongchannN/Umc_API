package umcTask.umcAPI.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetRestaurantReq {
    private int userIdx;
    private String jwt;
    private int page;
}
