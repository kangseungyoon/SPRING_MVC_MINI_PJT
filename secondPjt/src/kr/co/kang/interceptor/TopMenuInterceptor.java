package kr.co.kang.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.kang.beans.BoardInfoBean;
import kr.co.kang.beans.UserBean;
import kr.co.kang.service.TopMenuService;

public class TopMenuInterceptor implements HandlerInterceptor{
	
	private TopMenuService topMenuService;
	private UserBean loginUserBean;
	
	public TopMenuInterceptor(TopMenuService topMenuService,UserBean loginUserBean) {
		this.topMenuService=topMenuService;
		this.loginUserBean=loginUserBean;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		List<BoardInfoBean> topMenuList=topMenuService.getTopMenuList();
		request.setAttribute("topMenuList", topMenuList);
		request.setAttribute("loginUserBean",loginUserBean);
		
		return true;
	}
}
