package kr.co.bepoz.mapper;



import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import kr.co.bepoz.beans.ContentBean;

public interface BoardMapper {
	
	@SelectKey(statement="select content_seq.nextval from dual", keyProperty="content_idx", before=true, resultType=int.class)
	
	@Insert("insert into content_table(content_idx, content_subject, content_text, "
			+ "content_file, content_writer_idx, content_board_idx, content_date) "
			+ "values(#{content_idx},#{content_subject},#{content_text},#{content_file, jdbcType=VARCHAR},"
			+ "#{content_writer_idx}, #{content_board_idx},sysdate)")
	void addContentInfo(ContentBean writeContentBean);
	
	@Select("select board_info_name "
			+ "from board_info_table "
			+ "where board_info_idx=#{board_info_idx}")
	String getBoardInfoName(int board_info_idx);
	
	@Select("select ctnt.content_idx, ctnt.content_subject, utable.user_name as content_writer_name, "
			+ "to_char(ctnt.content_date, 'YYYY-MM-DD') as content_date" 
		   +" from content_table ctnt, user_table utable"
		   +" where ctnt.content_writer_idx = utable.user_idx"
		   +" and ctnt.content_board_idx=#{board_info_idx}" 
		   +" order by ctnt.content_idx desc")
	List<ContentBean> getContentList(int board_info_idx, RowBounds rowBounds);
	
	@Select("select utable.user_name as content_writer_name, to_char(ctnt.content_date, 'YYYY-MM-DD') as content_date,"
			+"ctnt.content_subject, ctnt.content_text, ctnt.content_file, ctnt.content_writer_idx "
			+"from content_table ctnt, user_table utable "
			+"where ctnt.content_writer_idx=utable.user_idx "
			+"and ctnt.content_idx=#{content_idx}")
	ContentBean getContentInfo(int content_idx);
	
	@Update("update content_table "
			+ "set content_subject =#{content_subject}, content_text=#{content_text}, "
			+ "content_file=#{content_file,jdbcType=VARCHAR} where content_idx=#{content_idx}")
	void modifyContentInfo(ContentBean modifyContentBean);
	
	@Delete("delete from content_table where content_idx=#{content_idx}")
	void deleteContentInfo(int content_idx);
	
	@Select("select count(*) from content_table where content_board_idx=#{content_board_idx}")
	int getContentCnt(int content_board_idx);
	
}
