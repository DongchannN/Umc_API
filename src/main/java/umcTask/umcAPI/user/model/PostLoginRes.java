package umcTask.umcAPI.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostLoginRes {
    private int userIdx;
    private String jwt;
}
