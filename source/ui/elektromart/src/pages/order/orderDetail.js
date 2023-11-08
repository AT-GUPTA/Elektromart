import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import Swal from "sweetalert2";
import 'bootstrap/dist/css/bootstrap.min.css';

function OrderDetail({ roleId }) {
    const [cart, setCart] = useState({});
    const location = useLocation();

    const [order, setOrder] = useState(location.state?.order || null);

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
        backgroundColor: '#2f4550',
        color: 'white',
        borderBottom: 'none'
    };

    const cardStyle = {
        boxShadow: '0 4px 8px 0 rgba(0,0,0,0.2)',
        marginBottom: '20px'
    };

    const badgeStyle = (status) => ({
        backgroundColor: status === 'pending' ? '#17a2b8' : '#28a745',
        padding: '2px 6px',
        borderRadius: '10px'
    });

    const listItemStyle = {
        borderBottom: '1px solid #f8f9fa'
    };

    const priceBadgeStyle = {
        backgroundColor: '#2f4550',
        padding: '5px 10px',
        borderRadius: '10px'
    };
    const markButtonStyle = {
        width: '20%',             
        margin: '10px auto',      
        backgroundColor: '#2f4550',
        color: '#f4f4f9',
        padding: '15px 30px',
        border: 'none',
        cursor: 'pointer',
        borderRadius: '10px',
        boxShadow: '2px 2px 5px rgba(0, 0, 0, 0.3)',
        transition: 'background-color 0.3s, transform 0.2s',
        position: 'relative',
        zIndex: 1,
        display: 'block',        
    };
    

    const updateShippingStatus = (newStatus) => {
        const token = localStorage.getItem("secret");
        fetch(`http://localhost:8080/api/orders/update-shipping-status?orderId=${order.orderId}&newStatus=${newStatus}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            credentials: 'include',
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response;
            })
            .then((data) => {
                Swal.fire({
                    icon: "success",
                    title: "Updated!",
                    text: "Shipping status updated successfully",
                });
                
                setOrder(prevOrder => ({
                    ...prevOrder,
                    shippingStatus: newStatus
                }));
            })
            .catch((error) => {
                Swal.fire({
                    icon: "error",
                    title: "Error!",
                    text: "Could not update shipping status " + error
                });
            });
    }
    

    return (order &&
        <div className="container mt-5">
            <div className="card border-primary" style={cardStyle}>
                <div className="card-header" style={cardHeaderStyle}>Order Details</div>
                <div className="card-body text-primary">
                    <h5 className="card-title">Order ID: {order.orderId}</h5>
                    <p className="card-text">Date: {order.createdDate}</p>
                    <p className="card-text">Total Items: {cart.cartContent?.length || 0}</p>
                    <p className="card-text">Total Price: ${totalPrice().toFixed(2)}</p>
                    <p className="card-text">Status: <span style={badgeStyle(order.shippingStatus)}>{order.shippingStatus}</span></p>
                    
                    {
                        roleId == 2 && order.shippingStatus === "PENDING" && (
                            <button style={markButtonStyle} onClick={() => updateShippingStatus("SHIPPED")}>
                                Mark as Shipped
                            </button>
                        )
                    }
                    {
                        roleId == 2 && order.shippingStatus === "SHIPPED" && (
                            <button style={markButtonStyle} onClick={() => updateShippingStatus("DELIVERED")}>
                                Mark as Delivered
                            </button>
                        )
                    }
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
