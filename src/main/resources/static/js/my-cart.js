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

    let mainUrl = "http://localhost:8080/api/my-cart"
    let modalPurchase = document.querySelector(".modal-wrapper");
    let purchasePopupButton = document.querySelector(".purchase-popup-button");
    let purchaseButton = modalPurchase.querySelector(".purchase-button");
    let cancelPurchaseButton = modalPurchase.querySelector(".cancel-button");
    let accessToken = JSON.parse(localStorage.getItem("access-token")).token;

    purchasePopupButton.addEventListener("click", () => {
        popupModal(modalPurchase);
        // request to get info balance and total Price
        let request = new XMLHttpRequest();
        let orderId = modalPurchase.querySelector("#orderId").value;
        let username = modalPurchase.querySelector("#username").value;
        request.open("GET", `${mainUrl}/payment-info?orderId=${orderId}&username=${username}`);
        request.setRequestHeader("Authorization", "Bearer "+accessToken);
        request.send();
        request.onload = () => {
            populatePaymentInfo(JSON.parse(request.response));
        };
    });

    let populatePaymentInfo = (payment) => {
        let myBalanceField = modalPurchase.querySelector("#myBalance");
        let totalPaymentField = modalPurchase.querySelector("#totalPayment");
        myBalanceField.value = payment.myBalance;
        totalPaymentField.value = payment.totalPayment;
        let infoField = modalPurchase.querySelector("table tbody tr td");
        infoField.textContent = `Your Balance is ${payment.myBalanceParsed} and the total payment that you must pay is ${payment.totalPaymentParsed}. Are you sure you want to continue this payment?`
    };

    purchaseButton.addEventListener("click", () => {
        let myBalance = modalPurchase.querySelector("#myBalance").value;
        let totalPayment = modalPurchase.querySelector("#totalPayment").value;
        let username = modalPurchase.querySelector("#username").value;
        let orderId = modalPurchase.querySelector("#orderId").value;

        let data = {
            username: username,
            orderId: orderId,
            myBalance: myBalance,
            totalPayment: totalPayment
        }

        let request = new XMLHttpRequest();
        request.open("PUT", `${mainUrl}/purchase`);
        request.setRequestHeader("Content-Type", "application/json");
        request.setRequestHeader("Authorization", "Bearer "+accessToken);
        request.send(JSON.stringify(data));
        request.onload = () => {
            if(request.status === 422){
                populateError(JSON.parse(request.response));
            }else if(request.status === 200){
                window.location.href = `http://localhost:8080/profile?username=${username}`;
            }
        };

    });

    let populateError = (error) => {
        for(let i = 0; i < error.length; i++){
            if(error[i].code == "PaymentCheck"){
                let balanceErrorField = modalPurchase.querySelector(".field-validation-error");
                balanceErrorField.textContent = error[i].defaultMessage;
            }
        }
        setTimeout(clearAllErrorFields, 3000);
    }

    let clearAllErrorFields = () => {
        let balanceErrorField = modalPurchase.querySelector(".field-validation-error");
        balanceErrorField.textContent = "";
    }

    cancelPurchaseButton.addEventListener("click", () => {
        closeModal(modalPurchase);
    });

    let closeModal = (modal) => {
        modal.classList.add("disable");
    };

    let popupModal = (modal) => {
        modal.classList.remove("disable");
    };
})();