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
        return cart.cartContent?.reduce((a, i) => a + i.price, 0)
    }

    useEffect(() => {
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
    }, [order.cartId]);

    return (
        <div className="container mt-5">
            <div className="card border-primary mb-3">
                <div className="card-header bg-primary text-white">Order Details</div>
                <div className="card-body text-primary">
                    <h5 className="card-title">Order ID: {order.orderId}</h5>
                    <p className="card-text">Date: {order.createdDate}</p>
                    <p className="card-text">Status: <span className={`badge ${order.shippingStatus === 'pending' ? 'bg-info' : 'bg-success'}`}>{order.shippingStatus}</span></p>
                    <p className="card-text">Total Items: {cart.cartContent?.length || 0}</p>
                    <p className="card-text">Total Price: ${totalPrice().toFixed(2)}</p>
                </div>
            </div>

            <div className="card mb-3">
                <div className="card-header">Shipping Information</div>
                <ul className="list-group list-group-flush">
                    <li className="list-group-item">Tracking number: {order.shippingId}</li>
                    <li className="list-group-item">Delivery Address: {order.shippingAddress}</li>
                </ul>
            </div>

            <div className="card mb-3">
                <div className="card-header">Payment Option</div>
                <div className="card-body">
                    <p className="card-text">Method: {order.paymentMethod}</p>
                </div>
            </div>

            <div className="card mb-3">
                <div className="card-header">Order Content</div>
                <ul className="list-group list-group-flush">
                    {cart.cartContent?.map(item => (
                        <li key={item.id} className="list-group-item d-flex justify-content-between align-items-center">
                            <div>
                                <h6 className="my-0">{item.name}</h6>
                                <small className="text-muted">Quantity: {item.quantity}</small>
                            </div>
                            <span className="badge bg-primary rounded-pill">${item.price.toFixed(2)}</span>
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
}

export default OrderDetail;
