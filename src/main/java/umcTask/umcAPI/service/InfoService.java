package umcTask.umcAPI.service;

import org.springframework.stereotype.Service;
import umcTask.umcAPI.model.User;
import umcTask.umcAPI.repository.UserRepository;

import java.util.Date;
import java.util.List;

@Service
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
}
