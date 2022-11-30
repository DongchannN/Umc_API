package umcTask.umcAPI.user.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class GetUserRes {

    private int userIdx;
    private String userName;
    private String userId;
    private String userPw;
    private Timestamp createdAt;
    private String status;
}
