import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { useLocation } from 'react-router-dom';

const OrderPlaced = () => {
  const location = useLocation();

  const isSuccess = location.state?.isSuccess;
  const deliveryDate = location.state?.deliveryDate;

  const today = new Date();
  const options = { year: 'numeric', month: 'long', day: 'numeric' };
  const formattedDate = today.toLocaleDateString('en-US', options);

  return (
    <div className="container mt-5">
      {isSuccess ? (
        <div className="text-center">
          <span className="text-success">
            <i className="bi bi-check-circle" style={{ fontSize: '2rem' }}></i>
          </span>
          <h2 className="text-success mt-3">Order Successfully Placed!</h2>
          <p className="mt-4">Thank you for your purchase. Here are the details of your order:</p>
          <div className="border p-4 mt-3">
            <p><strong>Created Date:</strong> {formattedDate}</p>
            <p><strong>Payment Method:</strong>Cash on delivery</p>
            <p><strong>Estimated Delivery Date:</strong> {deliveryDate}</p>
          </div>
        </div>
      ) : (
        <div className="text-center">
          <span className="text-danger">
            <i className="bi bi-x-circle" style={{ fontSize: '2rem' }}></i>
          </span>
          <h2 className="text-danger mt-3">Order Placement Failed</h2>
          <p className="mt-4">We're sorry, there was a problem placing your order. Please try again later.</p>
        </div>
      )}
    </div>
  );
};

export default OrderPlaced;
