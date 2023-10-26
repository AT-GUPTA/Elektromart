import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import "./singleProduct.css";
import "bootstrap/dist/css/bootstrap.min.css";
import {Button, Card} from "react-bootstrap";

function SingleProduct() {
    const {urlSlug} = useParams();
    const [product, setProduct] = useState(null);
    const [quantity, setQuantity] = useState(0);
    const [feedbackMessage, setFeedbackMessage] = useState(""); // For user feedback after cart operation
    const navigate = useNavigate();

    useEffect(() => {
        fetch(`http://localhost:8080/Elektromart_war/products/${urlSlug}`)
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

        const cartId = localStorage.getItem("cart_id");
        if (cartId) {
            fetch(`http://localhost:8080/Elektromart_war/cart/get-product-quantity?cartId=${cartId}&productSlug=${urlSlug}`)
                .then(response => response.json())
                .then(data => setQuantity(data.quantity))
                .catch(error => console.error("Error fetching product quantity: ", error));
        } else {
            setQuantity(0);
        }
    }, [urlSlug]);

    const addToCart = () => {
        let cartId = localStorage.getItem("cart_id");

        if (!cartId) {
            fetch(`http://localhost:8080/Elektromart_war/cart/create-cart`, {method: 'POST'})
                .then(response => response.json())
                .then(data => {
                    if (data.status === "SUCCESS") {
                        localStorage.setItem("cart_id", data.cartId);
                        addProductToCart(data.cartId, 1);
                    }
                })
                .catch(error => {
                    console.error("Error creating cart: ", error);
                    setFeedbackMessage("Error adding to cart. Please try again.");
                });
        } else {
            addProductToCart(cartId, quantity + 1);
        }
    };

    const addProductToCart = (cartId, newQuantity) => {
        let params = new URLSearchParams({
            cartId: cartId,
            productSlug: product.urlSlug,
            quantity: newQuantity
        });

        fetch(`http://localhost:8080/Elektromart_war/cart/add-product-to-cart?${params}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            }
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === "SUCCESS") {
                    setQuantity(newQuantity);
                    setFeedbackMessage(`Added ${newQuantity} ${product.name}(s) to the cart.`);
                }
            })
            .catch(error => {
                console.error("Error adding product to cart: ", error);
                setFeedbackMessage("Error adding to cart. Please try again.");
            });
    };

    const increaseQuantity = () => {
        setQuantity(prev => prev + 1);
        updateProductInCart(quantity + 1);
    };

    const decreaseQuantity = () => {
        if (quantity - 1 <= 0) {
            deleteProduct();
            setQuantity(0);
        } else {
            setQuantity(prev => prev - 1);
            updateProductInCart(quantity - 1);
        }
    };

    const updateProductInCart = (newQuantity) => {
        const cartId = localStorage.getItem("cart_id");
        fetch(`http://localhost:8080/Elektromart_war/cart/change-quantity`, {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: `cartId=${cartId}&productSlug=${product.urlSlug}&quantity=${quantity}`
        }).then(response => response.json())
            .catch(error => console.error("Error updating product quantity in cart: ", error));
    };

    const deleteProduct = () => {
        const cartId = localStorage.getItem("cart_id");
        fetch(`http://localhost:8080/Elektromart_war/deleteProductFromCart`, {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: `cartId=${cartId}&productSlug=${product.urlSlug}`
        }).then(response => response.json())
            .catch(error => console.error("Error deleting product from cart: ", error));
    };

    const navigateToCart = () => {
        navigate("/cart");
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
                    <Card.Img variant="top" src={"../../images/iphone.jpg"}/>
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
                                {quantity === 0 ? (
                                    <Button variant="primary" onClick={addToCart}>Add to Cart</Button>
                                ) : (
                                    <>
                                        <Button onClick={decreaseQuantity}>-</Button>
                                        <span>{quantity}</span>
                                        <Button onClick={increaseQuantity}>+</Button>
                                    </>
                                )}
                            </div>
                        </Card.Text>
                    </Card.Body>
                </Card>

                <Card>
                    <Card.Body>
                        <Button variant="secondary" onClick={navigateToCart}>View Cart</Button>
                    </Card.Body>
                </Card>
            </div>
        </div>
    );
}

export default SingleProduct;
