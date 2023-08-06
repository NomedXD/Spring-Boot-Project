function calculateTotal() {
    let singlePrices = document.getElementsByClassName('mb-0 singlePrice');
    let productCounts = document.getElementsByClassName('form-control form-control-sm productCount');
    let totalElement = document.getElementById('total');
    let total = 0;
    for (let i = 0; i < singlePrices.length; ++i) {
        total = total + parseFloat(singlePrices[i].innerHTML) * parseInt(productCounts[i].value, 10);
    }
    totalElement.innerHTML = total.toString(10) + '$';
}
let selectAddress = document.getElementById('addressSelect');
let addressDiv = document.getElementById('addressDiv');
let addressH5 = document.getElementById('addressH5');
selectAddress.onchange = function () {
    if(selectAddress.value === "0") {
        addressDiv.style.display = "none";
        addressH5.style.display = "none";
    } else {
        addressDiv.style.display = "block";
        addressH5.style.display = "block";
    }
}