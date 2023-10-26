import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Link } from 'react-router-dom';
import "../../styles/products.css";

function Products() {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    // Fetch products from the API endpoint
    fetch('http://localhost:8080/Elektromart/products')
      .then((response) => response.json())
      .then((data) => {
        setProducts(data);
      })
      .catch((error) => {
        console.error('Error fetching products: ', error);
      });
  }, []);

  return (
    <div className="container mt-5">
      <h1>Product List</h1>
      <div className="row">
        {products.map((product) => (
          <div className="col-md-4 mb-4" key={product.urlSlug}>
            <div className="card">
              <img
              src ={"../../images/iphone.jpg"}
                // src={product.image} // Add the product image URL here
                // alt={product.name}
                className="card-img-top"
              />
              <div className="card-body">
                <h5 className="card-title">{product.name}</h5>
                <p className="card-text">{product.description}</p>
                <p className="card-text">Vendor: {product.vendor}</p>
                <div className="d-flex justify-content-between align-items-center">
                  <p>Price: ${product.price.toFixed(2)}</p>
                  <Link to={`/product/${product.urlSlug}`} className="btn btn-primary productsButton">
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
