const serviceKey =
	"3iTyoUCfMgTYjLAsCNCG5bhfllv59H%2FeXycqEsqtadGcswZBw4EnowBlfcHzjURzr599VVj1bYBeMbjT%2FXwG5A%3D%3D";
// 검색 버튼을 누르면..
// 지역, 유형, 검색어 얻기.
// 위 데이터를 가지고 공공데이터에 요청.
// 받은 데이터를 이용하여 화면 구성.
document.getElementById("btn-search").addEventListener("click", (event) => {
	event.preventDefault();
	document.getElementById("search-section").style.display = "none";
	document.getElementById("search-section").setAttribute("float", "none");
	document.getElementById("map-display").style.display = "block";
	
	map.relayout();
	let form = document.getElementById('search-form');
	let formData = new FormData(form);

	const obj = {};
	formData.forEach((value, key) => obj[key] = value);

	var queryString =
		Object.entries(obj).map(([key, value]) => (value && key + '=' + value)).filter(v => v).join('&');

	fetch('http://localhost:8080/enjoytrips/', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/x-www-form-urlencoded'
		},
		body: queryString
	}).then((response) => {
		console.log(response);
		return response.json();
	})
		.then((data) =>  {
			// 전역변수에 데이터 저장
			window.globalData = data;
			makeList(data)
			});

	/*	let areaCode = document.getElementById("search-area").value;
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
			.then((data) => makeList(data));*/
});

// 합병정렬로 sort된 결과를 가져온다.
document.getElementById("sort-btn").addEventListener("click", function() {
	// 정렬 기준을 가져온다.
	var criteria = document.getElementById("sort-criteria").value;
	
	// 기준에 맞춰 데이터를 정렬한다.
	if (criteria == "titleUp") {
		window.globalData = mergeSort(window.globalData, (a, b) => a.title.localeCompare(b.title));
	} else if (criteria == "titleDown") {
		window.globalData = mergeSort(window.globalData, (a, b) => b.title.localeCompare(a.title));
	} else if (criteria == "location") {
		window.globalData = getSortedNearestSpots(map)
	}
	
	makeList(window.globalData);
})

// 합병정렬(merge sort)
function mergeSort(array, compareFn) {
	if (array.length < 2) {
		return array;
	}
	
	const middle = Math.floor(array.length/2);
	const left = array.slice(0, middle);
	const right = array.slice(middle);
	
	return merge(mergeSort(left, compareFn), mergeSort(right, compareFn), compareFn);
}

function merge(left, right, compareFn) {
	let result = [];
	let indexLeft = 0;
	let indexRight = 0;
	
	while (indexLeft < left.length && indexRight < right.length) {
		if (compareFn(left[indexLeft], right[indexRight]) <= 0) {
			result.push(left[indexLeft]);
			indexLeft++;
		} else {
			result.push(right[indexRight]);
			indexRight++;
		}
	}
	return result.concat(left.slice(indexLeft)).concat(right.slice(indexRight));
}

// 퀵 정렬
function quickSort(arr, compare) {
	if (arr.length < 2) {
		return arr;
	}
	
	const pivotIndex = Math.floor(arr.length / 2);
	const pivot = arr[pivotIndex];
	
	const left = [];
	const right = [];
	
	for (let i = 0; i < arr.length; i++) {
		if (i == pivotIndex) continue;
		if (compare(arr[i], pivot) < 0) {
			left.push(arr[i]);
		} else {
			right.push(arr[i]);
		}
	}
	return quickSort(left, compare).concat(pivot, quickSort(right, compare));
}

// 현재 내 위치에서 가까운 관광지 순으로 배열
function getSortedNearestSpots(map) {
	var center = map.getCenter(); // 지도의 현재 중심좌표를 가져온다.
	var data = window.globalData;
	
	// 거리계산하는 함수 -> Haversine 공식 (위도 경도 사용하여 최단거리 구하기)
	function getDistance(lat1, lon1, lat2, lon2) {
		var distance;
		var radius = 6371; // 지구 반지름
		var toRadian = Math.PI / 180;
		
		var dLat = Math.abs(lat2-lat1) * toRadian;
		var dLng = Math.abs(lon2-lon1) * toRadian;
		
		var sinLat = Math.sin(dLat/2);
		var sinLng = Math.sin(dLng/2);
		
		var squareRoot = Math.sqrt(
			sinLat * sinLng + 
			Math.cos(lat1 * toRadian) * Math.cos(lat2 * toRadian) * sinLat * sinLng);
		distance = 2 * radius * Math.asin(squareRoot);
		
		return distance;
	}
	
	// 각 관광지와 현위치 거리 계산후, quickSort를 이용하여 정렬
	function compareSpots(a, b) {
		var distanceA = getDistance(center.lat, center.lng, a.lat, a.lng);
		var distanceB = getDistance(center.lat, center.lng, b.lat, b.lng);
		return distanceA - distanceB;
	}
	
	return quickSort(data, compareSpots);
	
}



var positions; // marker 배열.
function makeList(data) {
	console.log(1);
	// document.querySelector("table").setAttribute("style", "display: ;");
	let trips = data;
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
			content: `      <div class="panel panel-default">
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
        
        </div>
      </div>
    </div>`, // 인포윈도우에 표시할 내용
		});

		// 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
		// 이벤트 리스너로는 클로저를 만들어 등록합니다
		// for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
		kakao.maps.event.addListener(
			marker,
			"mouseover",
			makeOverListener(map, marker, infowindow)
		);
		kakao.maps.event.addListener(
			marker,
			"mouseout",
			makeOutListener(infowindow)
		);

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
