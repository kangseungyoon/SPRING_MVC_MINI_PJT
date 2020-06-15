<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var='root' value='${pageContext.request.contextPath }/'/>
<script>
	alert('글쓴이가 아닙니다.')
	location.href='${root}main'
</script>