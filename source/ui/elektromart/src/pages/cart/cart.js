import React, {useEffect, useState} from 'react';
import {Link} from "react-router-dom";
import './cart.css';
import { useNavigate, useLocation } from 'react-router-dom';
import Swal from "sweetalert2";

const Cart = ({isAuth}) => {
    const [cartItems, setCartItems] = useState([]);
    const [loading, setLoading] = useState(true); // Added loading state

    const navigate = useNavigate();

    useEffect(() => {
        const cartId = localStorage.getItem("cart_id");
        fetch(`http://localhost:8080/api/cart/get-cart?cartId=${cartId}`)
            .then(response => 
                response.json()
            )
            .then(data => {
                console.log(data);
                setCartItems(data['cartContent']);
                setLoading(false); // Set loading to false on successful fetch
            })
            .catch(error => {
                console.error("Error fetching cart:", error);
                setLoading(false); // Set loading to false even on error
            });
    }, []);

    // If loading is true, show loading message
    if (loading) {
        return <div>Loading...</div>;
    }

    const updateQuantityAPI = (cartId, productSlug, quantity) => {
        fetch(`http://localhost:8080/api/cart/change-quantity`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `cartId=${cartId}&productSlug=${productSlug}&quantity=${quantity}`
        })
            .then(response => response.json())
            .then(data => {
                if (data.status !== 'SUCCESS') {
                    Swal.fire({
                        icon: "error",
                        title: "Error!",
                        text: "Error updating cart. Please try again.",
                    });
                }

                Swal.fire({
                    icon: "success",
                    title: "sucess!",
                    text: "Cart updated successfully.",
                    timer: 1000,
                });
            })
            .catch(error => {
                Swal.fire({
                    icon: "error",
                    title: "Error!",
                    text: "Error updating cart. Please try again.",
                });
            });
    };

    const handleIncreaseQty = (product) => {
        const cartId = localStorage.getItem("cart_id");
        updateQuantityAPI(cartId, product.urlSlug, product.quantity + 1);

        const updatedCartItems = cartItems.map(item =>
            item.id === product.id ? {...item, quantity: item.quantity + 1} : item
        );
        setCartItems(updatedCartItems);
    };

    const handleDecreaseQty = (product) => {
        const cartId = localStorage.getItem("cart_id");
        if (product.quantity > 1) {
            updateQuantityAPI(cartId, product.urlSlug, product.quantity - 1);

            const updatedCartItems = cartItems.map(item =>
                item.id === product.id && item.quantity > 1 ? {...item, quantity: item.quantity - 1} : item
            );
            setCartItems(updatedCartItems);
        }
    };

    const handleRemoveItem = (product) => {
        const cartId = localStorage.getItem("cart_id");
        fetch(`http://localhost:8080/api/cart/delete-cart-product`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `cartId=${cartId}&productSlug=${product.urlSlug}`
        })
            .then(response => response.json())
            .then(data => {
                if (data.status !== 'SUCCESS') {
                    Swal.fire({
                        icon: "error",
                        title: "Error!",
                        text: "Error updating cart. Please try again.",
                    });
                }

                Swal.fire({
                    icon: "success",
                    title: "success!",
                    text: "Cart updated successfully.",
                });
            })
            .catch(error => {
                Swal.fire({
                    icon: "error",
                    title: "Error!",
                    text: "Error updating cart. Please try again.",
                });
            });

        const updatedCartItems = cartItems.filter(item => item.id !== product.id);
        setCartItems(updatedCartItems);
    };


    // Calculate subtotal
    const subtotal = Array.isArray(cartItems)
        ? cartItems.reduce((acc, item) => acc + item.price * item.quantity, 0)
        : 0;
    const taxRate = 0.1; // 10% tax rate

    // Calculate tax amount
    const taxAmount = subtotal * taxRate;

    // Calculate total
    const total = subtotal + taxAmount;

    const handleButtonClick = () => {
        if (!isAuth) {
            Swal.fire({
                title: "Unauthorized",
                text: "Please log in to access your orders",
                icon: "warning",
                confirmButtonText: "Login"
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = '/login';
                }
            });
        } else if(cartItems.length <= 0) {
            Swal.fire({
                title: "Empty Cart",
                text: "Please add items to your cart",
                icon: "warning",
                confirmButtonText: "Take me to Products"
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = '/products';
                }
            });
        } else{
           
            const cartId = localStorage.getItem("cart_id");
            navigate("/checkout", { state: { cartItems, cartId} } );
        }
    };
    const handleClearCart = () => {
        const cartId = localStorage.getItem("cart_id");
        fetch(`http://localhost:8080/api/cart/clear-cart?cartId=${cartId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            }
        })
            .then(response => {
                if (response.ok) {
                    Swal.fire({
                        icon: "success",
                        title: "Success!",
                        text: "Cart cleared successfully.",
                    });
                    // Clear the cart items in the frontend
                    setCartItems([]);
                } else {
                    Swal.fire({
                        icon: "error",
                        title: "Error!",
                        text: "Failed to clear the cart. Please try again.",
                    });
                }
            })
            .catch(error => {
                Swal.fire({
                    icon: "error",
                    title: "Error!",
                    text: "Failed to clear the cart. Please try again.",
                });
            });
    };

    return (
        <div className="card cart-container">
            <div>
                <div className="row">
                    <div className="col">
                        <h4>
                            <b>Shopping Cart</b>
                        </h4>
                    </div>
                    <div className="col text-end">{cartItems.length} items</div>
                </div>
            </div>
            <div className="card-body">
                <div className="row mb-3">
                    <div className="col-2">
                        <b>Item</b>
                    </div>
                    <div className="col">
                        <b>Description</b>
                    </div>
                    <div className="col text-center">
                        <b>Price</b>
                    </div>
                    <div className="col text-center">
                        <b>Quantity</b>
                    </div>
                    <div className="col text-center">
                        <b>Total Price</b>
                    </div>
                    <div className="col remove"></div>
                </div>
                {cartItems.map((item, index) => (
                    <div className="row mb-3 border-bottom pb-3" key={index}>
                        <div className="col-2">
                            <img className="img-fluid" src={"../../images/iphone.jpg"} alt={item.name}/>
                        </div>
                        <div className="col">
                            <div className="row text-muted">{item.name}</div>
                            <div className="row"><Link to={`/products/${item.id}`}>{item.name}</Link></div>
                        </div>
                        <div className="col text-center">
                            $ {item.price.toFixed(2)}
                        </div>
                        <div className="col">
                            <div className="d-flex justify-content-center">
                                <div className="input-group input-group-sm"
                                     style={{maxWidth: "80px"}}>
                                    <button className="btn btn-sm" type="button"
                                            onClick={() => handleDecreaseQty(item)}>-
                                    </button>
                                    <input type="text" className="form-control form-control-sm text-center quantity-input"
                                           value={item.quantity} readOnly/>
                                    <button className="btn btn-sm" type="button"
                                            onClick={() => handleIncreaseQty(item)}>+
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div className="col text-center">$ {(item.price * item.quantity).toFixed(2)}
                        </div>
                        <div className="col remove">
                            <i className="bi bi-trash" onClick={() => handleRemoveItem(item)}></i>
                        </div>
                    </div>
                ))}
                <div className="row">
                    <div className="col"></div>
                    <div className="col"></div>
                    <div className="col"></div>
                    <div className="col text-end ub">
                        <b>Subtotal:</b>
                    </div>
                    <div className="col text-center ub">${subtotal.toFixed(2)}</div>
                </div>
                <div className="row">
                    <div className="col"></div>
                    <div className="col"></div>
                    <div className="col"></div>
                    <div className="col text-end bb">
                        <b>Tax ({(taxRate * 100).toFixed(0)}%):</b>
                    </div>
                    <div className="col text-center bb">${taxAmount.toFixed(2)}</div>
                </div>
                <div className="row mb-3">
                    <div className="col"></div>
                    <div className="col"></div>
                    <div className="col"></div>
                    <div className="col text-end bb">
                        <b>Total:</b>
                    </div>
                    <div className="col text-center bb">${total.toFixed(2)}</div>
                </div>

                <div className="row mb-3">
                    <div className="col"></div>
                    <div className="col"></div>
                    <div className="col">
                        <button onClick={handleClearCart} className="btn btn-danger">
                            Clear Cart
                        </button>
                    </div>
                    <div className="col">
                        <button onClick={handleButtonClick} className="btn btn-primary">
                           Checkout
                        </button>
                    </div>
                </div>
                <div className="back-to-shop">
                    <Link to="/products" style={{textDecoration: 'none'}}>
            <span className="text-muted">
              {' '}
                <i className="bi bi-arrow-left"></i> Back to Products
            </span>
                    </Link>
                </div>
            </div>
        </div>
    );
};

export default Cart;
