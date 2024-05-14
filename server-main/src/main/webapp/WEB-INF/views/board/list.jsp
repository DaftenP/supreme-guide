<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.List,com.ssafy.enjoytrips.model.dto.Board"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html class="no-js" lang="ko">
<head>
<meta charset="utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=edge" />
<title>여행 정보 공유 게시판</title>
<meta name="description" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<!-- 부트스트랩 CSS 링크 -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<!-- ========================= CSS here ========================= -->
<link rel="stylesheet" href="./assets/css/bootstrap.min.css" />
<link rel="stylesheet" href="./assets/css/LineIcons.2.0.css" />
<link rel="stylesheet" href="./assets/css/animate.css" />
<link rel="stylesheet" href="./assets/css/tiny-slider.css" />
<link rel="stylesheet" href="./assets/css/glightbox.min.css" />
<link rel="stylesheet" href="./assets/css/main.css" />
</head>
<body>
	<%@ include file="../templates/header.jsp"%>
	<c:if test="${ msg != null }">
		<script type="text/javascript">
			alert("${ msg }");
			${ loginMsg = null }
		</script>
	</c:if>
	<div id="container" class="container mt-5">
		<table class="table table-bordered">
			<thead class="thead-light">
				<tr>
					<th scope="col">글번호</th>
					<th scope="col">제목</th>
					<th scope="col">작성자</th>
					<th scope="col">조회수</th>
					<th scope="col">작성일</th>
				</tr>
			</thead>
			<tbody>
				<!-- 여기에 게시글 데이터를 표시할 부분 -->
				<c:forEach var="board" items="${list}">
					<tr>
						<td>${board.board_id}</td>
						<td><a
							href="${root}/board?action=view&board_id=${board.board_id}">${board.board_title}</a></td>
						<td>${board.board_writer}</td>
						<td>${board.board_view}</td>
						<td>${board.board_created_time}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<!-- 글쓰기 버튼 -->
		<a href="${root}/board?action=mvregist" class="btn btn-primary">글쓰기</a>

		<!-- 검색 폼 -->
		<form action="${root}/board?action=search" method="get" class="mt-3">
			<div class="form-row align-items-center">
				<div class="col-auto">
					<select name="category" class="form-control">
						<option value="title">제목</option>
						<option value="author">작성자</option>
						<option value="content">내용</option>
					</select>
				</div>
				<div class="col-auto">
					<input type="text" name="keyword" class="form-control"
						placeholder="검색어를 입력하세요">
				</div>
				<div class="col-auto">
					<button type="submit" class="btn btn-primary">검색</button>
				</div>
			</div>
		</form>
	</div>

	<!-- 부트스트랩 자바스크립트 및 jQuery 링크 -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
