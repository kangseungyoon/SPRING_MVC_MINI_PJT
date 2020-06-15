package kr.co.kang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kang.beans.BoardInfoBean;
import kr.co.kang.dao.TopMenuDao;

@Service
public class TopMenuService {
	@Autowired
	private TopMenuDao topMenuDao;
	
	public List<BoardInfoBean> getTopMenuList(){
		List<BoardInfoBean> topMenuList=topMenuDao.getTopMenuList();
		return topMenuList;
	}
}
