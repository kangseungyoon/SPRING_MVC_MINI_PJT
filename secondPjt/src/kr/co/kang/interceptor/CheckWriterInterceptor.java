package kr.co.kang.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.kang.beans.ContentBean;
import kr.co.kang.beans.UserBean;
import kr.co.kang.service.BoardService;

public class CheckWriterInterceptor implements HandlerInterceptor{
	private UserBean loginUserBean;
	private BoardService boardService;
	
	public CheckWriterInterceptor(UserBean loginUserBean,BoardService boardService) {
		// TODO Auto-generated constructor stub
		this.loginUserBean=loginUserBean;
		this.boardService=boardService;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		String str1=request.getParameter("content_idx");
		int content_idx=Integer.parseInt(str1);
		
		ContentBean currentContentBean=boardService.getContentInfo(content_idx);
		
		if(loginUserBean.getUser_idx()!=currentContentBean.getContent_writer_idx()) {
			String contextPath=request.getContextPath();
			response.sendRedirect(contextPath+"/board/not_writer");
			return false;
		}
		
		return true;
	}
}
