package umcTask.umcAPI.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umcTask.umcAPI.model.User;
import umcTask.umcAPI.service.InfoService;

import java.util.List;

@Slf4j
@RestController
public class HomeController {

    @Autowired
    private InfoService infoService;

    @GetMapping("/test")
    public String projectInfo() {
        return "Controller is working!";
    }

    @GetMapping("/userList")
    public Object userList() {
        log.debug("/userList start");
        List<User> userList = infoService.getUserList();

        return userList;
    }

    @GetMapping("/userListByCode")
    public Object userByStatusCode(@RequestParam("status") String status) {
        List<User> userList = infoService.findUserByStatus(status);
        return userList;
    }

    @PostMapping(value = "/join")
    public ResponseEntity<User> joinUser(@RequestBody User user) {
        try {
            log.debug("user = {}", user.toString());
            return new ResponseEntity<>(infoService.insert(user), HttpStatus.OK);
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
            Integer deletedCnt = infoService.deleteByIdx(userIdx);
            return new ResponseEntity<>(String.format("%d deleted", deletedCnt), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/changeInfo")
    public ResponseEntity<String> changeUser(@RequestBody User user) {
        try {
            log.debug("user = {}", user.toString());
            Integer updatedCnt = infoService.updateByIdx(user);

            return new ResponseEntity<>(String.format("%d updated", updatedCnt), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
