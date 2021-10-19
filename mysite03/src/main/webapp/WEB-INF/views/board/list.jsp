<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container2">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value=""> <input
						type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>

					<c:set var='count' value='${fn:length(list) }' />

					<c:forEach items='${list }' var='vo' varStatus='status'>
						<tr>
							<td>${count-status.index }</td>
							<td><a
								href="${pageContext.request.contextPath }/board?a=view&title=${vo.title }&rdate=${vo.reg_date }&userno=${vo.user_no }">${vo.title}</a>
							</td>
							<td>${vo.writer }</td>
							<td>${vo.hit}</td>
							<td>${vo.reg_date}</td>
							<td><a href="" class="del">삭제</a></td>
						</tr>
					</c:forEach>

				</table>
				<div class="bottom" style="display: inline;">



					<fmt:parseNumber var="i" type="number" value="${maxButton }" />


					<c:if test="${param.page ne '1' }">
						<button name="button"
							onclick="location.href='${pageContext.request.contextPath }/board?page=${param.page-1 }';">&nbsp;이전&nbsp;</button>
					</c:if>
					<c:forEach var="i" begin="1" end="${i }" varStatus='pageStatus'>

						
						<c:choose>
					        <c:when test="${param.page eq pageStatus.count}">
					            <button name="button" class="button"
							onclick="location.href='${pageContext.request.contextPath }/board?page=${pageStatus.count }';">&nbsp;${pageStatus.count }&nbsp;</button>
					        </c:when>
							<c:otherwise>
					           <button name="button"
							onclick="location.href='${pageContext.request.contextPath }/board?page=${pageStatus.count }';">&nbsp;${pageStatus.count }&nbsp;</button>
					         </c:otherwise>
						</c:choose>

						


						

					</c:forEach>
					<c:if test="${maxButton != param.page }">
						<button name="button"
							onclick="location.href='${pageContext.request.contextPath }/board?page=${param.page+1 }';">&nbsp;다음&nbsp;</button>
					</c:if>



					<a href="${pageContext.request.contextPath }/board?a=writeform"
						id="new-book">글쓰기</a><br />

				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>