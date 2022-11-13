package umcTask.umcAPI.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umcTask.umcAPI.user.model.GetUserRes;
import umcTask.umcAPI.user.model.PatchUserReq;
import umcTask.umcAPI.user.model.PostUserReq;

import java.util.List;

@Service
@Slf4j
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<GetUserRes> getUserList() {
        return this.userDao.findList();
    }

    public List<GetUserRes> findUserByStatus(String status) {
        return this.userDao.findByStatusCode(status);
    }

    public PostUserReq createUser(PostUserReq postUserReq) {
        return this.userDao.createUser(postUserReq);
    }

    public Integer deleteByIdx(Integer userIdx) {
        log.debug("user idx = {}", userIdx);

        return userDao.deleteByIdx(userIdx);
    }

    public Integer updateByIdx(PatchUserReq patchUserReq) {
        log.debug("user Idx = {}", patchUserReq.getUserIdx());

        return userDao.updateByIdx(patchUserReq);
    }

}
