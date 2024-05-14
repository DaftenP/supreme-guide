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
	href="../assets/images/favicon.svg" />

<!-- Place favicon.ico in the root directory -->

<!-- Web Font -->
<link
	href="https://fonts.googleapis.com/css2?family=Jost:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
	rel="stylesheet" />
<link href="https://fonts.googleapis.com/css2?family=Lato&display=swap"
	rel="stylesheet" />

<!-- ========================= CSS here ========================= -->
<link rel="stylesheet" href="./assets/css/bootstrap.min.css" />
<link rel="stylesheet" href="./assets/css/LineIcons.2.0.css" />
<link rel="stylesheet" href="./assets/css/animate.css" />
<link rel="stylesheet" href="./assets/css/tiny-slider.css" />
<link rel="stylesheet" href="./assets/css/glightbox.min.css" />
<link rel="stylesheet" href="./assets/css/main.css" />
</head>

<body>
	<%@ include file="./header.jsp"%>

	<!-- Start Hero Area -->
	<section class id="search-section">
		<div class="container">
			<div class="row">
				<div class="col-lg-10 offset-lg-1 col-md-12 col-12">
					<div class="hero-text text-center">
						<!-- Start Search Form -->
						<div class="search-form wow fadeInUp mt-0" data-wow-delay=".7s">
							<div class="row">
								<div class="col-lg-3 col-md-3 col-12 p-0">
									<div class="search-input">
										<label for="location"><i
											class="lni lni-map-marker theme-color"></i></label> <select
											name="location" id="search-area">
											<option value="0" selected>지역 선택</option>
										</select>
									</div>
								</div>
								<div class="col-lg-3 col-md-3 col-12 p-0">
									<div class="search-input">
										<label for="category"><i
											class="lni lni-grid-alt theme-color"></i></label> <select
											name="category" id="search-content-id">
											<option value="0" selected>관광지 유형</option>
											<option value="12">관광지</option>
											<option value="14">문화시설</option>
											<option value="15">축제공연행사</option>
											<option value="25">여행코스</option>
											<option value="28">레포츠</option>
											<option value="32">숙박</option>
											<option value="38">쇼핑</option>
											<option value="39">음식점</option>
										</select>
									</div>
								</div>
								<div class="col-lg-4 col-md-4 col-12 p-0">
									<div class="search-input">
										<label for="keyword"><i
											class="lni lni-search-alt theme-color"></i></label> <input
											type="search" name="keyword" id="search-keyword"
											placeholder="검색어 입력" aria-label="검색어 입력" />
									</div>
								</div>
								<div class="col-lg-2 col-md-2 col-12 p-0">
									<div class="search-btn button">
										<button class="btn" id="btn-search">
											<i class="lni lni-search-alt"></i> 검색
										</button>
									</div>
								</div>
							</div>
						</div>
						<!-- End Search Form -->
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- End Hero Area -->

	<section id="map-display" class="d-flex justify-content-start mb-20">
		<div class="mt-3 col-3">
			<div id="trip-plan" class="list-group pe-5 ps-5">
				<a href="#"
					class="list-group-item list-group-item-action text-center active"
					aria-current="true"> 여행지 목록 </a>
				<c:forEach var="item" items="${list }">
					<span class="plan-list">
					<a href="#" class="list-group-item list-group-item-action text-center">${item.name_trip_plan}</a>
					<input type="hidden" name = "x" value="${item.lng_trip_plan }">
					<input type="hidden" name = "y" value="${item.lat_trip_plan }">
					</span>
				</c:forEach>
			</div>
		</div>
		<div id="map" class="mt-3 col-9" style="width: 65%; height: 600px"></div>
		<form method="post" action="${root }/plan" id="plan_submit">
			<input type="hidden" name="action" value="regist">
			<input type="hidden" name="user_id" value="${loginUser.id }">
			<button type="submit" class="btn btn-success mr-2">등록</button>
		</form>
	</section>

	<section></section>

	<!-- Start Footer Area -->
	<footer class="footer">
		<!-- Start Footer Bottom -->
		<div class="footer-bottom">
			<div class="container">
				<div class="inner">
					<div class="row">
						<div class="col-12">
							<div class="content">
								<ul class="footer-bottom-links">
									<li><a href="javascript:void(0)">Terms of use</a></li>
									<li><a href="javascript:void(0)"> Privacy Policy</a></li>
									<li><a href="javascript:void(0)">Advanced Search</a></li>
									<li><a href="javascript:void(0)">Site Map</a></li>
									<li><a href="javascript:void(0)">Information</a></li>
								</ul>
								<p class="copyright-text">
									Designed and Developed by <a href="https://graygrids.com/"
										rel="nofollow" target="_blank">GrayGrids</a>
								</p>
								<ul class="footer-social">
									<li><a href="javascript:void(0)"><i
											class="lni lni-facebook-filled"></i></a></li>
									<li><a href="javascript:void(0)"><i
											class="lni lni-twitter-original"></i></a></li>
									<li><a href="javascript:void(0)"><i
											class="lni lni-youtube"></i></a></li>
									<li><a href="javascript:void(0)"><i
											class="lni lni-linkedin-original"></i></a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- End Footer Middle -->
	</footer>
	<!--/ End Footer Area -->

	<!-- ========================= scroll-top ========================= -->
	<a href="#" class="scroll-top btn-hover"> <i
		class="lni lni-chevron-up"></i>
	</a>

	<!-- ========================= JS here ========================= -->
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/5.1.3/js/bootstrap.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/2.11.6/umd/popper.min.js"></script>
	<script src="./assets/js/bootstrap.min.js"></script>
	<script src="./assets/js/wow.min.js"></script>
	<script src="./assets/js/tiny-slider.js"></script>
	<script src="./assets/js/glightbox.min.js"></script>
	<script src="./assets/js/main.js"></script>
	<script src="./assets/js/login.js"></script>
	<script src="./assets/js/logout.js"></script>
	<script src="./assets/js/join.js"></script>
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=12c18c4deb7d6b74a50155714e65642a&libraries=services,clusterer,drawing"></script>
	<script src="./assets/js/trip_plan.js"></script>
	<script type="module">
      import { serviceKey } from "././assets/js/key.js";
      window.serviceKey = serviceKey;
      // index page 로딩 후 전국의 시도 설정.
      let areaUrl =
        "https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=" +
        serviceKey +
        "&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json";

      // fetch(areaUrl, { method: "GET" }).then(function (response) { return response.json() }).then(function (data) { makeOption(data); });
      fetch(areaUrl, { method: "GET" })
        .then((response) => response.json())
        .then((data) => makeOption(data));

      function makeOption(data) {
        let areas = data.response.body.items.item;
        // console.log(areas);
        let sel = document.getElementById("search-area");
        areas.forEach((area) => {
          let opt = document.createElement("option");
          opt.setAttribute("value", area.code);
          opt.appendChild(document.createTextNode(area.name));

          sel.appendChild(opt);
        });
      }
    </script>
</body>
</html>
