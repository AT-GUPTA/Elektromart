import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import Swal from "sweetalert2";
import 'bootstrap/dist/css/bootstrap.min.css';

function OrderDetail() {
    const [cart, setCart] = useState({});
    const location = useLocation();
    const order = location.state?.order;

    const totalPrice = () => {
        if (!cart.cartContent) {
            return 0;
        }
        return cart.cartContent?.reduce((a, i) => a + i.price * i.quantity, 0);
    }

    useEffect(() => {
        if (order) {
            const token = localStorage.getItem("secret");
            fetch(`http://localhost:8080/api/cart/get-cart?cartId=${order.cartId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                credentials: 'include',
            })
            .then((response) => response.json())
            .then((data) => {
                setCart(data);
            })
            .catch((error) => {
                Swal.fire({
                    icon: "error",
                    title: "Error!",
                    text: "Could not fetch order details",
                });
            });
        }
      
    }, [order?.cartId]);

    // Styles
    const cardHeaderStyle = {
        backgroundColor: '#007bff',
        color: 'white',
        borderBottom: 'none'
    };

    const cardStyle = {
        boxShadow: '0 4px 8px 0 rgba(0,0,0,0.2)',
        marginBottom: '20px'
    };

    const badgeStyle = (status) => ({
        padding: '5px 10px',
        borderRadius: '10px'
    });

    const listItemStyle = {
        borderBottom: '1px solid #f8f9fa'
    };

    const priceBadgeStyle = {
        backgroundColor: '#007bff',
        padding: '5px 10px',
        borderRadius: '10px'
    };

    return (order &&
        <div className="container mt-5">
            <div className="card border-primary" style={cardStyle}>
                <div className="card-header" style={cardHeaderStyle}>Order Details</div>
                <div className="card-body text-primary">
                    <h5 className="card-title">Order ID: {order.orderId}</h5>
                    <p className="card-text">Date: {order.createdDate}</p>
                    <p className="card-text">Status: <span style={badgeStyle(order.shippingStatus)}>{order.shippingStatus}</span></p>
                    <p className="card-text">Total Items: {cart.cartContent?.length || 0}</p>
                    <p className="card-text">Total Price: ${totalPrice().toFixed(2)}</p>
                </div>
            </div>

            <div className="card" style={cardStyle}>
                <div className="card-header" style={cardHeaderStyle}>Shipping Information</div>
                <ul className="list-group list-group-flush">
                    <li className="list-group-item" style={listItemStyle}>Tracking number: {order.shippingId}</li>
                    <li className="list-group-item" style={listItemStyle}>Delivery Address: {order.shippingAddress}</li>
                </ul>
            </div>

            <div className="card" style={cardStyle}>
                <div className="card-header" style={cardHeaderStyle}>Payment Option</div>
                <div className="card-body">
                    <p className="card-text">Method: {order.paymentMethod}</p>
                </div>
            </div>

            <div className="card" style={cardStyle}>
                <div className="card-header" style={cardHeaderStyle}>Order Content</div>
                <ul className="list-group list-group-flush">
                    {cart.cartContent?.map(item => (
                        <li key={item.id} className="list-group-item d-flex justify-content-between align-items-center" style={listItemStyle}>
                            <div>
                                <h6 className="my-0">{item.name}</h6>
                                <small className="text-muted">Quantity: {item.quantity}</small>
                            </div>
                            <span style={priceBadgeStyle} className="badge rounded-pill">${(item.price * item.quantity).toFixed(2)}</span>
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
}

export default OrderDetail;
