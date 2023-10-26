import React, { useState, useEffect } from "react";
import "./admin.css";
//import products from "../products/products.json";
import "bootstrap/dist/css/bootstrap.min.css";
import Swal from "sweetalert2";


function uuidv4() {
  return "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g, function (c) {
    var r = (Math.random() * 16) | 0,
      v = c === "x" ? r : (r & 0x3) | 0x8;
    return v.toString(16);
  });
}
const Admin = () => {
  const [showAddProductForm, setShowAddProductForm] = useState(false);
  const [showEditProductForm, setShowEditProductForm] = useState(false);
  const [showAddAdminForm, setShowAddAdminForm] = useState(false);
  const [showAllProducts, setShowAllProducts] = useState(false);

  const [productData, setProductData] = useState({
    name: "",
    description: "",
    vendor: "",
    urlSlug: "",
    sku: "",
    price: "",
    discountPercent: "",
    isFeatured: false,
    isDelete: false,
  });

  const [products, setProducts] = useState([]);

  useEffect(() => {
    // Fetch products from the API endpoint
    fetch("http://localhost:8080/Elektromart/products/")
      .then((response) => response.json())
      .then((data) => {
        setProducts(data);
        setProductList(data); // Update product list here
      })
      .catch((error) => console.error("Error fetching products: ", error));
  }, []);

  const [selectedProduct, setSelectedProduct] = useState(null);
  const [productList, setProductList] = useState(products);

  const [adminData, setAdminData] = useState({
    adminEmail: "",
    adminName: "",
    adminToken: "",
  });

  useEffect(() => {
    const downloadLink = document.getElementById('downloadLink');

    if (showAllProducts && downloadLink) {
      downloadLink.addEventListener('click', handleDownloadClick);
    }

    return () => {
      if (downloadLink) {
        downloadLink.removeEventListener('click', handleDownloadClick);
      }
    };
  }, [showAllProducts]);

  const handleDownloadClick = async (e) => {
    try {
      const response = await fetch('http://localhost:8080/Elektromart_war/products/download');
      
      console.log(response.ok ? 'Download successful' : 'Download failed');
      if (!response.ok) {
        Swal.fire({
          icon: 'error',
          title: 'Error!',
          text: 'An error occurred while downloading the products.',
        });
      } else {
        const blob = await response.blob();
        const blobURL = URL.createObjectURL(blob);

        const a = document.createElement('a');
        a.href = blobURL;
        a.download = 'products.json';
        document.body.appendChild(a);
        a.click();

        URL.revokeObjectURL(blobURL);

        document.body.removeChild(a);
        }
      } catch (error) {
        Swal.fire({
          icon: 'error',
          title: 'Error!',
          text: 'An error occurred while downloading the products.',
        });
    }
  };

  const handleAddProductClick = () => {
    setShowAddProductForm(true);
    setShowEditProductForm(false);
    setShowAddAdminForm(false);
    setShowAllProducts(false);
  };

  const handleEditProductClick = () => {
    setShowAddProductForm(false);
    setShowEditProductForm(true);
    setShowAddAdminForm(false);
    setShowAllProducts(false);
  };

  const handleViewAllProductClick = () => {
    setShowAddProductForm(false);
    setShowEditProductForm(false);
    setShowAddAdminForm(false);
    setShowAllProducts(true);
  };

  const handleAddAdminClick = () => {
    setShowAddProductForm(false);
    setShowEditProductForm(false);
    setShowAddAdminForm(true);
    setShowAllProducts(false);
  };

  const handleFormChange = (e) => {
    const { name, value } = e.target;
    setProductData({
      ...productData,
      [name]: name === "isFeatured" ? e.target.checked : value,
    });
  };
  const handleProductSelect = (productId) => {
    const selectedProduct = products.find(
      (product) => product.id === productId
    );
    if (selectedProduct) {
      setSelectedProduct(selectedProduct);
      setProductData(selectedProduct);
    }
  };

  const handleAddAdminChange = (e) => {
    const { name, value } = e.target;
    setAdminData({
      ...adminData,
      [name]: value,
    });
  };

  const handleAddProductSubmit = (e) => {
    e.preventDefault();
    const randomSKU = uuidv4();

    setProductData({
      ...productData,
      sku: randomSKU,
      urlSlug: productData.name.toLowerCase().replace(/ /g, "-"), // Generate urlSlug based on product name
    });
    // Make a POST request to create a new product
    fetch("http://localhost:8080/Elektromart/products/", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(productData),
    })
      .then((response) => {
        if (response.ok) {
          // Close the modal for adding a new product
          setShowAddProductForm(false);

          return response.json(); // Parse the response if it's OK
        }
        throw new Error("Network response was not ok.");
      })
      .then((data) => {
        setProducts([...products, data]);
        setProductData({
          name: "",
          description: "",
          vendor: "",
          urlSlug: "",
          sku: "",
          price: "",
          discountPercent: "",
          isFeatured: false,
          isDelete: false,
        });
      })
      .catch((error) => console.error("Error adding product: ", error));
  };

  const handleEditProductSubmit = (e) => {
    e.preventDefault();
    if (!selectedProduct) {
      return;
    }

    // Make a POST request to update the selected product
    fetch(`http://localhost:8080/Elektromart/products/${selectedProduct.id}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(productData),
    })
      .then((response) => {
        if (response.ok) {
          // Clear the selected product and form fields
          setSelectedProduct(null);
          setProductData({
            name: "",
            description: "",
            vendor: "",
            urlSlug: "",
            sku: "",
            price: "",
            discountPercent: "",
            isFeatured: false,
            isDelete: false,
          });
          return response.json();
        }
        throw new Error("Network response was not ok.");
      })
      .then((data) => {
        // Update the products state with the updated product
        const updatedProducts = products.map((product) =>
          product.id === data.id ? data : product
        );
        setProducts(updatedProducts);
        setShowEditProductForm(false); // Close the edit product form
      })
      .catch((error) => console.error("Error editing product: ", error));
  };

  const handleAddAdminSubmit = (e) => {
    e.preventDefault();
    console.log("New admin data submitted:", adminData);
    setAdminData({
      adminEmail: "",
      adminName: "",
      adminToken: "",
    });
  };

  return (
    <div className="admin-container">
      <h2>Admin Panel</h2>
      <button className="adminButton" onClick={handleAddProductClick}>
        Add New Product
      </button>
      <button className="adminButton" onClick={handleEditProductClick}>
        Edit a Product
      </button>
      <button className="adminButton" onClick={handleViewAllProductClick}>
        View all Products
      </button>
      <button className="adminButton" onClick={handleAddAdminClick}>
        Add a New Admin
      </button>

      {showAddProductForm && (
        <form className="product-form" onSubmit={handleAddProductSubmit}>
          {/* Product fields */}
          <div className="input-group mt-3">
            <label htmlFor="productName" className="input-group-text">
              Product Name:
            </label>
            <input
              id="productName"
              type="text"
              className="form-control"
              name="name" // Match the key in productData
              value={productData.name}
              onChange={handleFormChange}
              required
            />
          </div>

          <div className="input-group mt-3">
            <label htmlFor="productDescription" className="input-group-text">
              Product Description:
            </label>
            <textarea
              id="productDescription"
              className="form-control"
              name="description"
              value={productData.description}
              onChange={handleFormChange}
              required
            />
          </div>
          <div className="input-group mt-3">
            <label htmlFor="productVendor" className="input-group-text">
              Product Vendor:
            </label>
            <input
              id="productVendor"
              type="text"
              className="form-control"
              name="vendor"
              value={productData.vendor}
              onChange={handleFormChange}
              required
            />
          </div>
          <div className="input-group mt-3">
            <label htmlFor="productSKU">
            </label>
            <input
              id="productSKU"
              type="hidden"
              className="form-control"
              name="sku"
              value={productData.sku}
              onChange={handleFormChange}
            />
          </div>
          <div className="input-group mt-3">
            <label htmlFor="urlSlug"></label>
            <input
              id="urlSlug"
              type="hidden"
              className="form-control"
              name="urlSlug"
              value={productData.urlSlug}
              onChange={handleFormChange}
            />
          </div>
          <div className="input-group mt-3">
            <label htmlFor="productPrice" className="input-group-text">
              Product Price:
            </label>
            <input
              id="productPrice"
              type="number"
              className="form-control"
              name="price"
              value={productData.price}
              onChange={handleFormChange}
              required
            />
          </div>
          <div className="input-group mt-3">
            <label htmlFor="productDiscount" className="input-group-text">
              Discount Percent:
            </label>
            <input
              id="productDiscount"
              type="number"
              className="form-control"
              name="discountPercent"
              value={productData.discountPercent}
              onChange={handleFormChange}
            />
          </div>
          <div className="input-group mt-3">
            <label htmlFor="isFeatured" className="input-group-text">
              Featured:
            </label>
            <input
              id="isFeatured"
              type="checkbox"
              className="form-check-input"
              name="isFeatured"
              checked={productData.isFeatured}
              onChange={handleFormChange}
            />
          </div>
          <button type="submit" className="btn btn-primary mt-3">
            Add Product
          </button>
        </form>
      )}
      {showEditProductForm && (
        <form className="product-form" onSubmit={handleEditProductSubmit}>
          {/* Product selection dropdown */}
          <div className="input-group mt-3">
            <label htmlFor="editProductId" className="input-group-text">
              Select Product:
            </label>
            <select
              id="editProductId"
              className="form-select"
              name="editProductId"
              value={selectedProduct ? selectedProduct.id : ""}
              onChange={(e) => handleProductSelect(parseInt(e.target.value))}
              required
            >
              <option value="">-- Select a product --</option>
              {products.map((product) => (
                <option key={product.id} value={product.id}>
                  {product.name}
                </option>
              ))}
            </select>
          </div>
          {/* Product fields */}
          <div className="input-group mt-3">
            <label htmlFor="productName" className="input-group-text">
              Product Name:
            </label>
            <input
              id="productName"
              type="text"
              className="form-control"
              name="name"
              value={productData.name}
              onChange={handleFormChange}
              required
            />
          </div>
          <div className="input-group mt-3">
            <label htmlFor="productDescription" className="input-group-text">
              Product Description:
            </label>
            <textarea
              id="productDescription"
              className="form-control"
              name="description"
              value={productData.description}
              onChange={handleFormChange}
              required
            />
          </div>
          <div className="input-group mt-3">
            <label htmlFor="productVendor" className="input-group-text">
              Product Vendor:
            </label>
            <input
              id="productVendor"
              type="text"
              className="form-control"
              name="vendor"
              value={productData.vendor}
              onChange={handleFormChange}
              required
            />
          </div>
          <div className="input-group mt-3">
            <label htmlFor="productSKU" className="input-group-text">
              Product SKU:
            </label>
            <input
              id="productSKU"
              type="text"
              className="form-control"
              name="sku"
              value={productData.sku}
              onChange={handleFormChange}
              required
            />
          </div>

          <div className="input-group mt-3">
            <label htmlFor="urlSlug" className="input-group-text">
              URL Slug:
            </label>
            <input
              id="urlSlug"
              type="text"
              className="form-control"
              name="urlSlug"
              value={productData.urlSlug}
              onChange={handleFormChange}
              required
            />
          </div>
          <div className="input-group mt-3">
            <label htmlFor="productPrice" className="input-group-text">
              Product Price:
            </label>
            <input
              id="productPrice"
              type="number"
              className="form-control"
              name="price"
              value={productData.price}
              onChange={handleFormChange}
              required
            />
          </div>
          <div className="input-group mt-3">
            <label htmlFor="productDiscount" className="input-group-text">
              Discount Percent:
            </label>
            <input
              id="productDiscount"
              type="number"
              className="form-control"
              name="discountPercent"
              value={productData.discountPercent}
              onChange={handleFormChange}
            />
          </div>
          <div className="input-group mt-3">
            <label htmlFor="isFeatured" className="input-group-text">
              Featured:
            </label>
            <input
              id="isFeatured"
              type="checkbox"
              className="form-check-input"
              name="isFeatured"
              checked={productData.isFeatured}
              onChange={handleFormChange}
            />
          </div>
          <button type="submit" className="btn btn-primary mt-3">
            Edit Product
          </button>
        </form>
      )}

      {showAllProducts && (
        <div className="container">
          {showAllProducts && (
            <div>
              <h1 className="d-flex align-items-center">
                All Products
                <a className="downloadLink" id="downloadLink" href="#">
                    <i className="bi bi-download"></i>
                </a>
                  <i className="bi bi-download"></i>
                </a>
              </h1>
              <table className="table table-striped">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Vendor</th>
                    <th>SKU</th>
                    <th>Price</th>
                  </tr>
                </thead>
                <tbody>
                  {productList.map((product) => (
                    <tr key={product.id}>
                      <td>{product.id}</td>
                      <td>{product.name}</td>
                      <td>{product.description}</td>
                      <td>{product.vendor}</td>
                      <td>{product.sku}</td>
                      <td>${product.price.toFixed(2)}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>
      )}

      {showAddAdminForm && (
        <form className="admin-form" onSubmit={handleAddAdminSubmit}>
          <div className="input-group mt-3">
            <label htmlFor="adminUsername" className="input-group-text">
              Username:
            </label>
            <input
              id="adminUsername"
              type="text"
              className="form-control"
              name="adminUsername"
              value={adminData.adminUsername}
              onChange={handleAddAdminChange}
              required
            />
          </div>
          <div className="input-group mt-3">
            <label htmlFor="adminEmail" className="input-group-text">
              Admin Email:
            </label>
            <input
              id="adminEmail"
              type="email"
              className="form-control"
              name="adminEmail"
              value={adminData.adminEmail}
              onChange={handleAddAdminChange}
              required
            />
          </div>
          <div className="input-group mt-3">
            <label htmlFor="adminPassword" className="input-group-text">
              Password:
            </label>
            <input
              id="adminPassword"
              type="password"
              className="form-control"
              name="adminPassword"
              value={adminData.adminPassword}
              onChange={handleAddAdminChange}
              required
            />
          </div>
          <div className="input-group mt-3">
            <label htmlFor="adminConfirmPassword" className="input-group-text">
              Confirm Password:
            </label>
            <input
              id="adminConfirmPassword"
              type="password"
              className="form-control"
              name="adminConfirmPassword"
              value={adminData.adminConfirmPassword}
              onChange={handleAddAdminChange}
              required
            />
          </div>
          <button type="submit" className="btn btn-primary mt-3">
            Add Admin
          </button>
        </form>
      )}
    </div>
  );
};

export default Admin;
