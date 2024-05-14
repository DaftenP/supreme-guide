logout = () => {
  document.getElementById("user_logout").style.display = "block";
  document.getElementById("user_login").style.display = "none";
  document.getElementById("star").style.display = "none";
  closeModal("logoutModal");
  alert("로그아웃 하였습니다.");
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
