package kr.co.kang.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.kang.beans.BoardInfoBean;
import kr.co.kang.beans.ContentBean;
import kr.co.kang.service.MainService;
import kr.co.kang.service.TopMenuService;

@Controller
public class MainController {
	@Autowired
	private MainService mainService;
	
	@Autowired
	private TopMenuService topMenuService;
	
	@GetMapping("/main")
	public String main(Model model) {
		
		ArrayList<List<ContentBean>> list=new ArrayList<>();
		
		for(int i=1;i<=4;i++) {
			List<ContentBean> list1=mainService.getContentList(i);
			list.add(list1);
		}
		model.addAttribute("list",list);
		
		List<BoardInfoBean> board_list=topMenuService.getTopMenuList();
		model.addAttribute("board_list",board_list);
		
		return "main";
	}
}
