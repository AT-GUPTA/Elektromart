import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { useParams, useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

const Checkout = () => {
  const [address, setAddress] = useState('');
  const [shippingCost, setShippingCost] = useState(null);
  const [deliveryDate, setDeliveryDate] = useState(null);
  const [validatingAddress, setValidatingAddress] = useState(false);
  const location = useLocation();
  const navigate = useNavigate();
  const cartItems = location.state?.cartItems;
  const cartId = location.state?.cartId;

  const handleAddressChange = (e) => {
    setAddress(e.target.value);
    setShippingCost(null); // Reset shipping cost while editing
  };

  const validateAddress = async (address) => {
    setValidatingAddress(true);
    // Simulate address validation delay
    setTimeout(() => {
      // Here you would typically make an API call to Google's API for address validation
      // For this mockup, we'll assume the address is always valid and calculate shipping
      setValidatingAddress(false);
      // Simulate shipping cost calculation based on address
      setShippingCost(calculateShipping(address));
      setDeliveryDate(calculateDeliveryDate());
    }, 1500);
  };

  const calculateShipping = (address) => {
    // Implement actual shipping cost calculation logic here
    // For this mockup, we'll return a fixed cost
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

    const orderInfo = {
        deliveryDate,
    }
    fetch('http://localhost:8080/api/orders/create-order', {
        method: 'POST',
        headers: {
            'X-Session-ID': userId,
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: `cartId=${cartId}&deliveryAddress=${address}`,
        credentials: 'include',
    })
    .then((response) => response.json())
    .then((data) => {
        navigate("/order-placed", { state:  { isSuccess: true, deliveryDate } } ) } )
    .catch((error) => {
        navigate("/order-placed", { state:  { isSuccess: false, deliveryDate } } ) } )
    // Implement order placement logic here
    console.log('Order placed:', { cartItems, address, shippingCost, deliveryDate });
    // Redirect to success page, show confirmation, etc.
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
          <div className="d-flex align-items-center">
             
            <div className="spinner-border spinner-border-sm mr-2" role="status">
            </div>
            &nbsp; Validating address...
          </div>
        )}
      </div>
      {shippingCost && deliveryDate && (
        <div className="card border-success mb-3">
          <div className="card-header bg-success text-white">Estimated Delivery and Shipping</div>
          <div className="card-body text-success">
            <h5 className="card-title">Shipping: ${shippingCost.toFixed(2)}</h5>
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
