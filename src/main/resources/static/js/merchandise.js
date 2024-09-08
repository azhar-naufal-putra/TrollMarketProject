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

    let mainUrl = "http://localhost:8080/api/merchandise";
    let modalInfo = document.querySelector(".modal-wrapper");
    let infoButtons = document.querySelectorAll(".info-button");
    let closeButton = document.querySelector(".modal-wrapper .close-button");

    infoButtons.forEach(button => {
        button.addEventListener("click", () => {
            popupModal(modalInfo);
            let merchandiseId = button.getAttribute("merchandise-id");
            let accessToken = (JSON.parse(localStorage.getItem("access-token"))).token;
            let request = new XMLHttpRequest();
            request.open("GET", `${mainUrl}/merchandise-info/${merchandiseId}`);
            request.setRequestHeader("Authorization", "Bearer " + accessToken);
            request.send();
            request.onload = () => {
                console.log(JSON.parse(request.response));
                populateInfoMerchandise(JSON.parse(request.response));
            }

        });
    });

    closeButton.addEventListener("click", () => {
        closeModal(modalInfo);
    });

    let populateInfoMerchandise = (merchandise) => {
        let merchandiseNameField = document.querySelector(".modal-wrapper [name='merchandiseName']");
        let categoryNameField = document.querySelector(".modal-wrapper [name='categoryName']");
        let descriptionField = document.querySelector(".modal-wrapper [name='description']");
        let priceField = document.querySelector(".modal-wrapper [name='price']");
        let discontinueField = document.querySelector(".modal-wrapper [name='discontinue']");
        merchandiseNameField.textContent = merchandise.merchandiseName;
        categoryNameField.textContent = merchandise.categoryName;
        descriptionField.textContent = merchandise.description;
        priceField.textContent = merchandise.price;
        discontinueField.textContent = merchandise.discontinue;

    };

    let popupModal = (modal) => {
        modal.classList.remove("disable");
    };
    let closeModal = (modal) => {
        modal.classList.add("disable");
    };
})()