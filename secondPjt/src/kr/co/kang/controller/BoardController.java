package kr.co.kang.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.kang.beans.ContentBean;
import kr.co.kang.beans.PageBean;
import kr.co.kang.beans.UserBean;
import kr.co.kang.service.BoardService;

@Controller
@RequestMapping(value="/board")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@Resource(name="loginUserBean")
	private UserBean loginUserBean;
	
	@GetMapping("/main")
	public String main(@RequestParam("board_info_idx")int board_info_idx,
						@RequestParam(value="page",defaultValue="1")int page, Model model) {
		model.addAttribute("board_info_idx",board_info_idx);
		String boardInfoName=boardService.getBoardInfoName(board_info_idx);
		List<ContentBean> contentList=boardService.getContentList(board_info_idx,page);
		model.addAttribute("boardInfoName",boardInfoName);
		model.addAttribute("contentList",contentList);
		
		PageBean pageBean=boardService.getContentCnt(board_info_idx, page);
		model.addAttribute("pageBean",pageBean);
		
		model.addAttribute("page",page);
		
		return "board/main";
	}
	
	@GetMapping("/read")
	public String read(@RequestParam("board_info_idx")int board_info_idx,
						@RequestParam("content_idx")int content_idx,
						@RequestParam("page")int page,Model model) {
		model.addAttribute("board_info_idx",board_info_idx);
		model.addAttribute("content_idx",content_idx);
		ContentBean readContentBean=boardService.getContentInfo(content_idx);
		model.addAttribute("readContentBean",readContentBean);
		
		model.addAttribute("loginUserBean",loginUserBean);
		model.addAttribute("page",page);
		
		return "board/read";
	}
	
	@GetMapping("/write")
	public String write(@ModelAttribute("writeContentBean")ContentBean writeContentBean,
						@RequestParam("board_info_idx")int board_info_idx) {
		writeContentBean.setContent_board_idx(board_info_idx);
		return "board/write";
	}
	
	@PostMapping("/write_pro")
	public String write_pro(@Valid @ModelAttribute("writeContentBean")ContentBean writeContentBean,
			BindingResult result) {
		if(result.hasErrors()) {
			return "board/write";
		}
		boardService.addContentInfo(writeContentBean);
		return "board/write_success";
	}
	
	@GetMapping("/modify")
	public String modify(@RequestParam("board_info_idx")int board_info_idx, 
						@RequestParam("content_idx")int content_idx,
						@RequestParam("page")int page,Model model,
						@ModelAttribute("modifyContentBean")ContentBean modifyContentBean) {
		ContentBean temp=boardService.getContentInfo(content_idx);
		modifyContentBean.setContent_writer_name(temp.getContent_writer_name());
		modifyContentBean.setContent_date(temp.getContent_date());
		modifyContentBean.setContent_subject(temp.getContent_subject());
		modifyContentBean.setContent_text(temp.getContent_text());
		modifyContentBean.setContent_file(temp.getContent_file());
		modifyContentBean.setContent_writer_idx(temp.getContent_writer_idx());
		modifyContentBean.setContent_board_idx(board_info_idx);
		modifyContentBean.setContent_idx(content_idx);
		model.addAttribute("page",page);
		return "board/modify";
	}
	
	@PostMapping("/modify_pro")
	public String modify_pro(@Valid @ModelAttribute("modifyContentBean")ContentBean modifyContentBean,
								@RequestParam("page")int page,
							BindingResult result,Model model) {
		model.addAttribute("page",page);
		if(result.hasErrors()) {
			return "board/modify";
		}
		boardService.modifyContentInfo(modifyContentBean);
		return "board/modify_success";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("board_info_idx")int board_info_idx, 
						@RequestParam("content_idx")int content_idx,Model model) {
		boardService.deleteContentInfo(content_idx);
		
		model.addAttribute("board_info_idx",board_info_idx);
		return "board/delete";
	}
	
	@GetMapping("/not_writer")
	public String not_writer() {
		return "board/not_writer";
	}
}
