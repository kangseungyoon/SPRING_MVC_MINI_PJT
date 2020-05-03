package kr.co.bepoz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.bepoz.beans.BoardInfoBean;
import kr.co.bepoz.dao.TopMenuDao;

@Service
public class TopMenuService {
	@Autowired
	private TopMenuDao topMenuDao;
	
	public List<BoardInfoBean> getTopMenuList(){
		List<BoardInfoBean> topMenuList=topMenuDao.getTopMenuList();
		return topMenuList;
	}
}
