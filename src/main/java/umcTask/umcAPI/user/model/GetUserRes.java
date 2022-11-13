package umcTask.umcAPI.user.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class GetUserRes {

    private int userIdx;
    private String userName;
    private String userId;
    private String userPw;
    private Timestamp createdAt;
    private String status;
}
