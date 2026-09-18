// Relative path works because this file is served by the same Spring Boot app
const API_URL = "/api/products";

document.addEventListener("DOMContentLoaded", loadProducts);


// Load all products into the table
function loadProducts() {
    fetch(API_URL)
        .then(function (res) {
            return res.json();
        })
        .then(function (products) {
            const tbody = document.getElementById("productTable");
            tbody.innerHTML = "";

            products.forEach(function (p) {
                const row = `
          <tr>
            <td>${p.productId}</td>
            <td>${p.name}</td>
            <td>${p.price}</td>
            <td>${p.category}</td>
            <td>${p.quantity}</td>
            <td class="actions">
              <button onclick='editProduct(${JSON.stringify(p)})'>Edit</button>
              <button onclick="deleteProduct(${p.productId})">Delete</button>
            </td>
          </tr>
        `;
                tbody.innerHTML += row;
            });
        })
        .catch(function (err) {
            console.error("Error loading products:", err);
        });
}


// Save (Add or Update) a product
function saveProduct() {
    const id = document.getElementById("productId").value;
    const name = document.getElementById("name").value;
    const price = document.getElementById("price").value;
    const category = document.getElementById("category").value;
    const quantity = document.getElementById("quantity").value;

    const product = {
        name: name,
        price: parseFloat(price),
        category: category,
        quantity: parseInt(quantity)
    };

    const isUpdate = id !== "";
    const url = isUpdate ? API_URL + "/" + id : API_URL;
    const method = isUpdate ? "PUT" : "POST";

    fetch(url, {
        method: method,
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(product)
    })
        .then(function (res) {
            if (!res.ok) {
                throw new Error("Request failed with status " + res.status);
            }
            return res.json();
        })
        .then(function () {
            resetForm();
            loadProducts();
        })
        .catch(function (err) {
            alert("Error: " + err.message);
        });
}


// Fill the form when Edit is clicked
function editProduct(product) {
    document.getElementById("productId").value = product.productId;
    document.getElementById("name").value = product.name;
    document.getElementById("price").value = product.price;
    document.getElementById("category").value = product.category;
    document.getElementById("quantity").value = product.quantity;
}


// Delete a product
function deleteProduct(id) {
    const confirmDelete = confirm("Are you sure you want to delete this product?");
    if (!confirmDelete) {
        return;
    }

    fetch(API_URL + "/" + id, {
        method: "DELETE"
    })
        .then(function () {
            loadProducts();
        })
        .catch(function (err) {
            alert("Error deleting product: " + err.message);
        });
}


// Clear the form
function resetForm() {
    document.getElementById("productId").value = "";
    document.getElementById("name").value = "";
    document.getElementById("price").value = "";
    document.getElementById("category").value = "";
    document.getElementById("quantity").value = "";
}