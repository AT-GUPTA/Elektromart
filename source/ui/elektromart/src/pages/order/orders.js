import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

function Orders({isAdmin}) {
    const [orders, setOrders] = useState([]);
    const [hasError, setHasError] = useState(false);

    useEffect(() => {
        const userId = localStorage.getItem("id");
        
        fetch('http://localhost:8080/api/orders/order-history', {
            method: 'GET',
            headers: {
                'X-Session-ID': userId,
                'Content-Type': 'application/json',
            },
            credentials: 'include',
        })
        .then((response) => response.json())
        .then((data) => {
            setOrders(data);
        })
        .catch((error) => {
            setHasError(true);
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
                                <tr key={order.orderId} className={order.shippingStatus === 'delivered' ? 'table-success' : ''}>
                                    <td>{order.orderId}</td>
                                    <td>{order.createdDate}</td>
                                    <td>{order.paymentMethod}</td>
                                    <td>{order.shippingStatus}</td>
                                    <td>
                                        <a href={`/orders/${order.orderId}`} className="btn btn-primary btn-sm">View</a>
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
