package umcTask.umcAPI.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetRestaurantRes {
    private int restIdx;
    private String restName;
}
