package umcTask.umcAPI.user.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserReq {
    private String jwt;
}
