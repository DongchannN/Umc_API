package umcTask.umcAPI.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umcTask.umcAPI.user.model.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public ResponseEntity<String> projectInfo() {
        return ResponseEntity.ok("Controller is working!");
    }


    /**
     * /users/list
     * 회원들의 모든 정보를 Get.
     * @return : List<GetUserRes> userList
     */
    @GetMapping("/list")
    public Object userList() {
        log.debug("/userList start");
        List<GetUserRes> userList = userService.getUserList();

        return userList;
    }

    /**
     * 회원들의 status를 이용해 정보를 가져옴.
     * @param status
     * @return : List<GetUserRes> userList
     */
    @GetMapping("/statusList/{status}")
    public Object userByStatusCode(@PathVariable("status") String status) {
        List<GetUserRes> userList = userService.findUserByStatus(status);
        return userList;
    }

    /**
     * 회원 가입
     * @param postUserReq
     * @return : ResponseEntity<PostUserRes>
     */
    @PostMapping(value = "/join")
    public ResponseEntity<PostUserRes> joinUser(@RequestBody PostUserReq postUserReq) {
        try {
            log.debug("user = {}", postUserReq.toString());
            return new ResponseEntity<>(userService.createUser(postUserReq), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 회원삭제
     * @param userIdx
     * @return
     */
    @ResponseBody
    @PostMapping("/deleteUser")
    public ResponseEntity<String> userDelete(@RequestParam(value = "userIdx") Integer userIdx) {
        try {
            log.debug("user idx = {}", userIdx);
            Integer deletedCnt = userService.deleteByIdx(userIdx);
            return new ResponseEntity<>(String.format("%d deleted", deletedCnt), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 회원정보 변경
     * @param userIdx
     * @param user
     * @return
     */
    @PatchMapping("/changeInfo/{userIdx}")
    public ResponseEntity<String> changeUser(@PathVariable("userIdx") int userIdx, @RequestBody User user) {
        try {
            PatchUserReq patchUserReq = new PatchUserReq(userIdx, user.getUserName(), user.getUserPw());
            log.debug("user = {}", user.toString());
            Integer updatedCnt = userService.updateByIdx(patchUserReq);

            return new ResponseEntity<>(String.format("%d updated", updatedCnt), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 로그인
     * @param userId
     * @param userPw
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<PostLoginRes> loginUser(@RequestParam(value = "userId") String userId, @RequestParam(value = "userPw") String userPw  ) {

        try {
            PostLoginReq postLoginReq = new PostLoginReq(userId, userPw);
            PostLoginRes postLoginRes = userService.userLogin(postLoginReq);

            return new ResponseEntity<>(postLoginRes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }
}
