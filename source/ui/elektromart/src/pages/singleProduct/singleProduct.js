import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "./singleProduct.css";
import "bootstrap/dist/css/bootstrap.min.css";
import { Card, Button } from "react-bootstrap";

function SingleProduct() {
  const { productId } = useParams();
  const [product, setProduct] = useState(null);
  const [quantity, setQuantity] = useState(1);

  useEffect(() => {
    fetch(`http://localhost:8080/Elektromart/products/${productId}`)
      .then((response) => {
        if (response.ok) {
          return response.json();
        }
        throw new Error("Network response was not ok.");
      })
      .then((data) => {
        setProduct(data);
      })
      .catch((error) => {
        console.error("Error fetching product data: ", error);
      });
  }, [productId]);

  const handleQuantityChange = (event) => {
    setQuantity(event.target.value);
  };

  const handleIncrement = () => {
    setQuantity(quantity + 1);
  };

  const handleDecrement = () => {
    if (quantity > 1) {
      setQuantity(quantity - 1);
    }
  };

  //TODO: fix the logic
  const addToCart = () => {
    console.log(`Added ${quantity} ${product.name}(s) to the cart.`);
  };

  if (!product) {
    return <div>Loading...</div>;
  }

  return (
    <div className="single-product-container">
      <div className="left-side">
        <Card className="mb-3">
          <Card.Body>
            <Card.Title>{product.name}</Card.Title>
          </Card.Body>
        </Card>

        <div className="tags">
          <ul className="list-inline">
            <li className="list-inline-item tag-oval">
              ID: {product?.id || "N/A"}
            </li>
            <li className="list-inline-item tag-oval">
              {product?.sku || "N/A"}
            </li>
            {product.isFeatured ? (
              <li className="list-inline-item tag-oval">Featured</li>
            ) : null}
            {product.discountPercent > 0 ? (
              <li className="list-inline-item tag-oval">Discount: {product.discountPercent}%</li>
            ) : null}
          </ul>
        </div>

        <Card className="mb-3">
          <Card.Img variant="top" src={"../../images/iphone.jpg"} />
        </Card>

        <Card>
          <Card.Body>
            <Card.Title>Description</Card.Title>
            <Card.Text>
              <table>
                <tbody>
                  <tr>
                    <td>{product.description}</td>
                  </tr>
                </tbody>
              </table>
            </Card.Text>
          </Card.Body>
        </Card>
      </div>

      <div className="right-side">
        <Card className="mb-3">
          <Card.Body>
            <Card.Title>Price</Card.Title>
            <Card.Text>
              <p>Price: ${product.price.toFixed(2)}</p>
              {product.discountPercent > 0 && (
                <p className="discount">
                  Discount: ${product.price * (product.discountPercent / 100)}
                </p>
              )}
              <div className="quantity">
                <label>Quantity:</label>
                <div className="quantity-control">
                  <input
                    type="number"
                    value={quantity}
                    onChange={handleQuantityChange}
                  />
                </div>
              </div>
              <Button variant="primary" onClick={addToCart}>
                Add to Cart
              </Button>
            </Card.Text>
          </Card.Body>
        </Card>

        <Card>
          <Card.Body>
            <Button variant="secondary">View Cart</Button>
          </Card.Body>
        </Card>
      </div>
    </div>
  );
}

export default SingleProduct;
