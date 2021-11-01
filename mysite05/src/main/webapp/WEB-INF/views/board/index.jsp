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
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value=""> <input
						type="submit" value="찾기">
				</form>
				<table class="tbl-ex" >
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

							<c:choose>
								<c:when test="${vo.depth > 1}">
									<c:choose>
										<c:when test="${vo.title eq '삭제된 글 입니다.'}">
										<td width='150px'
												style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap; word-break: break-all; text-align: left;">
												<c:forEach var="i" begin="3" end="${vo.depth}"
													varStatus="status">
					          &nbsp; &nbsp;
					         </c:forEach> <img
												src="${pageContext.request.contextPath }/assets/images/reply.png" />
												${vo.title}

											</td>
										</c:when>
										<c:otherwise>
											<td width='150px'
												style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap; word-break: break-all; text-align: left;">
												<c:forEach var="i" begin="3" end="${vo.depth}"
													varStatus="status">
					          &nbsp; &nbsp;
					         </c:forEach> <img
												src="${pageContext.request.contextPath }/assets/images/reply.png" />
												<a
												href="${pageContext.request.contextPath }/board/view/${vo.title }/${vo.reg_date }/${vo.user_no }/${page}/${vo.group_no}/${vo.order_no}/${vo.depth}">${vo.title}</a>

											
										</c:otherwise>
									</c:choose>
								</c:when>


								<c:otherwise>
									<c:choose>
										<c:when test="${vo.title eq '삭제된 글 입니다.'}">
											<td width='150px'
												style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap; word-break: break-all; text-align: left;">${vo.title }</td>
										</c:when>
										<c:otherwise>
											<td width='150px'
												style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap; word-break: break-all; text-align: left;"><a
												href="${pageContext.request.contextPath }/board/view/${vo.title }/${vo.reg_date }/${vo.user_no }/${page}/${vo.group_no}/${vo.order_no}/${vo.depth}">${vo.title}</a>
											</td>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>









							<td>${vo.writer }</td>
							<td>${vo.hit}</td>
							<td>${vo.reg_date}</td>
							<td><a
								href="${pageContext.request.contextPath }/board/delete/${vo.title }/${vo.reg_date }/${vo.user_no }/${page }"
								class="del">삭제</a></td>
						</tr>
					</c:forEach>

				</table>
				<div class="bottom" style="display: inline;">
					<c:if test="${page ne '1' }">
						<button name="button"
							onclick="location.href='${pageContext.request.contextPath }/board/page/${page-1 }';">&nbsp;이전&nbsp;</button>
					</c:if>
					<c:forEach var="i" begin="${start }" end="${end }"
						varStatus='pageStatus'>

						<c:choose>
							<c:when test="${page eq pageStatus.current}">
								<button name="button" class="button"
									onclick="location.href='${pageContext.request.contextPath }/board/page/${pageStatus.current }';">&nbsp;${pageStatus.current }&nbsp;</button>
							</c:when>
							<c:otherwise>
								<button name="button"
									onclick="location.href='${pageContext.request.contextPath }/board/page/${pageStatus.current }';">&nbsp;${pageStatus.current }&nbsp;</button>
							</c:otherwise>
						</c:choose>






					</c:forEach>
					<c:if test="${maxButton != page }">
						<button name="button"
							onclick="location.href='${pageContext.request.contextPath }/board/page/${page+1 }';">&nbsp;다음&nbsp;</button>
					</c:if>



					<a href="${pageContext.request.contextPath }/board/writeform"
						id="new-book">글쓰기</a><br />

				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>