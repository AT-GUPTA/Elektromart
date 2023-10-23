import React, {useEffect, useState} from 'react';
import cartContent from './cartContent.json';
import {Link} from "react-router-dom";
import './cart.css';


const Cart = () => {
    const [cartItems, setCartItems] = useState([]);

    useEffect(() => {
        localStorage.setItem('cartContent', JSON.stringify(cartContent));
        // Load cart items from local storage
        const loadedCartItems = JSON.parse(localStorage.getItem('cartContent')) || [];
        setCartItems(loadedCartItems);
        /*
        const cartId = localStorage.getItem("cartId");
        fetch(`http://localhost:8080/cart/getCart?cartId=${cartId}`)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            loadedCartItems = data;
            setCartItems(loadedCartItems);
        })
        .catch(error => console.error("Error fetching cart:", error));
         */
    }, []);

    const handleIncreaseQty = (id) => {
        const updatedCartItems = cartItems.map(item =>
            item.id === id ? {...item, quantity: item.quantity + 1} : item
        );
        setCartItems(updatedCartItems);
        localStorage.setItem('cartContent', JSON.stringify(updatedCartItems));
    };

    const handleDecreaseQty = (id) => {
        const updatedCartItems = cartItems.map(item =>
            item.id === id && item.quantity > 1 ? {...item, quantity: item.quantity - 1} : item
        );
        setCartItems(updatedCartItems);
        localStorage.setItem('cartContent', JSON.stringify(updatedCartItems));
    };

    const handleRemoveItem = (id) => {
        const updatedCartItems = cartItems.filter(item => item.id !== id);
        setCartItems(updatedCartItems);
        localStorage.setItem('cartContent', JSON.stringify(updatedCartItems));
    };

    // Calculate subtotal
    const subtotal = cartItems.reduce((acc, item) => acc + item.price * item.quantity, 0);

    // Define a fixed tax rate (you can replace this with your actual tax calculation)
    const taxRate = 0.1; // 10% tax rate

    // Calculate tax amount
    const taxAmount = subtotal * taxRate;

    // Calculate total
    const total = subtotal + taxAmount;

    return (
        <div className="card">
            <div className="card-header">
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
                            <img className="img-fluid" src={item.imageUrl} alt={item.name}/>
                        </div>
                        <div className="col">
                            <div className="row text-muted">{item.name}</div>
                            <div className="row"><Link to={`/products/${item.id}`}>{item.name}</Link></div>
                        </div>
                        <div className="col text-center">
                            $ {item.price}
                        </div>
                        <div className="col">
                            <div className="d-flex justify-content-center">
                                <div className="input-group input-group-sm"
                                     style={{maxWidth: "80px"}}>
                                    <button className="btn btn-sm" type="button"
                                            onClick={() => handleDecreaseQty(item.id)}>-
                                    </button>
                                    <input type="text" className="form-control form-control-sm text-center"
                                           value={item.quantity} readOnly/>
                                    <button className="btn btn-sm" type="button"
                                            onClick={() => handleIncreaseQty(item.id)}>+
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div className="col text-center">$ {(item.price * item.quantity).toFixed(2)}
                        </div>
                        <div className="col remove"> {/* text-right class for aligning icon to the right */}
                            <i className="bi bi-trash" onClick={() => handleRemoveItem(item.id)}></i>
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
                    <div className="col"></div>
                    <div className="col">
                        <Link to="/checkout" className="btn btn-primary">
                            Continue to Checkout
                        </Link>
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
