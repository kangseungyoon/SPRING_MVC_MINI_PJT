package kr.co.kang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import kr.co.kang.beans.UserBean;

// 프로젝트 작업시 사용할 bean을 정의하는 클래스
@Configuration
public class RootAppContext {
	
	@Bean("loginUserBean")
	@SessionScope
	public UserBean loginUserBean() {
		return new UserBean();
	}
}
