package umcTask.umcAPI.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetRestaurantRes {
    private int restIdx;
    private String restName;
}
