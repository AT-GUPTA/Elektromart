import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Swal from "sweetalert2";
import 'bootstrap/dist/css/bootstrap.min.css';

function ClaimOrder() {
    const [orderId, setOrderId] = useState('');
    const navigate = useNavigate();
    const userId = localStorage.getItem("id"); // Assuming "id" is the key used for storing user ID

    useEffect(() => {
        // Check if user is logged in (i.e., userId exists)
        if (!userId) {
            // Redirect to login if user ID is not found
            Swal.fire({
                icon: "warning",
                title: "Not Logged In",
                text: "You need to log in to claim an order.",
                confirmButtonText: "Go to Login"
            }).then(() => {
                navigate("/login");
            });
        }
    }, [userId, navigate]);

    const claimOrder = () => {
        const token = localStorage.getItem("secret");
        fetch(`http://localhost:8080/api/orders/update-order-user-if-absent?orderId=${orderId}&userId=${userId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            credentials: 'include',
        })
            .then((response) => {
                if (response.ok) {
                    return response.text().then(() => {
                        Swal.fire({
                            icon: "success",
                            title: "Success!",
                            text: "Order claimed successfully",
                        });
                    });
                } else if (response.status === 400) {
                    // Handle BadRequest, which means the order is already claimed
                    return response.text().then((text) => {
                        Swal.fire({
                            icon: "info",
                            title: "Order Already Claimed",
                            text: text,
                        });
                    });
                } else {
                    // Handle other types of network errors
                    throw new Error('Network response was not ok');
                }
            })
            .catch((error) => {
                Swal.fire({
                    icon: "error",
                    title: "Error!",
                    text: `Could not claim the order: ${error.message}`,
                });
            });
    };


    return (
        <div className="container mt-5">
            <div className="card border-primary mb-3">
                <div className="card-header">Claim an Order</div>
                <div className="card-body">
                    <div className="mb-3">
                        <label htmlFor="orderId" className="form-label">Order ID</label>
                        <input
                            type="text"
                            className="form-control"
                            id="orderId"
                            value={orderId}
                            onChange={(e) => setOrderId(e.target.value)}
                        />
                    </div>
                    <button
                        className="btn btn-primary"
                        onClick={claimOrder}
                        disabled={!orderId}
                    >
                        Claim Order
                    </button>
                </div>
            </div>
        </div>
    );
}

export default ClaimOrder;
