package kr.co.bepoz.service;

import java.awt.print.Pageable;
import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.bepoz.beans.ContentBean;
import kr.co.bepoz.beans.PageBean;
import kr.co.bepoz.beans.UserBean;
import kr.co.bepoz.dao.BoardDao;

@Service
@PropertySource("/WEB-INF/properties/option.properties")
public class BoardService {
	
	@Autowired
	private BoardDao boardDao;
	
	@Resource(name="loginUserBean")
	private UserBean loginUserBean;
	
	@Value("${path.upload}")
	private String path_upload;
	
	@Value("${page.listcnt}")
	private int page_listcnt;
	
	@Value("${page.paginationcnt}")
	private int page_paginationcnt;
	
	private String saveUploadFile(MultipartFile upload_File) {
		String file_name=System.currentTimeMillis()+"_"+upload_File.getOriginalFilename();
		try {
			upload_File.transferTo(new File(path_upload+"/"+file_name));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return file_name;
	}
	
	public void addContentInfo(ContentBean writeContentBean) {
//		System.out.println(writeContentBean.getContent_subject());
//		System.out.println(writeContentBean.getContent_text());
//		System.out.println(writeContentBean.getUpload_File().getSize());
		
		MultipartFile upload_File=writeContentBean.getUpload_File();
		
		if(upload_File.getSize()>0) {
			String file_name=saveUploadFile(upload_File);
			writeContentBean.setContent_file(file_name);
			
			System.out.println(file_name);
		}
		
		writeContentBean.setContent_writer_idx(loginUserBean.getUser_idx());
		boardDao.addContentInfo(writeContentBean);
	}
	
	public String getBoardInfoName(int board_info_idx) {
		return boardDao.getBoardInfoName(board_info_idx);
	}
	
	public List<ContentBean> getContentList(int board_info_idx, int page){
		int start=(page-1)*page_listcnt;
		RowBounds rowBounds=new RowBounds(start, page_listcnt);
		
		return boardDao.getContentList(board_info_idx,rowBounds);
	}
	
	public ContentBean getContentInfo(int content_idx) {
		return boardDao.getContentInfo(content_idx);
	}
	
	public void modifyContentInfo(ContentBean modifyContentBean) {
		MultipartFile upload_File=modifyContentBean.getUpload_File();
		if(upload_File.getSize()>0) {
			String file_name=saveUploadFile(upload_File);
			modifyContentBean.setContent_file(file_name);
		}
		
		boardDao.modifyContentInfo(modifyContentBean);
	}
	
	public void deleteContentInfo(int content_idx) {
		boardDao.deleteContentInfo(content_idx);
	}
	
	public PageBean getContentCnt(int content_board_idx, int currentPage) {
		int content_cnt=boardDao.getContentCnt(content_board_idx);
		
		PageBean pageBean=new PageBean(content_cnt, currentPage, page_listcnt, page_paginationcnt);
		return pageBean;
	}
	
}
