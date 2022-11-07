package umcTask.umcAPI.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umcTask.umcAPI.model.User;
import umcTask.umcAPI.repository.UserRepository;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class InfoService {
    private final UserRepository userRepository;

    public InfoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUserList() {
        return this.userRepository.findList();
    }

    public List<User> findUserByStatus(String status) {
        return this.userRepository.findByStatusCode(status);
    }

    public User insert(User user) {
        return this.userRepository.insert(user);
    }

    public Integer deleteByIdx(Integer userIdx) {
        log.debug("user idx = {}", userIdx);

        return userRepository.deleteByIdx(userIdx);
    }

    public Integer updateByIdx(User user) {
        log.debug("user Idx = {}", user.getUserIdx());

        return userRepository.updateByIdx(user);
    }

}
