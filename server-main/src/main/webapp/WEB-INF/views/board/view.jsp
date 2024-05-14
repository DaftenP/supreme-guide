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
<title>게시글 상세보기</title>
<meta name="description" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<!-- 부트스트랩 CSS 링크 -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<style>
.content-section {
	margin-top: 20px;
}
</style>
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
	<div id="container" class="container mt-5">
		<div class="card">
			<div class="card-body">
				<h5 class="card-title">${board.board_title}</h5>
				<h6 class="card-subtitle mb-2 text-muted">작성자:
					${board.board_writer}</h6>
				<div class="content-section">
					<p class="card-text">${board.board_content}</p>
				</div>
				<hr>
				<p class="card-text">조회수: ${board.board_view}</p>
				<p class="card-text">작성일: ${board.board_created_time}</p>
				<div class="d-flex justify-content-end">
					<a href="${root}/board?action=mvmodify&board_id=${board.board_id}"
						class="btn btn-primary mr-2">글 수정</a> <a
						href="${root}/board?action=delete&board_id=${board.board_id}"
						class="btn btn-danger">글 삭제</a>
				</div>
			</div>
		</div>
		<!-- 뒤로가기 버튼 -->
		<a href="${root }/board?action=list" class="btn btn-secondary mt-3">목록으로</a>
	</div>

	<!-- 부트스트랩 자바스크립트 및 jQuery 링크 -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
