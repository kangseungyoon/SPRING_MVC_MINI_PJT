package kr.co.kang.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.kang.beans.BoardInfoBean;
import kr.co.kang.mapper.TopMenuMapper;

@Repository
public class TopMenuDao {

	@Autowired
	private TopMenuMapper topMenuMapper;
	
	public List<BoardInfoBean> getTopMenuList(){
		List<BoardInfoBean> topMenuList=topMenuMapper.getTopMenuList();
		return topMenuList;
	}
	
}
