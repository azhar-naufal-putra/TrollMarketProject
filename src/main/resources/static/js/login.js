(() => {
    let loginUrl = "http://localhost:8080/api/account/authenticate"
    let loginButton = document.querySelector("form button");
    let loginForm = document.querySelector("form");
    loginButton.addEventListener('click', (event) => {
        event.preventDefault();
        let username = document.querySelector("#username").value;
        let password = document.querySelector("#password").value;
        let role = document.querySelector("#role").value;
        let dataLogin = {
            username:username,
            password:password,
            role:role
        };
        let request = new XMLHttpRequest();
        request.open("POST", loginUrl);
        request.setRequestHeader("Content-Type", "application/json");
        request.send(JSON.stringify(dataLogin));
        request.onload = () => {
            if(request.status === 200){
                console.log(request.status)
                localStorage.setItem("access-token", request.response);
                loginForm.submit();
            }else {
                console.log(request.status)
                window.location.href = "http://localhost:8080/account/fail-login";
            }
        }
    })
})()