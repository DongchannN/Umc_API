package umcTask.umcAPI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import umcTask.umcAPI.dto.UserDto;
import umcTask.umcAPI.mapper.UserMapper;
import umcTask.umcAPI.service.UserService;

import java.security.PrivateKey;

@SpringBootTest
class UmcApiApplicationTests {

	@Autowired
	private UserService uService;

	@Test
	public void userTest()
	{
		UserDto user = new UserDto();
		user.setUserId("test2");
		user.setUserPw("test2");
		user.setUserName("테스트2");
		uService.join(user);
		System.out.println(uService.getUser("test2"));
		System.out.println("로그인 결과:"+uService.login("test2", "test2"));

	}

	@Test
	void contextLoads() {
	}

}
