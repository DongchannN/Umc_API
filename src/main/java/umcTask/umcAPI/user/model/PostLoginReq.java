package umcTask.umcAPI.user.model;

import lombok.Data;

@Data
public class PostLoginReq {
    private String userId;
    private String userPw;

    public PostLoginReq(String userId, String userPw) {
        this.userId = userId;
        this.userPw = userPw;
    }
}
