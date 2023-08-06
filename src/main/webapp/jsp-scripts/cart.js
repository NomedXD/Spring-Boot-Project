function calculateTotal() {
    let singlePrices = document.getElementsByClassName('.singlePrice');
    let productCounts = document.getElementsByClassName('.productCount');
    let totalElement = document.getElementById('total');
    let total = 0;
    for (let i = 0; i < singlePrices.length; ++i) {
        total = total + parseFloat(singlePrices[i].innerHTML) * parseInt(productCounts[i].innerHTML, 10);
    }
    totalElement.innerHTML = total.toString(10) + '$';
}