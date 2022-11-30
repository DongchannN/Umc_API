package umcTask.umcAPI.user.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchUserReq {
    private int userIdx;
    private String userName;
    private String userPw;

    public PatchUserReq(int userIdx, String userName, String userPw) {
        this.userIdx = userIdx;
        this.userName = userName;
        this.userPw = userPw;
    }
}
