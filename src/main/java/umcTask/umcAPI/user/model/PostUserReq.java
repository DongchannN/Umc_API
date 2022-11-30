package umcTask.umcAPI.user.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원가입(POST)시 클라이언트에서 서버에 보내는 정보.
 */
@Getter
@Setter
public class PostUserReq {
    private String userName;
    private String userId;
    private String userPw;
}
