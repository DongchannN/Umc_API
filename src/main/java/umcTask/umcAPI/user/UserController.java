package umcTask.umcAPI.user;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.StringFormattedMessage;
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
    public String projectInfo() {
        return "Controller is working!";
    }

    @GetMapping("/list")
    public Object userList() {
        log.debug("/userList start");
        List<GetUserRes> userList = userService.getUserList();

        return userList;
    }

    @GetMapping("/statusList/{status}")
    public Object userByStatusCode(@PathVariable("status") String status) {
        List<GetUserRes> userList = userService.findUserByStatus(status);
        return userList;
    }

    @PostMapping(value = "/join")
    public ResponseEntity<PostUserReq> joinUser(@RequestBody PostUserReq postUserReq) {
        try {
            log.debug("user = {}", postUserReq.toString());
            return new ResponseEntity<>(userService.createUser(postUserReq), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam(value = "userId") String userId, @RequestParam(value = "userPw") String userPw  ) {
        System.out.println("Mapping good");
        try {
            PostLoginReq postLoginReq = new PostLoginReq(userId, userPw);
            Integer loginCnt = userService.userLogin(postLoginReq);
            if (loginCnt == 200) {
                return new ResponseEntity<>(String.format("Login Success!"), HttpStatus.OK);
            } else return new ResponseEntity<>(String.format("Login Failed"), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }
}
