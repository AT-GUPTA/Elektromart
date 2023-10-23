import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { useParams } from 'react-router-dom';
import Swal from 'sweetalert2'

const products = require('./products.json');

function ProductDetail() {
  const { id } = useParams();

  const product = products.find((item) => item.id.toString() === id);

  if (!product) {
    return <div>Product not found</div>;
  }

  const handleAddToCart = (_, productName) => {
      Swal.fire({
        title: 'Wow!',
        text: `You added ${productName} to cart!`,
        icon: 'success',
        timer: 1500
    })
  };

  return (
    <div className="container mt-5">
      <h1>Product Detail</h1>
      <div className="row">
        <div className="col-md-4">
          <img
            src={product.image}
            className="img-fluid"
          />
        </div>
        <div className="col-md-8">
          <div className="card">
            <div className="card-body">
              <h5 className="card-title">{product.name}</h5>
              <p className="card-text">{product.description}</p>
              <p className="card-text"><b>Vendor:</b> {product.vendor}</p>
              <p className="card-text"><b>Price:</b> {product.price.toFixed(2)}$</p>
              <button onClick={() => handleAddToCart("", product.name)} className="btn btn-primary">
                Add to Cart
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ProductDetail;
