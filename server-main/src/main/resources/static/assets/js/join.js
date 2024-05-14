joinPwCheck = function(event) {
	event.preventDefault(); // 폼 제출 기본 동작 방지
	
	// 비밀번호와 비밀번호 확인 필드의 값을 가져옴
	var password = document.getElementById("regist_pw").value;
	var passwordRepeat = document.getElementById("regist_pw-repeat").value;

	// 비밀번호와 비밀번호 확인 값이 같은지 확인
	if (password !== passwordRepeat) {
		alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
		console.log("새 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
		return; // 값이 다르면 제출 중단
	}

	// 값이 일치하면 폼을 제출
	document.getElementById("userRegisterForm").submit();
}