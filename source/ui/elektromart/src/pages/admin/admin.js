import React, {useEffect, useState} from "react";
import "./admin.css";
import "bootstrap/dist/css/bootstrap.min.css";
import { useNavigate } from 'react-router-dom';
import Swal from "sweetalert2";
import Form from "../signup/Form";


const Admin = () => {
    const [showAddProductForm, setShowAddProductForm] = useState(false);
    const [showEditProductForm, setShowEditProductForm] = useState(false);
    const [showAddAdminForm, setShowAddAdminForm] = useState(false);
    const [showAllProducts, setShowAllProducts] = useState(true);
    const [showAllOrders, setShowAllOrders] = useState(false);
    const [errorMessage, setErrorMessage] = useState(null);
    const [usernames, setUsernames] = useState([]);
    const [selectedUsername, setSelectedUsername] = useState('');

    const navigate = useNavigate();


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
    const [orders, setOrders] = useState([]);

    useEffect(() => {
        if (errorMessage) {
            Swal.fire({
                icon: "error",
                title: "Error!",
                text: errorMessage,
            });
        }
    }, [errorMessage]);

    useEffect(() => {
        const token = localStorage.getItem("secret");
        fetch('http://localhost:8080/api/orders/', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
        .then((response) => response.json())
        .then((data) => {
            const usernames = data.map(order => order.username);
            setUsernames([...new Set(usernames)]);
            setOrders(data);
        })
        .catch((error) => {
            setErrorMessage("Could not fetch orders");
        });
    }, []);


    useEffect(() => {
        // Fetch products from the API endpoint
        const token = localStorage.getItem("secret");
        fetch("http://localhost:8080/api/products/",{
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
            .then((response) => response.json())
            .then((data) => {
                setProducts(data);
                setProductList(data); // Update product list here
            })
            .catch((error) => console.error("Error fetching products: ", error));
    }, []);

    useEffect(() => {
        setProductList(products);
    }, [products]);

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

            const token = localStorage.getItem("secret");
            const response = await fetch('http://localhost:8080/api/products/download',{
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });

            if (!response.ok) {
               setErrorMessage('Download failed');
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

    };

    const handleAddProductClick = () => {
        setShowAddProductForm(true);
        setShowEditProductForm(false);
        setShowAddAdminForm(false);
        setShowAllProducts(false);
        setShowAllOrders(false);
    };

    const handleEditProductClick = () => {
        setShowAddProductForm(false);
        setShowEditProductForm(true);
        setShowAddAdminForm(false);
        setShowAllProducts(false);
        setShowAllOrders(false);
    };

    const handleViewAllProductClick = () => {
        setShowAddProductForm(false);
        setShowEditProductForm(false);
        setShowAddAdminForm(false);
        setShowAllProducts(true);
        setShowAllOrders(false);
    };

    const handleAddAdminClick = () => {
        setShowAddProductForm(false);
        setShowEditProductForm(false);
        setShowAddAdminForm(true);
        setShowAllProducts(false);
        setShowAllOrders(false);
    };

    const handleShowAllOrders = () => {
        setShowAddProductForm(false);
        setShowEditProductForm(false);
        setShowAddAdminForm(false);
        setShowAllProducts(false);
        setShowAllOrders(true);
    };

    const handleFormChange = (e) => {
        const {name, value} = e.target;
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
        const {name, value} = e.target;
        setAdminData({
            ...adminData,
            [name]: value,
        });
    };

    const handleAddProductSubmit = (e) => {
        e.preventDefault();
        const token = localStorage.getItem("secret");
        fetch("http://localhost:8080/api/products/", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(productData),
        })
            .then((response) => {
                if (response.ok) {
                    // Close the modal for adding a new product
                    setShowAddProductForm(false);
                    return response.json(); // Parse the response if it's OK
                } else {
                    setErrorMessage("Something went wrong when adding a new product.");
                }
            })
            .then((data) => {
                // Product was added successfully, show a success message
                Swal.fire({
                    icon: "success",
                    title: "Success!",
                    text: "Product added successfully.",
                });

                setProducts([...products, data]);
                setProductData({
                    name: "",
                    description: "",
                    vendor: "",
                    price: "",
                    discountPercent: "",
                    isFeatured: false,
                    isDelete: false,
                });
            })
            .then(()=>{handleViewAllProductClick()})
            .catch((error) => console.error("Error adding product: ", error));
    };

    const handleEditProductSubmit = async (e) => {
        e.preventDefault();
        if (!selectedProduct) {
            return;
        }

        try {
            const token = localStorage.getItem("secret");
            const response = await fetch(
                `http://localhost:8080/api/products/${selectedProduct.id}`,
                {
                    method: "PUT", // Assuming you use PUT for editing
                    headers: {
                        "Content-Type": "application/json",
                        'Authorization': `Bearer ${token}`
                    },
                    body: JSON.stringify(productData),
                }
            );
            if (response.ok) {
                const data = await response.json();

                // Product was updated successfully, show a success message
                Swal.fire({
                    icon: "success",
                    title: "Success!",
                    text: "Product updated successfully.",
                });

                setProducts((prevProducts) =>
                    prevProducts.map((product) =>
                        product.id === data.id ? data : product
                    )
                );
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
                setShowEditProductForm(false);
            } else {
                setErrorMessage("Something went wrong when editing a product.");
            }
            handleViewAllProductClick();
        } catch (error) {
            console.error("Error editing product: ", error);
            setErrorMessage("Something went wrong when editing a product.");
        }
    };

    const handleAddAdminSubmit = (e) => {
        e.preventDefault();
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
            <button className="adminButton" onClick={handleShowAllOrders}>
                View all Orders
            </button>
            <button className="adminButton" onClick={handleAddAdminClick}>
                Add a New Admin
            </button>

            {showAllOrders && ( 
                <div className="container mt-5">
                <h2 className="mb-4">Your Orders</h2>

                <div className="mb-4">
                    <label htmlFor="userIdSelect" className="form-label">Select a Username</label>
                    <select 
                        id="userIdSelect" 
                        className="form-select"
                        value={selectedUsername}
                        onChange={(e) => {
                            setSelectedUsername(e.target.value)
                        }}
                    >
                        <option value="">Select a Username...</option>
                        {usernames.map(u => (
                            <option key={u} value={u}>{u}</option>
                        ))}
                    </select>
                </div>
                <div className="card">
                    <div className="card-body">
                        <table className="table">
                            <thead>
                                <tr>
                                    <th scope="col">Order ID</th>
                                    <th scope="col">Date</th>
                                    <th scope="col">Status</th>
                                    <th scope="col">Payment method</th>
                                    <th scope="col">Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {orders.filter(e => selectedUsername === "" || e.username === selectedUsername).map(order => (
                                    <tr key={order.orderId} className={order.shippingStatus === 'delivered' ? 'table-success' : ''}>
                                        <td>{order.orderId}</td>
                                        <td>{order.createdDate}</td>
                                        <td>{order.shippingStatus.charAt(0).toUpperCase() + order.shippingStatus.slice(1)}</td>
                                        <td>{order.paymentMethod}</td>
                                        <td>
                                            <button className="btn btn-primary btn-sm" onClick={() => navigate(`/orders/${order.orderId}`, { state: { order } })}>
                                                View
                                            </button>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                </div>
                </div>
            )}

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
                <div>
                    <div className="signup-box-admin container text-bg-light rounded-5">
                        <h3 className="text-center pt-2 mt-2 fw-semibold">Sign Up as Admin</h3>
                        <hr/>
                        <Form role="2"/>
                    </div>
                </div>
            )}
        </div>
    );
};

export default Admin;
