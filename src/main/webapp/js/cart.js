function updateCart(id, e) {
    let count = e.value;
    if (count > 0 && count < 100) {
        $.ajax({
            type: "PUT",
            url: "cart?productID=" + id + "&count=" + count,
            success: function (response) {
                let tableCart = document.getElementById("cart");
                let currRow = e.parentElement.parentElement.rowIndex;

                tableCart.rows[currRow].cells[4].innerHTML = response.total;
                tableCart.rows[tableCart.rows.length - 1].cells[1].innerHTML = response.cartPrice;
            }
        })
    }
}

function deleteFromCart(id, row) {
    $.ajax({
        type: "DELETE",
        url: "cart?productID=" + id,
        success: function (response) {
            let tableCart = document.getElementById("cart");
            let currRow = row.parentElement.parentElement.rowIndex;
            tableCart.deleteRow(currRow);
            tableCart.rows[tableCart.rows.length - 1].cells[1].innerHTML = response.cartPrice;

        }
    })
}