import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { useNavigate } from 'react-router-dom';
import Swal from "sweetalert2";
import '../../styles/orders.css'


function Orders({isAdmin}) {
    const [orders, setOrders] = useState([]);
    const [hasError, setHasError] = useState(false);

    const navigate = useNavigate();


    useEffect(() => {
        const userId = localStorage.getItem("id");
        const token = localStorage.getItem("secret");
        fetch(`http://localhost:8080/api/orders/order-history?userId=${userId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            credentials: 'include',
        })
        .then((response) => response.json())
        .then((data) => {
            setOrders(data);
        })
        .catch((error) => {
            Swal.fire({
                icon: "error",
                title: "error!",
                text: "Could not fetch order history",
            });
        });
    }, []);

    return (
        <div className="container mt-5">
            <h2 className="mb-4">Your Orders</h2>
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
                        {orders.map(order => (
                            <tr key={order.orderId}>
                            <td>{order.orderId}</td>
                            <td>{order.createdDate}</td>
                            <td>
                                <span className={`status-label ${order.shippingStatus.toLowerCase() === 'delivered' ? 'status-delivered' : 'status-other'}`}>
                                {order.shippingStatus.charAt(0).toUpperCase() + order.shippingStatus.slice(1)}
                                </span>
                            </td>
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
    );
}

export default Orders;
