login = () => {
  let id = document.getElementById("identifier").value;
  let password = document.getElementById("password").value;
  if (id.length > 0 && password.length > 0) {
    if (id === "ssafy" && password === "1234") {
      closeModal("loginModal");
      alert("로그인에 성공하셨습니다!");

      // changeNavMenu();
      document.getElementById("user_logout").style.display = "none";
      document.getElementById("user_login").style.display = "block";
      document.getElementById("star").style.display = "block";
    } else {
      alert("아이디 또는 비밀번호를 확인하세요.");
    }
  } else {
    alert("아이디와 비밀번호를 입력하세요.");
  }
};
closeModal = (param) => {
  var modal = document.getElementById(param);
  modal.classList.remove("show");
  modal.setAttribute("aria-hidden", "true");
  var modalBackdrop = document.getElementsByClassName("modal-backdrop");
  document.body.removeChild(modalBackdrop[0]);
  document.body.classList.remove("modal-open");
  setTimeout(() => {
    modal.setAttribute("style", "display: none");
  }, 300);
  document.body.style.paddingRight = "0";
};
