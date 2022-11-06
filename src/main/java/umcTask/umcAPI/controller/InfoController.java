package umcTask.umcAPI.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import umcTask.umcAPI.model.User;
import umcTask.umcAPI.service.InfoService;

import java.util.List;

@Slf4j
@RestController
public class InfoController {

    @Autowired
    private InfoService infoService;

    @GetMapping("/get")
    public String projectInfo() {
        return "project name is baemin.";
    }

//    @GetMapping("/info")
//    public Object UserInfo() {
//        log.debug("/info start");
//        User user = infoService.getUserInfo();
//        return user;
//    }

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
}
