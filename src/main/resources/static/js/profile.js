(() => {
    let checkIdentity = () => {
        let urlParams = new URLSearchParams(window.location.search);
        let usernameParam = urlParams.get("username");
        let usernameLogin = document.querySelector(".user .full-name").textContent;
        if(usernameParam != usernameLogin){
            window.location.href = "http://localhost:8080/account/access-denied";
        }
    }
    checkIdentity();
    
    let mainUrl = "http://localhost:8080/api/profile";
    let modalBalance = document.querySelector(".modal-wrapper");
    let accessToken = (JSON.parse(localStorage.getItem("access-token"))).token;
    let balanceButton = document.querySelector(".balance-button");
    let addBalanceButton = modalBalance.querySelector(".add-button");
    let cancelButton = modalBalance.querySelector(".cancel-button");
    

    if(balanceButton){
        balanceButton.addEventListener("click", () => {
            popupModal(modalBalance);
            let balanceField = modalBalance.querySelector("#balance");
            balanceField.value = null;
        });
    }

    cancelButton.addEventListener("click", () =>{
        closeModal(modalBalance);
    });

    addBalanceButton.addEventListener("click", () => {
        let username = modalBalance.querySelector("#username").value;
        let balance = modalBalance.querySelector("#balance").value;
        let data = {
            username: username,
            balance: balance
        }

        let request = new XMLHttpRequest();
        request.open("PUT", `${mainUrl}/balance`)
        request.setRequestHeader("Content-Type", "application/json");
        request.setRequestHeader("Authorization", "Bearer "+accessToken);
        request.send(JSON.stringify(data));
        request.onload = () => {
            if(request.status === 422){
                populateError(JSON.parse(request.response));
            }else if(request.status === 200){
                window.location.reload();
            }
        }
    });

    let populateError = (error) => {
        for(let i = 0; i < error.length; i++){
            if(error[i].field == "balance"){
                let balanceErrorField = modalBalance.querySelector("#balance + .field-validation-error");
                balanceErrorField.textContent = error[i].defaultMessage;
            }
        }
        setTimeout(clearAllErrorFields, 3000);
    }

    let clearAllErrorFields = () => {
        let balanceErrorField = modalBalance.querySelector("#balance + .field-validation-error");
        balanceErrorField.textContent = "";
    }

    let popupModal = (modal) =>{
        modal.classList.remove("disable");
    };
    let closeModal = (modal) => {
        modal.classList.add("disable");
    };
})();