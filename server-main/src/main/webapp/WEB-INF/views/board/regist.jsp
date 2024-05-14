<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html class="no-js" lang="ko">
<head>
<meta charset="utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=edge" />
<title>글쓰기</title>
<meta name="description" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<!-- 부트스트랩 CSS 링크 -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

<!-- ========================= CSS here ========================= -->
<link rel="stylesheet" href="../assets/css/bootstrap.min.css" />
<link rel="stylesheet" href="../assets/css/LineIcons.2.0.css" />
<link rel="stylesheet" href="../assets/css/animate.css" />
<link rel="stylesheet" href="../assets/css/tiny-slider.css" />
<link rel="stylesheet" href="../assets/css/glightbox.min.css" />
<link rel="stylesheet" href="../assets/css/main.css" />
</head>
<body>
	<%@ include file="../templates/header.jsp"%>
	<!-- 글 등록 폼 -->
	<form class="container mt-5" method="post" action="${root}/board">
		<input type="hidden" name="action" value="regist">
		<div class="form-group">
			<input type="text" class="form-control" name="board_writer"
				placeholder="작성자" value="${ loginUser.id }" readonly="readonly" />
		</div>
		<div class="form-group">
			<input type="text" class="form-control" name="board_title"
				placeholder="제목" />
		</div>
		<div class="form-group">
			<textarea class="form-control" rows="5" name="board_content"
				placeholder="내용"></textarea>
		</div>
		<div class="form-group">
			<button type="submit" class="btn btn-success mr-2">등록</button>
			<button type="reset" class="btn btn-secondary">초기화</button>
		</div>
	</form>

	<!-- 부트스트랩 자바스크립트 및 jQuery 링크 -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
