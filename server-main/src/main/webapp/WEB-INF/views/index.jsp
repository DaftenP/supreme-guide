<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="com.ssafy.enjoytrips.model.dto.Sido,java.util.HashMap"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page isELIgnored="false"%>
<c:set var="root" value="${ pageContext.request.contextPath }" />

<!DOCTYPE html>
<html class="no-js" lang="zxx">
<head>
<meta charset="utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=edge" />
<title>Wish Trip</title>
<meta name="description" content />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<!-- ========================= CSS here ========================= -->
<link rel="stylesheet" href="${root}/assets/css/LineIcons.2.0.css" />
<link rel="stylesheet" href="${root}/assets/css/bootstrap.min.css" />
<link rel="stylesheet" href="${root}/assets/css/animate.css" />
<link rel="stylesheet" href="${root}/assets/css/tiny-slider.css" />
<link rel="stylesheet" href="${root}/assets/css/glightbox.min.css" />
<link rel="stylesheet" href="${root}/assets/css/main.css" />
</head>

<body>
	<%@ include file="./templates/header.jsp"%>

	<!-- Start Hero Area -->
	<section class="hero-area overlay" id="search-section">
		<div class="container">
			<div class="row">
				<div class="col-lg-12 col-md-12 col-12">
					<div class="hero-text text-center">
						<!-- Start Hero Text -->
						<div class="section-heading">
							<h2 class="wow fadeInUp" data-wow-delay=".3s">전국 관광지 정보</h2>
							<p class="wow fadeInUp" data-wow-delay=".5s">
								<br />꿈꾸던 여행지를 찾아보세요! <br />어디를 가고 싶으신가요?
							</p>
						</div>
						<!-- End Search Form -->
						<!-- Start Search Form -->
						<div class="search-form wow fadeInUp" data-wow-delay=".7s">
							<form id="search-form">
								<input type="hidden" name="action" value="search" />
								<div class="row">
									<div class="col-lg-2 col-md-2 col-6 p-0">
										<div class="search-input">
											<label for="location"><i
												class="lni lni-map-marker theme-color"></i></label> <select
												name="sido" id="search-area-sido">
												<option value="0" selected>시/도 선택</option>
												<c:forEach var="sido" items="${ sidoList }">
													<option value="${ sido.code }">${ sido.name }</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-lg-2 col-md-2 col-6 p-0">
										<div class="search-input">
											<label for="location"><i
												class="lni lni-map-marker theme-color"></i></label> <select
												name="gugun" id="search-area-gugun">
												<optgroup id="search-area-gugun-0">
													<option value="0" selected>구/군 선택</option>
												</optgroup>
												<c:forEach var="gugunList" items="${ gugunMap }">
													<optgroup id="search-area-gugun-${ gugunList.key }"
														style="display: none;">
														<c:forEach var="gugun" items="${ gugunList.value }">
															<option value="${ gugun.code }">${ gugun.name }</option>
														</c:forEach>
													</optgroup>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-lg-2 col-md-2 col-12 p-0">
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
							</form>
						</div>
						<!-- End Search Form -->
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- End Hero Area -->


	<section id="map-display" class="d-col" style="display: none;">
		<div id="map" class="mt-3 ms-auto me-auto"
			style="width: 90%; height: 600px;"></div>
		<div id="map-controller"
			class="d-flex justify-content-end search-btn button">
			<button class="btn me-2" data-bs-toggle="modal"
				data-bs-target="#listModal">목록 보기</button>
			<button class="btn me-5" onclick="searchReload()">
				<i class="lni lni-search-alt"></i> 검색
			</button>
		</div>
	</section>

	<!-- Start Categories Area -->
	<section class="categories">
		<div class="container"></div>
	</section>
	<!-- /End Categories Area -->

	<!-- Start Items Grid Area -->
	<section class="items-grid section">
		<div class="container">
			<div class="row">
				<div class="col-12">
					<div class="section-title">
						<h2 class="wow fadeInUp" data-wow-delay=".4s">핫플레이스</h2>
						<p class="wow fadeInUp" data-wow-delay=".6s">많은 사람들이 호감을 가진 인기
							장소도 살펴보세요!</p>
					</div>
				</div>
			</div>
			<div class="single-head">
				<div class="row">
					<div class="col-lg-4 col-md-6 col-12">
						<!-- Start Single Grid -->
						<div class="single-grid wow fadeInUp" data-wow-delay=".2s">
							<div class="image">
								<a href="item-details.html" class="thumbnail"><img
									src="assets/images/tmp/swiss.jpg" alt="#" /></a>
								<div class="author">
									<div class="author-image">
										<a href="javascript:void(0)"><img
											src="assets/images/items-grid/author-1.jpg" alt="#" /> <span>jaeko</span></a>
									</div>

								</div>
							</div>
							<div class="content">
								<div class="top-content">
									<a href="javascript:void(0)" class="tag">관광지 </a>
									<h3 class="title">
										<a href="item-details.html">스위스</a>
									</h3>
									<p class="update-time">Last Updated: 1 hours ago</p>
									<ul class="rating">
										<li><i class="lni lni-star-filled"></i></li>
										<li><i class="lni lni-star-filled"></i></li>
										<li><i class="lni lni-star-filled"></i></li>
										<li><i class="lni lni-star-filled"></i></li>
										<li><i class="lni lni-star-filled"></i></li>
										<li><a href="javascript:void(0)">(35)</a></li>
									</ul>
									<ul class="info-list">
										<li><a href="javascript:void(0)"><i
												class="lni lni-map-marker"></i> New York, US</a></li>
										<li><a href="javascript:void(0)"><i
												class="lni lni-timer"></i> Feb 18, 2023</a></li>
									</ul>
								</div>
								<div class="bottom-content">
									<p class="price">
										Start From: <span>$200.00</span>
									</p>
									<a href="javascript:void(0)" class="like"><i
										class="lni lni-heart"></i></a>
								</div>
							</div>
						</div>
						<!-- End Single Grid -->
					</div>
					<div class="col-lg-4 col-md-6 col-12">
						<!-- Start Single Grid -->
						<div class="single-grid wow fadeInUp" data-wow-delay=".4s">
							<div class="image">
								<a href="item-details.html" class="thumbnail"><img
									src="assets/images/items-grid/img2.jpg" alt="#" /></a>
								<div class="author">
									<div class="author-image">
										<a href="javascript:void(0)"><img
											src="assets/images/items-grid/author-2.jpg" alt="#" /> <span>Alex
												Jui</span></a>
									</div>

								</div>
							</div>
							<div class="content">
								<div class="top-content">
									<a href="javascript:void(0)" class="tag">Real Estate</a>
									<h3 class="title">
										<a href="item-details.html">Amazing Room for Rent</a>
									</h3>
									<p class="update-time">Last Updated: 2 hours ago</p>
									<ul class="rating">
										<li><i class="lni lni-star-filled"></i></li>
										<li><i class="lni lni-star-filled"></i></li>
										<li><i class="lni lni-star-filled"></i></li>
										<li><i class="lni lni-star-filled"></i></li>
										<li><i class="lni lni-star-filled"></i></li>
										<li><a href="javascript:void(0)">(20)</a></li>
									</ul>
									<ul class="info-list">
										<li><a href="javascript:void(0)"><i
												class="lni lni-map-marker"></i> Dallas, Washington</a></li>
										<li><a href="javascript:void(0)"><i
												class="lni lni-timer"></i> Jan 7, 2023</a></li>
									</ul>
								</div>
								<div class="bottom-content">
									<p class="price">
										Start From: <span>$450.00</span>
									</p>
									<a href="javascript:void(0)" class="like"><i
										class="lni lni-heart"></i></a>
								</div>
							</div>
						</div>
						<!-- End Single Grid -->
					</div>
					<div class="col-lg-4 col-md-6 col-12">
						<!-- Start Single Grid -->
						<div class="single-grid wow fadeInUp" data-wow-delay=".6s">
							<div class="image">
								<a href="item-details.html" class="thumbnail"><img
									src="assets/images/items-grid/img3.jpg" alt="#" /></a>
								<div class="author">
									<div class="author-image">
										<a href="javascript:void(0)"><img
											src="assets/images/items-grid/author-3.jpg" alt="#" /> <span>Devid
												Milan</span></a>
									</div>

								</div>
							</div>
							<div class="content">
								<div class="top-content">
									<a href="javascript:void(0)" class="tag">Mobile Phones</a>
									<h3 class="title">
										<a href="item-details.html">Canon SX Powershot D-SLR</a>
									</h3>
									<p class="update-time">Last Updated: 3 hours ago</p>
									<ul class="rating">
										<li><i class="lni lni-star-filled"></i></li>
										<li><i class="lni lni-star-filled"></i></li>
										<li><i class="lni lni-star-filled"></i></li>
										<li><i class="lni lni-star-filled"></i></li>
										<li><i class="lni lni-star-filled"></i></li>
										<li><a href="javascript:void(0)">(55)</a></li>
									</ul>
									<ul class="info-list">
										<li><a href="javascript:void(0)"><i
												class="lni lni-map-marker"></i> New York, US</a></li>
										<li><a href="javascript:void(0)"><i
												class="lni lni-timer"></i> Mar 18, 2023</a></li>
									</ul>
								</div>
								<div class="bottom-content">
									<p class="price">
										Start From: <span>$700.00</span>
									</p>
									<a href="javascript:void(0)" class="like"><i
										class="lni lni-heart"></i></a>
								</div>
							</div>
						</div>
						<!-- End Single Grid -->
					</div>
					<div class="col-lg-4 col-md-6 col-12">
						<!-- Start Single Grid -->
						<div class="single-grid wow fadeInUp" data-wow-delay=".2s">
							<div class="image">
								<a href="item-details.html" class="thumbnail"><img
									src="assets/images/items-grid/img4.jpg" alt="#" /></a>
								<div class="author">
									<div class="author-image">
										<a href="javascript:void(0)"><img
											src="assets/images/items-grid/author-4.jpg" alt="#" /> <span>Jesia
												Jully</span></a>
									</div>

								</div>
							</div>
							<div class="content">
								<div class="top-content">
									<a href="javascript:void(0)" class="tag">Vehicles</a>
									<h3 class="title">
										<a href="item-details.html">BMW 5 Series GT Car</a>
									</h3>
									<p class="update-time">Last Updated: 4 hours ago</p>
									<ul class="rating">
										<li><i class="lni lni-star-filled"></i></li>
										<li><i class="lni lni-star-filled"></i></li>
										<li><i class="lni lni-star-filled"></i></li>
										<li><i class="lni lni-star-filled"></i></li>
										<li><i class="lni lni-star-filled"></i></li>
										<li><a href="javascript:void(0)">(35)</a></li>
									</ul>
									<ul class="info-list">
										<li><a href="javascript:void(0)"><i
												class="lni lni-map-marker"></i> New York, US</a></li>
										<li><a href="javascript:void(0)"><i
												class="lni lni-timer"></i> Apr 12, 2023</a></li>
									</ul>
								</div>
								<div class="bottom-content">
									<p class="price">
										Start From: <span>$1000.00</span>
									</p>
									<a href="javascript:void(0)" class="like"><i
										class="lni lni-heart"></i></a>
								</div>
							</div>
						</div>
						<!-- End Single Grid -->
					</div>
					<div class="col-lg-4 col-md-6 col-12">
						<!-- Start Single Grid -->
						<div class="single-grid wow fadeInUp" data-wow-delay=".4s">
							<div class="image">
								<a href="item-details.html" class="thumbnail"><img
									src="assets/images/items-grid/img5.jpg" alt="#" /></a>
								<div class="author">
									<div class="author-image">
										<a href="javascript:void(0)"><img
											src="assets/images/items-grid/author-5.jpg" alt="#" /> <span>Thomas
												Deco</span></a>
									</div>

								</div>
							</div>
							<div class="content">
								<div class="top-content">
									<a href="javascript:void(0)" class="tag">Apple</a>
									<h3 class="title">
										<a href="item-details.html">Apple Macbook Pro 13 Inch</a>
									</h3>
									<p class="update-time">Last Updated: 5 hours ago</p>
									<ul class="rating">
										<li><i class="lni lni-star-filled"></i></li>
										<li><i class="lni lni-star-filled"></i></li>
										<li><i class="lni lni-star-filled"></i></li>
										<li><i class="lni lni-star-filled"></i></li>
										<li><i class="lni lni-star-filled"></i></li>
										<li><a href="javascript:void(0)">(35)</a></li>
									</ul>
									<ul class="info-list">
										<li><a href="javascript:void(0)"><i
												class="lni lni-map-marker"></i> Louis, Missouri, US</a></li>
										<li><a href="javascript:void(0)"><i
												class="lni lni-timer"></i> May 25, 2023</a></li>
									</ul>
								</div>
								<div class="bottom-content">
									<p class="price">
										Start From: <span>$550.00</span>
									</p>
									<a href="javascript:void(0)" class="like"><i
										class="lni lni-heart"></i></a>
								</div>
							</div>
						</div>
						<!-- End Single Grid -->
					</div>
					<div class="col-lg-4 col-md-6 col-12">
						<!-- Start Single Grid -->
						<div class="single-grid wow fadeInUp" data-wow-delay=".6s">
							<div class="image">
								<a href="item-details.html" class="thumbnail"><img
									src="assets/images/items-grid/img6.jpg" alt="#" /></a>
								<div class="author">
									<div class="author-image">
										<a href="javascript:void(0)"><img
											src="assets/images/items-grid/author-6.jpg" alt="#" /> <span>Jonson
												zack</span></a>
									</div>
								</div>
							</div>
							<div class="content">
								<div class="top-content">
									<a href="javascript:void(0)" class="tag">Restaurant</a>
									<h3 class="title">
										<a href="item-details.html">Cream Restaurant</a>
									</h3>
									<p class="update-time">Last Updated: 7 hours ago</p>
									<ul class="rating">
										<li><i class="lni lni-star-filled"></i></li>
										<li><i class="lni lni-star-filled"></i></li>
										<li><i class="lni lni-star-filled"></i></li>
										<li><i class="lni lni-star-filled"></i></li>
										<li><i class="lni lni-star-filled"></i></li>
										<li><a href="javascript:void(0)">(20)</a></li>
									</ul>
									<ul class="info-list">
										<li><a href="javascript:void(0)"><i
												class="lni lni-map-marker"></i> New York, US</a></li>
										<li><a href="javascript:void(0)"><i
												class="lni lni-timer"></i> Feb 18, 2023</a></li>
									</ul>
								</div>
								<div class="bottom-content">
									<p class="price">
										Start From: <span>$500.00</span>
									</p>
									<a href="javascript:void(0)" class="like"><i
										class="lni lni-heart"></i></a>
								</div>
							</div>
						</div>
						<!-- End Single Grid -->
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- /End Items Grid Area -->

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
	<script src="assets/js/bootstrap.min.js"></script>
	<script src="assets/js/wow.min.js"></script>
	<script src="assets/js/tiny-slider.js"></script>
	<script src="assets/js/glightbox.min.js"></script>
	<script src="assets/js/main.js"></script>
	<script src="assets/js/login.js"></script>
	<script src="assets/js/logout.js"></script>
	<script src="assets/js/join.js"></script>
	<script src="assets/js/user-update.js"></script>
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=12c18c4deb7d6b74a50155714e65642a&libraries=services,clusterer,drawing"></script>
	<script src="assets/js/map.js"></script>
	<script type="text/javascript">
		// 시/도 선택하는 select 엘리먼트
		var sidoSelect = document.getElementById("search-area-sido");
		var selected = '0';

		// 시/도 선택이 변경될 때마다 호출되는 함수
		sidoSelect
				.addEventListener(
						"change",
						function() {
							var selectedSidoCode = sidoSelect.value;
							var gugunSelect = document
									.getElementById("search-area-gugun-"
											+ selectedSidoCode);
							if (gugunSelect != null) {
								gugunSelect.style.display = "block";
								document.getElementById("search-area-gugun-"
										+ selected).style.display = "none";
								selected = selectedSidoCode;
								document.getElementById("search-area-gugun").value = 0;
							}
						});
	</script>

</body>
</html>
