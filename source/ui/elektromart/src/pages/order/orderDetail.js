import React, { useState, useEffect } from 'react';

function OrderDetail() {
    const [orderDetails, setOrderDetails] = useState({});

    useEffect(() => {
        const orderId = 1;
        const fetchedOrderDetails = {
            id: orderId,
            date: '2023-11-05',
            status: 'Not completed',
            shippingInfo: {
                shippingId: 'SHIP12345',
                shippingStatus: 'Not shipped',
                deliveryAddress: '123 Main St, City, Country',
            },
            paymentOption: 'Cash on delivery',
            orderContent: [
                { id: 1, name: 'Product A', price: '$25', link: '/product/1' },
                { id: 2, name: 'Product B', price: '$25', link: '/product/2' },
            ],
        };
        setOrderDetails(fetchedOrderDetails);
    }, []);

    return (
        <div>
            <h2>Order Details</h2>
            <p>Order ID: {orderDetails.id}</p>
            <p>Date: {orderDetails.date}</p>
            <p>Status: {orderDetails.status}</p>
            {orderDetails.status === 'Not completed' && <button onClick={() => window.location.href='/checkout'}>Order now</button>}
            <h3>Shipping Information</h3>
            <p>Shipping ID: {orderDetails.shippingInfo?.shippingId}</p>
            <p>Shipping Status: {orderDetails.shippingInfo?.shippingStatus}</p>
            {orderDetails.status !== 'Completed' && orderDetails.status !== 'In progress'
                ? <input type="text" defaultValue={orderDetails.shippingInfo?.deliveryAddress} />
                : <p>Delivery Address: {orderDetails.shippingInfo?.deliveryAddress}</p>}
            <h3>Payment Option</h3>
            <label>
                <input type="radio" value="Cash on delivery" checked disabled /> Cash on delivery
            </label>
            <h3>Order Content</h3>
            <ul>
                {orderDetails.orderContent?.map(item => (
                    <li key={item.id}>
                        <a href={item.link}>{item.name}</a>: {item.price}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default OrderDetail;
