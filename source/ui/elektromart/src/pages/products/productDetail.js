import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { useParams } from 'react-router-dom';

const products = require('./products.json');

function ProductDetail() {
  const { id } = useParams();

  const product = products.find((item) => item.id.toString() === id);

  if (!product) {
    return <div>Product not found</div>;
  }

  const handleAddToCart = () => {
    console.log(`product added to the cart`);
  };

  return (
    <div className="container mt-5">
      <h1>Product Detail</h1>
      <div className="card">
        <div className="card-body">
          <h5 className="card-title">{product.name}</h5>
          <p className="card-text">{product.description}</p>
          <p className="card-text">Vendor: {product.vendor}</p>
          <p className="card-text">Price: ${product.price.toFixed(2)}</p>
          <button onClick={handleAddToCart} className="btn btn-primary">
            Add to Cart
          </button>
        </div>
      </div>
    </div>
  );
}

export default ProductDetail;
