import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { useParams, useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import '../../styles/checkout.css'

const Checkout = () => {
  const [address, setAddress] = useState('');
  const [shippingCost, setShippingCost] = useState(null);
  const [deliveryDate, setDeliveryDate] = useState(null);
  const [validatingAddress, setValidatingAddress] = useState(false);
  const location = useLocation();
  const navigate = useNavigate();
  const cartItems = location.state?.cartItems;
  const cartId = location.state?.cartId;

  const subtotal = cartItems.reduce((acc, item) => acc + item.price * item.quantity, 0);
  const taxRate = 0.10;
  const taxAmount = subtotal * taxRate;
  const total = subtotal + taxAmount;

  const handleAddressChange = (e) => {
    setAddress(e.target.value);
    setShippingCost(null); // Reset shipping cost while editing
  };

  const validateAddress = async (address) => {
    setValidatingAddress(true);
    setTimeout(() => {
      setValidatingAddress(false);
      setShippingCost(calculateShipping(address));
      setDeliveryDate(calculateDeliveryDate());
    }, 1500);
  };

  const calculateShipping = (address) => {
    return 5.99;
  };

  const calculateDeliveryDate = () => {
    const today = new Date();
    const delivery = new Date(today.setDate(today.getDate() + Math.floor(Math.random() * (14 - 5)) + 5));
    return delivery.toDateString();
  };

  const placeOrder = () => {
    const userId = localStorage.getItem("id");
    const token = localStorage.getItem("secret");

    fetch('http://localhost:8080/api/orders/create-order', {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({cartId, deliveryAddress: address, userId}),
    })
    .then((response) => response.json())
    .then((data) => {
       const newCartId = data.newCardId;
       localStorage.setItem("cart_id", newCartId);
       navigate("/order-placed", { state:  { isSuccess: true, deliveryDate } } ) } )
    .catch((error) => {
      console.log(error);
        navigate("/order-placed", { state:  { isSuccess: false, deliveryDate } } ) } )
  };

  return (
    cartItems && 
    <div className="container mt-5">
      <h2 className="mb-4">Review Your Cart</h2>
      <ul className="list-group mb-3">
        {cartItems.map((item) => (
          <li key={item.id} className="list-group-item d-flex justify-content-between lh-sm">
            <div>
              <h6 className="my-0">{item.name}</h6>
              <small className="text-muted">Quantity: {item.quantity}</small>
            </div>
            <span className="text-muted">${item.price.toFixed(2)}</span>
          </li>
        ))}
        <li className="list-group-item d-flex justify-content-between">
          <span>Subtotal</span>
          <strong>${subtotal.toFixed(2)}</strong>
        </li>
        <li className="list-group-item d-flex justify-content-between">
          <span>Tax (10%)</span>
          <strong>${taxAmount.toFixed(2)}</strong>
        </li>
        <li className="list-group-item d-flex justify-content-between bg-light">
          <span className="text-success">Total (incl. tax)</span>
          <strong className="text-success">${total.toFixed(2)}</strong>
        </li>
      </ul>
      <div className="mb-4">
        <h4>Delivery Address</h4>
        <input
          type="text"
          className="form-control"
          placeholder="Enter your address"
          value={address}
          onChange={handleAddressChange}
          onBlur={() => validateAddress(address)}
          disabled={validatingAddress}
        />
         {validatingAddress && (
          <div className="validating-address">
            <div className="spinner-border spinner-border-sm" role="status"></div>
            &nbsp; Validating address...
          </div>
        )}
      </div>
      {shippingCost && deliveryDate && (
        <div className="card border-success mb-3">
          <div className="card-header bg-success text-white">Estimated Delivery and Shipping</div>
          <div className="card-body text-success">
            <h5 className="card-title">Standard Shipping: ${shippingCost.toFixed(2)}</h5>
            <p className="card-text">Estimated Delivery Date: <b>{deliveryDate}</b></p>
          </div>
        </div>
      )}

        <button 
            className="btn btn-success btn-lg btn-block" 
            onClick={placeOrder}
            disabled={!address || !shippingCost || validatingAddress}
        >
        Place Order
      </button>
    </div> 
  );
};


export default Checkout;
