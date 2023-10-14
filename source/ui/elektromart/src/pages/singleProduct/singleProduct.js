import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import './singleProduct.css'; 
import 'bootstrap/dist/css/bootstrap.min.css';
import { Card, Button } from 'react-bootstrap'; 

function SingleProduct() {
  const { productId } = useParams();
  const [product, setProduct] = useState(null);
  const [quantity, setQuantity] = useState(1);

  useEffect(() => {
    // Simulated product data
    const simulatedProduct = {
      id: productId,
      name: 'Sample Product',
      description: 'This is a sample product description.',
      price: 19.99,
      discount: 10,
      imageUrl: '/images/iphone.jpg',
      tags: {
        id: 12345,
        category: 'Electronics',
        featured: true,
      },
    };

    setProduct(simulatedProduct);
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

  const addToCart = () => {
    // Implement your addToCart logic here
    // For example, dispatch an action to add the product to the cart
    console.log(`Added ${quantity} ${product.name}(s) to the cart.`);
  };

  if (!product) {
    return <div>Loading...</div>;
  }

  return (
    <div className="single-product-container">
      {/* Left Side */}
      <div className="left-side">
        {/* Card for Product Name */}
        <Card className="mb-3">
          <Card.Body>
            <Card.Title>{product.name}</Card.Title>
          </Card.Body>
        </Card>

        {/* Tags */}
        <div className="tags">
          <ul className="list-inline">
            <li className="list-inline-item tag-oval">ID: {product.tags.id}</li>
            <li className="list-inline-item tag-oval">Category: {product.tags.category}</li>
            <li className="list-inline-item tag-oval">Featured: {product.tags.featured ? 'Yes' : 'No'}</li>
            <li className="list-inline-item tag-oval">Discount: {product.discount}%</li>
          </ul>
        </div>
        {/* Card for Product Image */}
        <Card className="mb-3">
          <Card.Img variant="top" src={product.imageUrl} />
        </Card>

        {/* Card for Description Table */}
        <Card>
          <Card.Body>
            <Card.Title>Description</Card.Title>
            <Card.Text>
              <table>
                <tbody>
                  {/* Add table rows for product description */}
                  <tr>
                    <td>Description:</td>
                    <td>{product.description}</td>
                  </tr>
                </tbody>
              </table>
            </Card.Text>
          </Card.Body>
        </Card>
      </div>

      {/* Right Side */}
      <div className="right-side">
        {/* Card for Price and Add to Cart */}
        <Card className="mb-3">
          <Card.Body>
            <Card.Title>Price</Card.Title>
            <Card.Text>
              <p>Price: ${product.price}</p>
              {product.discount > 0 && (
                <p className="discount">Discount: ${product.price * (product.discount / 100)}</p>
              )}
              <div className="quantity">
              <label>Quantity:</label>
          <div className="quantity-control">
            <input type="number" value={quantity} onChange={handleQuantityChange} />
          </div>
              </div>
              <Button variant="primary" onClick={addToCart}>
                Add to Cart
              </Button>
            </Card.Text>
          </Card.Body>
        </Card>

        {/* Card for View Cart Button */}
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
