package umcTask.umcAPI.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umcTask.umcAPI.JwtService;
import umcTask.umcAPI.baseResponse.BaseException;
import umcTask.umcAPI.baseResponse.BaseResponse;
import umcTask.umcAPI.baseResponse.BaseResponseStatus;
import umcTask.umcAPI.user.model.*;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private final JwtService jwtService;

    public UserController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> projectInfo() {

        return ResponseEntity.ok("Controller is working!");
    }


    /**
     * /users/list
     * 회원들의 모든 정보를 Get.
     *
     * @return : List<GetUserRes> userList
     */
    @GetMapping("/list")
    public BaseResponse<List<GetUserRes>> userList() {
        log.debug("/userList start");
        List<GetUserRes> userList = userService.getUserList();

        return new BaseResponse<>(userList);
    }

    /**
     * 회원들의 status를 이용해 정보를 가져옴.
     *
     * @param status
     * @return : List<GetUserRes> userList
     */
    @GetMapping("/statusList/{status}")
    public BaseResponse<List<GetUserRes>> userByStatusCode(@PathVariable("status") String status) {

        List<GetUserRes> userList = userService.findUserByStatus(status);
        return new BaseResponse<>(userList);
    }

    /**
     * 회원 가입
     *
     * @param postUserReq
     * @return : ResponseEntity<PostUserRes>
     */
    @PostMapping(value = "/join")
    public BaseResponse<PostUserRes> joinUser(@RequestBody PostUserReq postUserReq) {
        try {
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 회원삭제
     *
     * @param userIdx
     * @return
     */
    @ResponseBody
    @PostMapping("/deleteUser")
    public BaseResponse<String> userDelete(@RequestParam(value = "userIdx") Integer userIdx) {
        try {
            log.debug("user idx = {}", userIdx);
            Integer deletedCnt = userService.deleteByIdx(userIdx);
            String result = deletedCnt + " deleted";
            return new BaseResponse<>(result);
        } catch (Exception exception) {
            return new BaseResponse<>(exception.getMessage());
        }
    }

    /**
     * 회원정보 변경
     *
     * @param userIdx
     * @param user
     * @return
     */
    @PatchMapping("/changeInfo/{userIdx}")
    public BaseResponse<String> changeUser(@PathVariable("userIdx") int userIdx, @RequestBody User user) {
        PatchUserReq patchUserReq = new PatchUserReq(userIdx, user.getUserName(), user.getUserPw());
        Integer updatedCnt = userService.updateByIdx(patchUserReq);
        String result = updatedCnt + " updated";
        return new BaseResponse<>(result);
    }

    /**
     * 로그인
     *
     * @param userId
     * @param userPw
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<PostLoginRes> loginUser(@RequestParam(value = "userId") String userId, @RequestParam(value = "userPw") String userPw) {

        try {
            PostLoginReq postLoginReq = new PostLoginReq(userId, userPw);
            PostLoginRes postLoginRes = userService.userLogin(postLoginReq);

            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

    }

    @GetMapping("/{userIdx}/board")
    public BaseResponse<GetBoardRes> boardPage(@PathVariable("userIdx") int userIdx, @RequestParam(value = "page") int page, GetUserReq getUserReq) throws Exception {

        String jwt = getUserReq.getJwt();

        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(BaseResponseStatus.UNAUTHORIZED);
            }

            GetBoardRes getBoardRes = userService.boardPage();
            return new BaseResponse<>(getBoardRes);
        } catch (Exception e) {
            throw e;
        }


    }
}
