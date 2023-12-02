import React, { useEffect, useState } from 'react';
import Alert from 'react-bootstrap/Alert';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Link } from 'react-router-dom';
import "../../styles/products.css";

function Products() {
    const [products, setProducts] = useState([]);
    const [hasError, setHasError] = useState(false);

    useEffect(() => {
        // Fetch products from the API endpoint
        fetch('http://localhost:8080/api/products/')
            .then((response) => response.json())
            .then((data) => {
                setProducts(data);
            })
            .catch(() => {
                setHasError(true);
            });
    }, []);

    const variant = 'danger';

    return (
        <div className="container mt-5">
            <h1>Product List</h1>
            {hasError && 
                <Alert key={variant} variant={variant}>
                    Could not fetch the products. Please try again later!
                </Alert>
            }
            {!hasError && 
                <div className="row row-cols-1 row-cols-md-3 g-4">
                    {products.map((product) => (
                        <div className="col" key={product.urlSlug}>
                            <div className="card h-100">
                                <img
                                    src={"../../images/iphone.jpg"}
                                    className="card-img-top"
                                />
                                <div className="card-body d-flex flex-column">
                                    <h5 className="card-title">{product.name}</h5>
                                    <p className="card-text">{product.description}</p>
                                    <p className="card-text">Vendor: {product.vendor}</p>
                                    <div className="d-flex justify-content-between align-items-center mt-auto">
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
            }
        </div>
    );
}

export default Products;
