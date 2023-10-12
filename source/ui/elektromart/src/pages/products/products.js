import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Link } from 'react-router-dom'; // Import Link from React Router

const products = require('./products.json');

function Products() {
  return (
    <div className="container mt-5">
      <h1>Product List</h1>
      <div className="row">
        {products.map((product) => (
          <div className="col-md-4 mb-4" key={product.id}>
            <div className="card">
              <div className="card-body">
                <h5 className="card-title">{product.name}</h5>
                <p className="card-text">{product.description}</p>
                <p className="card-text">Vendor: {product.vendor}</p>
                <div className="d-flex justify-content-between align-items-center">
                  <p>Price: ${product.price.toFixed(2)}</p>
                  <Link to={`/products/${product.id}`} className="btn btn-primary">
                    Buy
                  </Link>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Products;
