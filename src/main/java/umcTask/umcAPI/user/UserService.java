package umcTask.umcAPI.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import umcTask.umcAPI.SHA256;
import umcTask.umcAPI.user.model.*;
import umcTask.umcAPI.JwtService;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserService {
    private final UserDao userDao;

    private final JwtService jwtService;

    public UserService(UserDao userDao, JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
    }

    public List<GetUserRes> getUserList() {
        return this.userDao.findList();
    }

    public List<GetUserRes> findUserByStatus(String status) {
        return this.userDao.findByStatusCode(status);
    }

    public PostUserRes createUser(PostUserReq postUserReq) throws NoSuchAlgorithmException {
        SHA256 sha256 = new SHA256();
        String encryptedPw;
        try {
            encryptedPw = sha256.encrypt(postUserReq.getUserPw());
            postUserReq.setUserPw(encryptedPw);
        } catch (Exception e) {
            log.error(e.toString());
        }

        try {
            int userIdx = userDao.createUser(postUserReq);
            String jwt = jwtService.createJwt(userIdx);
            return new PostUserRes(userIdx, jwt);
        } catch (Exception e) {
            throw e;
        }
    }

    public Integer deleteByIdx(Integer userIdx) {
        log.debug("user idx = {}", userIdx);

        return userDao.deleteByIdx(userIdx);
    }

    public Integer updateByIdx(PatchUserReq patchUserReq) {
        log.debug("user Idx = {}", patchUserReq.getUserIdx());

        return userDao.updateByIdx(patchUserReq);
    }

    public PostLoginRes userLogin(PostLoginReq postLoginReq) throws Exception {
        GetUserRes user = userDao.getUserIdPw(postLoginReq);

        SHA256 sha256 = new SHA256();

        String encryptedInputPw = "";
        try {
            encryptedInputPw = sha256.encrypt(postLoginReq.getUserPw());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (user.getUserPw().equals(encryptedInputPw)) {
            int userIdx = user.getUserIdx();
            String jwt = jwtService.createJwt(userIdx);
            return new PostLoginRes(userIdx, jwt);
        } else {
            Exception exception = new Exception("Failed to Login");
            throw exception;
        }
    }


}
