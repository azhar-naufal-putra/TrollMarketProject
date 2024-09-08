(() => {
    let mainUrl = "http://localhost:8080/api/shipper"
    let addButton = document.querySelector(".create-button");
    let modalInsertUpdate = document.querySelector(".modal-wrapper");
    let createUpdateButton = document.querySelector(".upsert-form .create-udpate-button");
    let cancelButton = document.querySelector(".cancel-button");
    let editButtons = document.querySelectorAll(".edit-button");

    addButton.addEventListener("click", () => {
        popupModal(modalInsertUpdate);
        createUpdateButton.textContent = "Create";
        let shipperName = document.querySelector("#shipperName");
        let price = document.querySelector("#price");
        let service = document.querySelector("#service");
        shipperName.value = null;
        price.value = 0;
        service.checked = false;
    });
    
    editButtons.forEach(editButton => {
        editButton.addEventListener("click", () => {
            popupModal(modalInsertUpdate);
            createUpdateButton.textContent = "Update";
            let shipperId = editButton.getAttribute("shipper-id");
            let accessToken = (JSON.parse(localStorage.getItem("access-token"))).token;
            let request = new XMLHttpRequest();
            request.open("GET", `${mainUrl}/shipper-form/${shipperId}`);
            request.setRequestHeader("Authorization", "Bearer " + accessToken);
            request.send();
            request.onload = () => {
                let dataShipper = JSON.parse(request.response);
                let shipperIdField = document.querySelector("#shipperId");
                let shipperNameField = document.querySelector("#shipperName");
                let priceField = document.querySelector("#price");
                let serviceCheckBox = document.querySelector("#service");
                shipperIdField.value = dataShipper.shipperId;
                shipperNameField.value = dataShipper.shipperName;
                priceField.value = dataShipper.price;
                serviceCheckBox.checked = dataShipper.service;
            }
        });
    });

    createUpdateButton.addEventListener("click", () => {
        let shipperId = document.querySelector("#shipperId").value;
        let shipperName = document.querySelector("#shipperName").value;
        let price = document.querySelector("#price").value;
        let service = document.querySelector("#service").checked;
        let data = {
            shipperId: shipperId,
            shipperName: shipperName,
            price: price,
            service: service
        }
        let request = new XMLHttpRequest();
        let accessToken = (JSON.parse(localStorage.getItem("access-token"))).token;
        request.open("POST", `${mainUrl}/shipper-form`);
        request.setRequestHeader("Content-Type", "application/json");
        request.setRequestHeader("Authorization", "Bearer " + accessToken);
        request.send(JSON.stringify(data));
        request.onload = () => {
            if(request.status === 422){
                populateError(JSON.parse(request.response));
            }else if(request.status === 200){
                window.location.reload();
            }else{
                alert("error");
            }
        }
    });

    cancelButton.addEventListener("click", () => {
        closeModal(modalInsertUpdate);
    });

    let populateError = (error) => {
        for(let i = 0; i < error.length; i++){
            if((error[i].code != null && error[i].code == "UniqueShipperName") || error[i].field == "shipperName"){
                let shipperNameErrorField = document.querySelector("#shipperName + .field-validation-error");
                shipperNameErrorField.textContent = error[i].defaultMessage;
            }
            if(error[i].field == "price"){
                let priceErrorField = document.querySelector("#price + .field-validation-error");
                priceErrorField.textContent = error[i].defaultMessage;
            }
        }
        setTimeout(clearAllErrorFields, 3000);
    }

    let clearAllErrorFields = () => {
        let shipperNameErrorField = document.querySelector("#shipperName + .field-validation-error");
        let priceErrorField = document.querySelector("#price + .field-validation-error");
        shipperNameErrorField.textContent = "";
        priceErrorField.textContent = "";
    }



    let popupModal = (modal) => {
        modal.classList.remove("disable");
    }

    let closeModal = (modal) => {
        modal.classList.add("disable");
    }
})()