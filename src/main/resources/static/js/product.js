
//
// function filterProducts(categoryName) {
//     var products = document.getElementsByClassName('product-item');
//
//     for (var i = 0; i < products.length; i++) {
//         if (categoryName === 'all') {
//             products[i].style.display = 'block';
//         }
//         else {
//             if (products[i].getAttribute('data-category') === categoryName) {
//                 products[i].style.display = 'block';
//             }
//             else {
//                 products[i].style.display = 'none';
//             }
//         }
//     }
// }

// 제일 상단 팝업창
function closePopup() {
    document.getElementById('popup').style.display = 'none';
    document.getElementById('popup').style.height = '0';
    document.getElementById('popup').style.padding = '0';
}

function filterProducts(category) {
    var products = document.querySelectorAll('.product-item');
    products.forEach(function(product) {
        if (category === 'all' || product.getAttribute('data-category') === category) {
            product.style.display = 'block';
        } else {
            product.style.display = 'none';
        }
    });
}

// 이거 적용하면 카테고리별로 나눠지는데, 이거 적용하면 전체보기가 안됨 그리고 쉬발 관리자 안넘어감
//
// document.addEventListener('DOMContentLoaded', function() {
//     var links = document.querySelectorAll('nav ul li a');
//     links.forEach(function(link) {
//         link.addEventListener('click', function(event) {
//             event.preventDefault();
//             var category = this.getAttribute('data-category');
//             filterProducts(category);
//         });
//     });
// });