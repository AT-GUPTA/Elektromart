import React, { useState } from "react";
import "./admin.css";

const Admin = () => {
  const [showAddProductForm, setShowAddProductForm] = useState(false);
  const [showEditProductForm, setShowEditProductForm] = useState(false);
  const [showAddAdminForm, setShowAddAdminForm] = useState(false);

  const [productData, setProductData] = useState({
    productName: "",
    productImageUrl: "",
    productPrice: 0,
    discount: 0,
    isFeatured: false,
    id: Math.floor(Math.random() * 1000),
  });

  const [editProductId, setEditProductId] = useState("");

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
    setEditProductId(e.target.value);
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
      productPrice: 0,
      discount: 0,
      isFeatured: false,
      id: Math.floor(Math.random() * 1000),
    });
  };

  const handleEditProductSubmit = (e) => {
    e.preventDefault();
    console.log("Editing product with ID:", editProductId);
    setEditProductId("");
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
          <label>
            Product Name:
            <input
              type="text"
              name="productName"
              value={productData.productName}
              onChange={handleFormChange}
            />
          </label>
          <label>
            Product Image URL:
            <input
              type="text"
              name="productImageUrl"
              value={productData.productImageUrl}
              onChange={handleFormChange}
            />
          </label>
          <label>
            Product Price:
            <input
              type="number"
              name="productPrice"
              value={productData.productPrice}
              onChange={handleFormChange}
            />
          </label>
          <label>
            Discount:
            <input
              type="number"
              name="discount"
              value={productData.discount}
              onChange={handleFormChange}
            />
          </label>
          <label>
            Featured:
            <input
              type="checkbox"
              name="isFeatured"
              checked={productData.isFeatured}
              onChange={handleFormChange}
            />
          </label>
          <button type="submit">Add Product</button>
        </form>
      )}

      {showEditProductForm && (
        <form className="product-form" onSubmit={handleEditProductSubmit}>
          <label>
            Product ID:
            <input
              type="text"
              name="editProductId"
              value={editProductId}
              onChange={handleEditProductIdChange}
            />
          </label>
          <label>
            Product Name:
            <input
              type="text"
              name="productName"
              value={productData.productName}
              onChange={handleFormChange}
            />
          </label>
          <label>
            Product Image URL:
            <input
              type="text"
              name="productImageUrl"
              value={productData.productImageUrl}
              onChange={handleFormChange}
            />
          </label>
          <label>
            Product Price:
            <input
              type="number"
              name="productPrice"
              value={productData.productPrice}
              onChange={handleFormChange}
            />
          </label>
          <label>
            Discount:
            <input
              type="number"
              name="discount"
              value={productData.discount}
              onChange={handleFormChange}
            />
          </label>
          <label>
            Featured:
            <input
              type="checkbox"
              name="isFeatured"
              checked={productData.isFeatured}
              onChange={handleFormChange}
            />
          </label>
          <button type="submit">Edit Product</button>
        </form>
      )}
      {showAddAdminForm && (
        <form className="admin-form" onSubmit={handleAddAdminSubmit}>
          <label>
            Admin Email:
            <input
              type="email"
              name="adminEmail"
              value={adminData.adminEmail}
              onChange={handleAddAdminChange}
            />
          </label>
          <label>
            Admin Name:
            <input
              type="text"
              name="adminName"
              value={adminData.adminName}
              onChange={handleAddAdminChange}
            />
          </label>
          <label>
            Admin Token:
            <input
              type="text"
              name="adminToken"
              value={adminData.adminToken}
              onChange={handleAddAdminChange}
            />
          </label>
          <button type="submit">Add Admin</button>
        </form>
      )}
    </div>
  );
};

export default Admin;
