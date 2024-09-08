(() => {
    let mainUrl = "http://localhost:8080/api/shop";
    let modalInfo = document.querySelector(".modal-wrapper.detail");
    let infoButtons = document.querySelectorAll(".detail-info-button");
    let closeModalInfoButton = modalInfo.querySelector(".close-button");
    let addCartButtons = document.querySelectorAll(".add-cart-button");
    let modalCart = document.querySelector(".modal-wrapper.form");
    let submitCartButton = modalCart.querySelector(".add-button");
    let accessToken = (JSON.parse(localStorage.getItem("access-token"))).token;
    let cancelButton = modalCart.querySelector(".cancel-button");
    let alertBox = document.querySelector(".alert.alert-success");

    infoButtons.forEach(button => {
        button.addEventListener("click", () => {
            popupModal(modalInfo);
            let merchandiseId = button.getAttribute("merchandise-id");
            let request = new XMLHttpRequest();
            request.open("GET", `${mainUrl}/merchandise-info?merchandiseId=${merchandiseId}`);
            request.setRequestHeader("Authorization", "Bearer " + accessToken);
            request.send();
            request.onload = () => {
                populateInfoMerchandise(JSON.parse(request.response));
            };
        });
    });

    addCartButtons.forEach(button => {
        button.addEventListener("click", () =>{
            popupModal(modalCart);
            modalCart.querySelector("#quantity").value = null;
            let merchandiseId = button.getAttribute("merchandise-id");
            let merchandiseIdField = modalCart.querySelector("#merchandiseId");
            merchandiseIdField.value = merchandiseId;
            let request = new XMLHttpRequest();
            request.open("GET", `${mainUrl}/shipper-options`);
            request.setRequestHeader("Authorization", "Bearer " + accessToken);
            request.send();
            request.onload = () =>{
                populateShipperOptions(JSON.parse(request.response));
            }
        });
    });

    let populateShipperOptions = (shippers) => {
        let selectShipper = modalCart.querySelector("#shipperId");
        selectShipper.innerHTML = "";
        let defaultOption = document.createElement("option");
        defaultOption.textContent = "Select Shipment...";
        defaultOption.value = null;
        selectShipper.appendChild(defaultOption);
        for({shipperId, shipperName} of shippers){
            let shipperOption = document.createElement("option");
            shipperOption.value = shipperId;
            shipperOption.textContent = shipperName;
            selectShipper.appendChild(shipperOption);
        }
    }

    submitCartButton.addEventListener("click", () =>{
        let merchandiseId = modalCart.querySelector("#merchandiseId").value;
        let buyerUsername = modalCart.querySelector("#buyerUsername").value;
        let shipperId = modalCart.querySelector("#shipperId").value;
        let quantity = modalCart.querySelector("#quantity").value;
        let data = {
            merchandiseId: merchandiseId,
            quantity: quantity,
            shipperId: shipperId,
            buyerUsername: buyerUsername
        }

        let request = new XMLHttpRequest();
        request.open("POST", `${mainUrl}/add-merchandise-to-cart`);
        request.setRequestHeader("Content-Type", "application/json");
        request.setRequestHeader("Authorization", "Bearer "+accessToken);
        request.send(JSON.stringify(data));
        request.onload = () =>{
            if(request.status === 200){
                closeModal(modalCart);
                alertBox.hidden = false;
                alertBox.textContent = "Product Successfully added to Cart";
                setTimeout(clearAlertBox, 3000);
            }else if(request.status === 422){
                populateError(JSON.parse(request.responseText));
            }
        };

    });

    let populateError = (error) => {
        for(let i = 0; i < error.length; i++){
            if(error[i].field == "shipperId"){
                let shipperErrorField = modalCart.querySelector("#shipperId + .field-validation-error");
                shipperErrorField.textContent = error[i].defaultMessage;
            }
            if(error[i].field == "quantity"){
                let quantityErrorField = modalCart.querySelector("#quantity + .field-validation-error");
                quantityErrorField.textContent = error[i].defaultMessage;
            }
        }
        setTimeout(clearAllErrorFields, 3000);
    }

    let clearAllErrorFields = () => {
        let shipperErrorField = modalCart.querySelector("#shipperId + .field-validation-error");
        let quantityErrorField = modalCart.querySelector("#quantity + .field-validation-error");
        shipperErrorField.textContent = "";
        quantityErrorField.textContent = "";
    }

    let clearAlertBox = () => {
        alertBox.textContent = "";
        alertBox.hidden = true;
    }
    

    cancelButton.addEventListener("click", () => {
        closeModal(modalCart);
    });

    closeModalInfoButton.addEventListener("click", () => {
        closeModal(modalInfo); 
    });

    let populateInfoMerchandise = (merchandise) => {
        let merchandiseNameField = modalInfo.querySelector("[name='merchandiseName']");
        let categoryNameField = modalInfo.querySelector("[name='categoryName']");
        let descriptionField = modalInfo.querySelector("[name='description']");
        let priceField = modalInfo.querySelector("[name='price']");
        let sellerNameField = modalInfo.querySelector("[name='sellerName']");

        merchandiseNameField.textContent = merchandise.merchandiseName;
        categoryNameField.textContent = merchandise.categoryName;
        descriptionField.textContent = merchandise.description;
        priceField.textContent = merchandise.price;
        sellerNameField.textContent = merchandise.sellerName;
    };


    let closeModal = (modal) => { 
        modal.classList.add("disable");
    };
    let popupModal = (modal) => {
        modal.classList.remove("disable");
    };
})();