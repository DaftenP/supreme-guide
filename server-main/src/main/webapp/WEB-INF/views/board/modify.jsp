<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.ssafy.enjoytrips.model.dto.Board"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>글 수정</title>
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
	<div class="container mt-5">
		<h1>글 수정</h1>
		<form action="${root }/board" method="post">
			<input type="hidden" name="action" value="modify"> <input
				type="hidden" name="board_id" value="${board.board_id}">
			<div class="form-group">
				<label for="writer">작성자</label> <input type="text"
					class="form-control" id="writer" name="board_writer"
					value="${board.board_writer}" readonly>
			</div>
			<div class="form-group">
				<label for="title">제목</label> <input type="text"
					class="form-control" id="title" name="board_title"
					value="${board.board_title}">
			</div>
			<div class="form-group">
				<label for="content">내용</label>
				<textarea class="form-control" id="content" rows="5"
					name="board_content">${board.board_content}</textarea>
			</div>
			<button type="submit" class="btn btn-primary">수정 완료</button>
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
