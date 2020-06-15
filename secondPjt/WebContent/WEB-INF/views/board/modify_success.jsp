<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var='root' value='${pageContext.request.contextPath }/'/>
<script>
	alert('수정 성공')
	location.href='${root}board/main?board_info_idx=${modifyContentBean.content_board_idx}&content_idx=${modifyContentBean.content_idx}&page=${page}'
</script>