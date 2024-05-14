

// 검색 버튼을 누르면..
// 지역, 유형, 검색어 얻기.
// 위 데이터를 가지고 공공데이터에 요청.
// 받은 데이터를 이용하여 화면 구성.
document.getElementById("btn-search").addEventListener("click", () => {
	document.getElementById("map-display").style.display = "block";

	map.relayout();
	let baseUrl = `https://apis.data.go.kr/B551011/KorService1/`;
	// let searchUrl = `https://apis.data.go.kr/B551011/KorService1/searchKeyword1?serviceKey=${serviceKey}&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A`;
	// let searchUrl = `https://apis.data.go.kr/B551011/KorService1/areaBasedList1?serviceKey=${serviceKey}&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A`;

	let queryString = `serviceKey=${serviceKey}&numOfRows=500&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A`;
	let areaCode = document.getElementById("search-area").value;
	let contentTypeId = document.getElementById("search-content-id").value;
	let keyword = document.getElementById("search-keyword").value;

	if (parseInt(areaCode)) queryString += `&areaCode=${areaCode}`;
	if (parseInt(contentTypeId)) queryString += `&contentTypeId=${contentTypeId}`;
	// if (!keyword) {
	//   alert("검색어 입력 필수!!!");
	//   return;
	// } else searchUrl += `&keyword=${keyword}`;
	let service = ``;
	if (keyword) {
		service = `searchKeyword1`;
		queryString += `&keyword=${keyword}`;
	} else {
		service = `areaBasedList1`;
	}
	let searchUrl = baseUrl + service + "?" + queryString;

	fetch(searchUrl)
		.then((response) => response.json())
		.then((data) => makeList(data));
});

var positions; // marker 배열.
function makeList(data) {
	// console.log(data);
	// document.querySelector("table").setAttribute("style", "display: ;");
	let trips = data.response.body.items.item;
	let tripList = ``;
	positions = [];
	console.log(`${trips.length} items received`);
	trips.forEach((area) => {
		tripList += `
            <tr data-bs-dismiss="modal" onclick="moveCenter(${area.mapy}, ${area.mapx});">
              <td><img src="${area.firstimage}" width="100px"></td>
              <td>${area.title}</td>
              <td>${area.addr1} ${area.addr2}</td>
              <td>${area.mapy}</td>
              <td>${area.mapx}</td>
            </tr>
          `;

		let markerInfo = {
			title: area.title,
			latlng: new kakao.maps.LatLng(area.mapy, area.mapx),
			x: area.mapx,
			y: area.mapy,
			type: area.contenttypeid,
			content: area.title,
			imgSrc: area.firstimage,
			address: area.addr1 + area.addr2,
			zipcode: area.zipcode,
		};
		positions.push(markerInfo);
	});
	document.getElementById("trip-list").innerHTML = tripList;
	displayMarker();
}


// 카카오지도
var mapContainer = document.getElementById("map"), // 지도를 표시할 div
	mapOption = {
		center: new kakao.maps.LatLng(37.500613, 127.036431), // 지도의 중심좌표
		level: 5, // 지도의 확대 레벨
	};

// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption);
// 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

function displayMarker() {
	console.log(position);
	// 마커 이미지의 이미지 주소입니다
	var imageSrc =
		"https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; // 기타이미지
	var markers = [];
	for (var i = 0; i < positions.length; i++) {
		// 마커 이미지의 이미지 크기 입니다
		var imageSize = new kakao.maps.Size(24, 35);
		// 마커 이미지를 생성합니다
		if (positions[i].type == "12") {
			// 관광지
			var markerImage = new kakao.maps.MarkerImage(
				"./assets/images/map/travel.png",
				imageSize
			);
		} else if (positions[i].type == "14") {
			// 문화시설
			var markerImage = new kakao.maps.MarkerImage(
				"./assets/images/map/culture.png",
				imageSize
			);
		} else if (positions[i].type == "15") {
			// 축제 공연 행사
			var markerImage = new kakao.maps.MarkerImage(
				"./assets/images/map/festival.png",
				imageSize
			);
		} else if (positions[i].type == "25") {
			// 여행 코스
			var markerImage = new kakao.maps.MarkerImage(
				"./assets/images/map/course.png",
				imageSize
			);
		} else if (positions[i].type == "28") {
			// 레포츠
			var markerImage = new kakao.maps.MarkerImage(
				"./assets/images/map/leisure.png",
				imageSize
			);
		} else if (positions[i].type == "32") {
			// 숙박
			var markerImage = new kakao.maps.MarkerImage(
				"./assets/images/map/hotel.png",
				imageSize
			);
		} else if (positions[i].type == "38") {
			// 쇼핑
			var markerImage = new kakao.maps.MarkerImage(
				"./assets/images/map/shop.png",
				imageSize
			);
		} else if (positions[i].type == "39") {
			// 음식점
			var markerImage = new kakao.maps.MarkerImage(
				"./assets/images/map/food.png",
				imageSize
			);
		} else {
			// 기타
			var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
		}

		// 마커를 생성합니다
		var marker = new kakao.maps.Marker({
			position: positions[i].latlng, // 마커를 표시할 위치
			title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
			image: markerImage, // 마커 이미지
		});

		// 마커에 표시할 인포윈도우를 생성합니다
		var infowindow = new kakao.maps.InfoWindow({
			content: `
                <div id="infoWindow" class="panel panel-default">
                    <div
                      class="popover-arrow"
                      style="position: absolute; transform: translate(0px, 47px); top: 0px"></div>
                    <h3 class="popover-header">${positions[i].title}</h3>
                    <div class="d-flex">
                    <div class="image p-3 col-4" style="margin-bottom: 10px; background-color: ${positions[i].imgSrc ? "transparent" : "#87CEEB"
				};">
                      ${positions[i].imgSrc
					? `<img src="${positions[i].imgSrc}" style="max-width: 100%; height: 100px;">`
					: `<div style="padding: 10px; color: white; max-width: 100%; height: 100px; display: flex; justify-content: center; align-items: center;">
                                이미지 없음
                              </div>`
				}
                      </div>
                      <div class="address pt-3 col-8" style="font-size: 14px;">
                      ${positions[i].address}<br><br>
                      우편번호: ${positions[i].zipcode}<br/>
                      <button
                      type="button"
                      id="btn-add-trip"
                      class="btn btn-primary btn-sm"
                      onclick="addTrip('${positions[i].title}', '${positions[i].x}', '${positions[i].y}');">
                      여행지 추가
                      </button>
                      </div>
                    </div>
                  </div>
			`, // 인포윈도우에 표시할 내용
		});

		// 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
		// 이벤트 리스너로는 클로저를 만들어 등록합니다
		// for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
		kakao.maps.event.addListener(
			marker,
			"click",
			makeOverListener(map, marker, infowindow)
		);
		kakao.maps.event.addListener(map, "click", makeOutListener(infowindow));

		// 마커에 클릭이벤트를 등록합니다
		var position = positions[i];
		kakao.maps.event.addListener(marker, "click", clickListener(position));


		// 클러스터 객체 생성
		var clusterer = new kakao.maps.MarkerClusterer({
			map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
			averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
			minLevel: 6, // 클러스터 할 최소 지도 레벨
			disableClickZoom: true, // 클러스터 마커를 클릭했을 때 지도가 확대되지 않도록 설정한다
		});

		// 마커 클러스터러에 클릭이벤트를 등록합니다
		// 마커 클러스터러를 생성할 때 disableClickZoom을 true로 설정하지 않은 경우
		// 이벤트 헨들러로 cluster 객체가 넘어오지 않을 수도 있습니다
		kakao.maps.event.addListener(clusterer, "clusterclick", function(cluster) {
			// 현재 지도 레벨에서 1레벨 확대한 레벨
			var level = map.getLevel() - 1;
			// 지도를 클릭된 클러스터의 마커의 위치를 기준으로 확대합니다
			map.setLevel(level, { anchor: cluster.getCenter() });
		});
		markers.push(marker);
	}

	// 클러스터에 마커 등록
	clusterer.addMarkers(markers);

	// 첫번째 검색 정보를 이용하여 지도 중심을 이동 시킵니다
	map.setCenter(positions[0].latlng);
}


// 인포윈도우를 표시하는 클로저를 만드는 함수입니다
function makeOverListener(map, marker, infowindow) {
	return function() {
		infowindow.open(map, marker);
	};
}
// 인포윈도우를 닫는 클로저를 만드는 함수입니다
function makeOutListener(infowindow) {
	return function() {
		infowindow.close();
	};
}
// 마커를 클릭시 해당 마커 중심으로 카메라 시점 이동
function clickListener(position) {
	return function() {
		map.setCenter(position.latlng);
	};
}

function moveCenter(lat, lng) {
	map.setCenter(new kakao.maps.LatLng(lat, lng));
}

searchReload = () => {
	document.getElementById("search-section").style.display = "block";
	document.getElementById("map-display").style.display = "none";
};

let linePath = [];
addTrip = (title, x, y) => {
	tripPlan = document.getElementById("trip-plan");
	let content = `<a href="#" class="list-group-item list-group-item-action text-center">${title}</a>`
	tripPlan.innerHTML += content;
	
	planSubmit = document.getElementById("plan_submit");
	let str = `<input type="hidden" name="name_trip_plan[]" value="${title}">`;
	str += `<input type="hidden" name="lat_trip_plan[]" value="${y}">`;
	str += `<input type="hidden" name="lng_trip_plan[]" value="${x}">`;
	planSubmit.innerHTML += str;

	linePath.push(new kakao.maps.LatLng(y, x));
	addLine();
};

addLine = () => {
	// 지도에 표시할 선을 생성합니다
	var polyline = new kakao.maps.Polyline({
		path: linePath, // 선을 구성하는 좌표배열 입니다
		strokeWeight: 5, // 선의 두께 입니다
		strokeColor: "#3d5afe", // 선의 색깔입니다
		strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
		strokeStyle: "solid", // 선의 스타일입니다
	});

	// 지도에 선을 표시합니다
	polyline.setMap(map);
};

window.onload = () => {
	let list = document.querySelectorAll(".plan-list");
	list.forEach(function(item) {
		let x = item.querySelector('input[name="x"]').value; // x 값 가져오기
		let y = item.querySelector('input[name="y"]').value; // y 값 가져오기
		linePath.push(new kakao.maps.LatLng(y, x));
	});
	addLine();
}
