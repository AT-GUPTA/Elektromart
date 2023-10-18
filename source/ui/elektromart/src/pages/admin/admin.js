import React, { useState } from "react";
import "./admin.css";
import products from "../products/products.json";

const Admin = () => {
  const [showAddProductForm, setShowAddProductForm] = useState(false);
  const [showEditProductForm, setShowEditProductForm] = useState(false);
  const [showAddAdminForm, setShowAddAdminForm] = useState(false);

  const [productData, setProductData] = useState({
    productName: "",
    productImageUrl: "",
    productPrice: "",
    discount: "",
    isFeatured: false,
    id: Math.floor(Math.random() * 1000),
  });

  const [editProductId, setEditProductId] = useState("");
  const [productList, setProductList] = useState(products); // Use the imported products

  const [adminData, setAdminData] = useState({
    adminEmail: "",
    adminName: "",
    adminToken: "",
  });

  const handleAddProductClick = () => {
    setShowAddProductForm(true);
    setShowEditProductForm(false);
    setShowAddAdminForm(false);
  };

  const handleEditProductClick = () => {
    setShowAddProductForm(false);
    setShowEditProductForm(true);
    setShowAddAdminForm(false);
  };

  const handleAddAdminClick = () => {
    setShowAddProductForm(false);
    setShowEditProductForm(false);
    setShowAddAdminForm(true);
  };

  const handleFormChange = (e) => {
    const { name, value } = e.target;
    setProductData({
      ...productData,
      [name]: name === "isFeatured" ? e.target.checked : value,
    });
  };

  const handleEditProductIdChange = (e) => {
    const selectedProductId = e.target.value;
    setEditProductId(selectedProductId);
  
    // Find the selected product in the products array
    const selectedProduct = products.find((product) => product.id === selectedProductId);
  
    if (selectedProduct) {
      // Autofill the product data fields with the selected product's details
      setProductData({
        productName: selectedProduct.name,
        productImageUrl: selectedProduct.image,
        productPrice: selectedProduct.price.toFixed(2),
        discount: "",
        isFeatured: false,
        id: selectedProduct.id,
      });
    } else {
      // If the selected product is not found, clear the product data fields
      setProductData({
        productName: "",
        productImageUrl: "",
        productPrice: "",
        discount: "",
        isFeatured: false,
        id: "",
      });
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
    console.log("Product data submitted:", productData);
    setProductData({
      productName: "",
      productImageUrl: "",
      productPrice: "",
      discount: "",
      isFeatured: false,
      id: Math.floor(Math.random() * 1000),
    });
  };

  const handleEditProductSubmit = (e) => {
    e.preventDefault();
    console.log("Editing product with ID:", editProductId);
    setEditProductId("");
    setProductData({
      productName: "",
      productImageUrl: "",
      productPrice: "",
      discount: "",
      isFeatured: false,
      id: Math.floor(Math.random() * 1000),
    });
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
      <button onClick={handleAddProductClick}>Add New Product</button>
      <button onClick={handleEditProductClick}>Edit a Product</button>
      <button onClick={handleAddAdminClick}>Add a New Admin</button>

      {showAddProductForm && (
        <form className="product-form" onSubmit={handleAddProductSubmit}>
          {/* Product fields similar to Sign Up form */}
          <div className="input-group mt-3">
            <label htmlFor="productName" className="input-group-text">
              Product Name:
            </label>
            <input
              id="productName"
              type="text"
              className="form-control"
              name="productName"
              value={productData.productName}
              onChange={handleFormChange}
              required
            />
          </div>
          <div className="input-group mt-3">
            <label htmlFor="productImageUrl" className="input-group-text">
              Product Image URL:
            </label>
            <input
              id="productImageUrl"
              type="text"
              className="form-control"
              name="productImageUrl"
              value={productData.productImageUrl}
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
              name="productPrice"
              value={productData.productPrice}
              onChange={handleFormChange}
              required
            />
          </div>
          <div className="input-group mt-3">
            <label htmlFor="discount" className="input-group-text">
              Discount:
            </label>
            <input
              id="discount"
              type="number"
              className="form-control"
              name="discount"
              value={productData.discount}
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
              value={editProductId}
              onChange={handleEditProductIdChange}
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
              name="productName"
              value={productData.productName}
              onChange={handleFormChange}
              required
            />
          </div>
          <div className="input-group mt-3">
            <label htmlFor="productImageUrl" className="input-group-text">
              Product Image URL:
            </label>
            <input
              id="productImageUrl"
              type="text"
              className="form-control"
              name="productImageUrl"
              value={productData.productImageUrl}
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
              name="productPrice"
              value={productData.productPrice}
              onChange={handleFormChange}
              required
            />
          </div>
          <div className="input-group mt-3">
            <label htmlFor="discount" className="input-group-text">
              Discount:
            </label>
            <input
              id="discount"
              type="number"
              className="form-control"
              name="discount"
              value={productData.discount}
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