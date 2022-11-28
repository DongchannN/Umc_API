package umcTask.umcAPI.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umcTask.umcAPI.SHA256;
import umcTask.umcAPI.baseResponse.BaseException;
import umcTask.umcAPI.baseResponse.BaseResponseStatus;
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

    /**
     * 모든 유저 정보 반환
     * @return
     */
    public List<GetUserRes> getUserList() {
        return this.userDao.findList();
    }

    /**
     * 상태코드로 유저 정보 반환.
     * @param status
     * @return
     */
    public List<GetUserRes> findUserByStatus(String status) {
        return this.userDao.findByStatusCode(status);
    }

    /**
     * 유저 생성
     * @param postUserReq
     * @return
     * @throws NoSuchAlgorithmException
     */
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        String encryptedPw;
        try {
            encryptedPw = new SHA256().encrypt(postUserReq.getUserPw());
            postUserReq.setUserPw(encryptedPw);
        } catch (Exception ignored) {
            throw new BaseException(BaseResponseStatus.ENCRYPT_PASSWORD_ERROR);
        }

        try {
            int userIdx = userDao.createUser(postUserReq);
            String jwt = jwtService.createJwt(userIdx);
            return new PostUserRes(userIdx, jwt);
        } catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    /**
     * 유저 삭제
     * @param userIdx
     * @return
     */
    public Integer deleteByIdx(Integer userIdx) throws BaseException{
        log.debug("user idx = {}", userIdx);

        return userDao.deleteByIdx(userIdx);
    }

    /**
     * 유저 정보 갱신
     * @param patchUserReq
     * @return
     */
    public Integer updateByIdx(PatchUserReq patchUserReq) {
        log.debug("user Idx = {}", patchUserReq.getUserIdx());

        return userDao.updateByIdx(patchUserReq);
    }

    /**
     * 유저 로그인
     * @param postLoginReq
     * @return
     * @throws Exception
     */
    public PostLoginRes userLogin(PostLoginReq postLoginReq) throws BaseException {
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
            throw new BaseException(BaseResponseStatus.WRONG_PASSWORD);
        }
    }

    public GetBoardRes boardPage() {
        return new GetBoardRes(1);
    }


}
