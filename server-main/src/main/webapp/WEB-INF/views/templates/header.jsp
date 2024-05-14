<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.ssafy.enjoytrips.model.dto.User"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="root" value="${ pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js" lang="zxx">
<head>
<meta charset="utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=edge" />
<title>Wish Trip</title>
<meta name="description" content />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="shortcut icon" type="image/x-icon"
	href="${root}/assets/images/favicon.ico" />

<!-- Place favicon.ico in the root directory -->

<!-- Web Font -->
<link
	href="https://fonts.googleapis.com/css2?family=Jost:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
	rel="stylesheet" />
<link href="https://fonts.googleapis.com/css2?family=Lato&display=swap"
	rel="stylesheet" />
</head>
<body>
	<!-- Preloader -->
	<!-- 	<div class="preloader"> -->
	<!-- 		<div class="preloader-inner"> -->
	<!-- 			<div class="preloader-icon"> -->
	<!-- 				<span></span> <span></span> -->
	<!-- 			</div> -->
	<!-- 		</div> -->
	<!-- 	</div> -->
	<!-- /End Preloader -->
	<c:if test="${ loginMsg != null }">
		<script type="text/javascript">
			alert("${ loginMsg }");
			${loginMsg = null}
			history.back();
		</script>
	</c:if>

	<c:choose>
		<c:when test="${ deleteMsg != null }">
			<script type="text/javascript">
				alert("${ deleteMsg }");
				${deleteMsg = null}
				location.href = "${root}"
			</script>
		</c:when>

		<c:when test="${ modifyMsg != null }">
			<script type="text/javascript">
				alert("${ modifyMsg }");
				${modifyMsg = null}
				location.href = "${root}"
			</script>
		</c:when>

		<c:when test="${ registMsg != null }">
			<script type="text/javascript">
				alert("${ registMsg }");
				${registMsg = null}
				location.href = "${root}"
			</script>
		</c:when>

		<c:when test="${ modifyMsg != null }">
			<script type="text/javascript">
				alert("${ modifyMsg }");
				${modifyMsg = null}
				location.href = "${root}"
			</script>
		</c:when>
	</c:choose>

	<!-- Start Header Area -->
	<header class="header navbar-area shadow">
		<div class="container">
			<div class="row align-items-center">
				<div class="col-lg-12">
					<div class="nav-inner">
						<nav class="navbar navbar-expand-lg">
							<a class href="${root }/index.jsp"> <img src="${ root }/assets/images/logo/ssafy_logo.png" alt="Logo" height="40px" />
								<span class="logo-text" style="color: navy;">Wish Trip</span>
							</a>
							<button class="navbar-toggler mobile-menu-btn" type="button"
								data-bs-toggle="collapse"
								data-bs-target="#navbarSupportedContent"
								aria-controls="navbarSupportedContent" aria-expanded="false"
								aria-label="Toggle navigation">
								<span class="toggler-icon"></span> <span class="toggler-icon"></span>
								<span class="toggler-icon"></span>
							</button>
							<div class="collapse navbar-collapse sub-menu-bar"
								id="navbarSupportedContent">
								<ul id="nav" class="navbar-nav ms-auto">
									<li class="nav-item"><a class="active dd-menu collapsed"
										href="${root }/index.jsp" aria-label="Toggle navigation">Home</a></li>
									<li class="nav-item"><a
										href="${root }/plan?action=home&id=${ loginUser.id }"
										aria-label="Toggle navigation">나의여행 계획</a></li>
									<li class="nav-item"><a href="javascript:void(0)"
										aria-label="Toggle navigation">핫플자랑하기</a>
									</li>
									<li class="nav-item"><a href="${root}/board?action=list"
										aria-label="Toggle navigation">여행정보공유하기</a></li>
									<li class="nav-item"><a href="javascript:void(0)"
										aria-label="Toggle navigation">공지사항</a>
									</li>
								</ul>
							</div>
							<!-- navbar collapse -->
							<c:choose>
								<c:when test="${ loginUser == null }">
									<div class="login-button" id="user_logout">
										<ul>
											<li><a data-bs-toggle="modal"
												data-bs-target="#loginModal"><i class="lni lni-enter"></i>
													로그인</a></li>
											<li><a data-bs-toggle="modal"
												data-bs-target="#registerModal"><i class="lni lni-user"></i>
													회원가입</a></li>
										</ul>
									</div>
								</c:when>
								<c:otherwise>
									<div class="login-button" id="user_login">
										<ul>
											<li><a data-bs-toggle="modal"
												data-bs-target="#logoutModal"><i class="lni lni-exit"></i>
													로그아웃</a></li>
											<li><a data-bs-toggle="modal"
												data-bs-target="#userinfoModal"><i class="lni lni-user"></i>
													마이페이지</a></li>
										</ul>
									</div>
								</c:otherwise>
							</c:choose>
							<div class="button header-button" id="star"
								style="display: none;">
								<a href="javascript:void(0)" class="btn">즐겨찾기</a>
							</div>
						</nav>
						<!-- navbar -->
					</div>
				</div>
			</div>
			<!-- row -->
		</div>
		<!-- container -->
	</header>
	<!-- End Header Area -->

	<!-- Modal Area -->
	<div class="modal fade" id="loginModal" data-bs-backdrop="static"
		data-bs-keyboard="false" tabindex="-1"
		aria-labelledby="staticBackdropLabel" aria-hidden="true">
		<form method="post" action="/user/login">
			<div class="modal-dialog modal-350 mt-200">
				<div class="modal-content">
					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title ms-1">로그인</h4>
						<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
					</div>

					<!-- Modal body -->
					<div class="modal-body">
						<input type="hidden" name="action" value="login" />
						<div
							class="d-flex mb-3 align-items-center justify-content-between">
							<div class="mt-2 pe-2">
								<label for="id" class="form-label">아이디 :</label>
							</div>
							<div>
								<input type="text" class="form-control" id="id"
									placeholder="아이디" name="id" value="${ cookie.savedId.value }"
									required />
							</div>
						</div>
						<div
							class="d-flex mb-3 align-items-center justify-content-between">
							<div class="mt-2 pe-2">
								<label for="pw" class="form-label">비밀번호:</label>
							</div>
							<div>
								<input type="password" class="form-control" id="pw"
									placeholder="비밀번호" name="pw" required />
							</div>
						</div>
						<div class="d-flex mb-3 align-items-center">
							<div class="mt-2 pe-2">
								<label for="pw" class="form-label">아이디 저장</label> <input
									type="checkbox" name="remember"
									${ cookie.savedId != null ? "checked" : "" } />
							</div>
						</div>
					</div>

					<!-- Modal footer -->
					<div class="modal-footer d-flex justify-content-between">
						<div>
							<button type="button" id="btn-search-id"
								class="btn btn-secondary btn-sm ms-2" onclick>아이디 찾기</button>
							<button type="button" id="btn-search-pwd"
								class="btn btn-secondary btn-sm" onclick>비밀번호 찾기</button>
						</div>
						<div>
							<button type="submit" id="btn-login"
								class="btn btn-primary btn-sm me-2">로그인</button>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
	<!-- register modal -->
	<div class="modal fade" id="registerModal" data-bs-backdrop="static"
		data-bs-keyboard="false" tabindex="-1"
		aria-labelledby="staticBackdropLabel" aria-hidden="true">
		<form method="post" action="/user/regist" id="userRegisterForm"
			onsubmit="joinPwCheck(event);">
			<input type="hidden" name="action" value="regist" />
			<div class="modal-dialog modal-350 mt-100">
				<div class="modal-content">
					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title ms-1">회원가입</h4>
						<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
					</div>

					<!-- Modal body -->
					<div class="modal-body">
						<div
							class="d-flex mb-3 align-items-center justify-content-between">
							<div class="mt-2 pe-2">
								<label for="name" class="form-label">이름 :</label>
							</div>
							<div>
								<input type="text" class="form-control" id="regist_name"
									placeholder="이름" name="name" />
							</div>
						</div>
						<div
							class="d-flex mb-3 align-items-center justify-content-between">
							<div class="mt-2 pe-2">
								<label for="question" class="form-label">아이디 :</label>
							</div>
							<div>
								<input type="text" class="form-control" id="regist_id"
									placeholder="아이디" name="id" />
							</div>
						</div>
						<div style="text-align: right; margin-bottom: 20px;">
							<span style="color: red; display: none"> 아이디 중복입니다. </span>
							<button type="button" class="btn btn-secondary btn-sm"
								id="checkIdBtn">중복확인</button>
						</div>
						<div
							class="d-flex mb-3 align-items-center justify-content-between">
							<div class="mt-2 pe-2">
								<label for="password" class="form-label">비밀번호:</label>
							</div>
							<div>
								<input type="password" class="form-control" id="regist_pw"
									placeholder="비밀번호" name="pw" />
							</div>
						</div>

						<div
							class="d-flex mb-3 align-items-center justify-content-between">
							<div class="mt-2 pe-2">
								<label for="password-repeat" class="form-label">비밀번호 확인:</label>
							</div>
							<div>
								<input type="password" class="form-control"
									id="regist_pw-repeat" placeholder="비밀번호 확인" name="pw-repeat" />
							</div>
						</div>
					</div>

					<!-- Modal footer -->
					<div class="modal-footer">
						<button type="submit" id="btn-poll-create"
							class="btn btn-primary btn-sm">회원가입</button>
					</div>
				</div>
			</div>
		</form>
	</div>
	<!-- list modal -->
	<div class="modal fade" id="listModal" data-bs-backdrop="static"
		data-bs-keyboard="false" tabindex="-1"
		aria-labelledby="staticBackdropLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title ms-1">목록</h4>
					
					<!-- 정렬 목록 drop down 추가 -->
					<!-- 정렬 목록 추가 -->
                	<select id="sort-criteria" class="form-select" style="width: auto; margin-left: auto;">
                    	<option value="titleUp">관광지명 오름차순</option>
                    	<option value="titleDown">관광지명 내림차순</option>
                    	<option value="location">가까운 관광지 순으로 보기</option>
                	</select>
                	<button id="sort-btn" class="btn btn-primary ms-2">정렬</button>	
                	
                	<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>

				<!-- Modal body -->
				<div class="modal-body">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>대표이미지</th>
								<th>관광지명</th>
								<th>주소</th>
								<th>위도</th>
								<th>경도</th>
							</tr>
						</thead>
						<tbody id="trip-list"></tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<!-- 로그아웃 모달 -->
	<div class="modal fade" id="logoutModal" data-bs-backdrop="static"
		data-bs-keyboard="false" tabindex="-1"
		aria-labelledby="staticBackdropLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm mt-200">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title ms-1">로그아웃</h4>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>

				<!-- Modal body -->
				<div class="modal-body">
					<form action>
						<div
							class="d-flex mb-3 align-items-center justify-content-between">
							<div class="mt-2 pe-2">정말 로그아웃 하시겠습니까?</div>
						</div>
					</form>
				</div>

				<!-- Modal footer -->
				<div class="modal-footer">
					<a href="${root}/user?action=logout" id="btn-logout"
						class="btn btn-danger btn-sm">로그아웃</a>
				</div>
			</div>
		</div>
	</div>
	<!-- user-update modal -->
	<div class="modal fade" id="userinfoModal" data-bs-backdrop="static"
		data-bs-keyboard="false" tabindex="-1"
		aria-labelledby="staticBackdropLabel" aria-hidden="true">
		<div class="modal-dialog modal-350 mt-100">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title ms-1">회원정보</h4>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>

				<!-- Modal body -->
				<form method="post" action="${ root }/user" id="userModifyForm"
					onsubmit="modifyPwCheck(event);">
					<input type="hidden" name="action" value="modify">
					<div class="modal-body">
						<div
							class="d-flex mb-3 align-items-center justify-content-between">
							<div class="mt-2 pe-2">이름</div>
							<div>${ loginUser.name }</div>
						</div>
						<div
							class="d-flex mb-3 align-items-center justify-content-between">
							<input type="hidden" name="id" value="${ loginUser.id }" />
							<div class="mt-2 pe-2">아이디</div>
							<div>${ loginUser.id }</div>
						</div>
						<div
							class="d-flex mb-3 align-items-center justify-content-between">
							<div class="mt-2 pe-2">
								<label for="password" class="form-label">새 비밀번호:</label>
							</div>
							<div>
								<input type="password" class="form-control" id="modify_pw"
									placeholder="비밀번호" name="pw" />
							</div>
						</div>

						<div
							class="d-flex mb-3 align-items-center justify-content-between">
							<div class="mt-2 pe-2">
								<label for="password-repeat" class="form-label">새 비밀번호
									확인:</label>
							</div>
							<div>
								<input type="password" class="form-control"
									id="modify_pw-repeat" placeholder="비밀번호 확인" name="pw-repeat" />
							</div>
						</div>
					</div>

					<!-- Modal footer -->
					<div class="modal-footer">
						<button type="submit" id="btn-poll-update"
							class="btn btn-primary btn-sm">회원정보수정</button>
						<a href="${ root }/user?action=delete&id=${ loginUser.id }"
							id="btn-poll-delete" class="btn btn-danger btn-sm">회원정보탈퇴</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- user_update modal end -->
</body>
</html>