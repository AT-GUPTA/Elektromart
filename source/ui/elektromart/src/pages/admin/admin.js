import React, { useState } from 'react';
import './admin.css';

function Admin() {
  const [showAddProductForm, setShowAddProductForm] = useState(false);
  const [showEditProductForm, setShowEditProductForm] = useState(false);
  const [showAddAdminForm, setShowAddAdminForm] = useState(false);

  const [product, setProduct] = useState({
    name: '',
    imageUrl: '',
    price: '',
    discount: '',
    featured: false,
    id: '',
  });

  const [adminEmail, setAdminEmail] = useState('');

  const handleAddProductClick = () => {
    setShowAddProductForm(true);
    setShowEditProductForm(false);
    setShowAddAdminForm(false);
  };

  const handleEditProductClick = () => {
    setShowEditProductForm(true);
    setShowAddProductForm(false);
    setShowAddAdminForm(false);
  };

  const handleAddAdminClick = () => {
    setShowAddAdminForm(true);
    setShowAddProductForm(false);
    setShowEditProductForm(false);
  };

  const handleAdminEmailChange = (event) => {
    setAdminEmail(event.target.value);
  };

  const handleAddProductSubmit = (event) => {
    event.preventDefault();
    // Generate a unique ID for the product (you can use a library like uuid)
    const generatedId = Math.random().toString(36).substring(7);
    setProduct((prevProduct) => ({
      ...prevProduct,
      id: generatedId,
    }));
    // Here, you can handle the submission of the product data
    console.log('Product data submitted:', product);
    // Clear the form and hide it
    setProduct({
      name: '',
      imageUrl: '',
      price: '',
      discount: '',
      featured: false,
      id: '',
    });
    setShowAddProductForm(false);
  };

  const handleEditProductSubmit = (event) => {
    event.preventDefault();
    // Here, you can handle the submission of the edited product data
    console.log('Edited product data submitted:', product);
    // Clear the form and hide it
    setProduct({
      name: '',
      imageUrl: '',
      price: '',
      discount: '',
      featured: false,
      id: '',
    });
    setShowEditProductForm(false);
  };

  const handleAddAdminSubmit = (event) => {
    event.preventDefault();
    // Here, you can handle the submission of the new admin's email
    console.log('New admin email submitted:', adminEmail);
    // Clear the form and hide it
    setAdminEmail('');
    setShowAddAdminForm(false);
  };

  return (
    <div className="admin-container">
      <h1>Admin Interface</h1>
      <button onClick={handleAddProductClick}>Add New Product</button>
      <button onClick={handleEditProductClick}>Edit a Product</button>
      <button onClick={handleAddAdminClick}>Add New Admin</button>

      {/* Add New Product Form */}
      {showAddProductForm && (
        <form className="add-product-form" onSubmit={handleAddProductSubmit}>
          {/* ... (same form as before) */}
          <button type="submit">Add Product</button>
        </form>
      )}

      {/* Edit Product Form */}
      {showEditProductForm && (
        <form className="edit-product-form" onSubmit={handleEditProductSubmit}>
          {/* ... (same form as before) */}
          <button type="submit">Edit Product</button>
        </form>
      )}

      {/* Add New Admin Form */}
      {showAddAdminForm && (
        <form className="add-admin-form" onSubmit={handleAddAdminSubmit}>
          <label>
            Admin Email:
            <input
              type="email"
              name="adminEmail"
              value={adminEmail}
              onChange={handleAdminEmailChange}
              required
            />
          </label>
          <button type="submit">Add Admin</button>
        </form>
      )}
    </div>
  );
}

export default Admin;
