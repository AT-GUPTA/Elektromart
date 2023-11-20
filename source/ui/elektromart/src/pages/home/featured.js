import React, { useState, useEffect, useRef } from 'react';
import '../../styles/featured.css';

function Featured() {
    const [featuredProducts, setFeaturedProducts] = useState([]);
    const [currentIndex, setCurrentIndex] = useState(0);
    const autoPlayRef = useRef();
    const intervalRef = useRef();

    const images = ['galaxy.jpg', 'Iphone.jpg', 'nokia.jpg', 'pixel.jpg', 'xiaomi.jpg'];

    useEffect(() => {
        fetch('http://localhost:8080/api/products/featured')
            .then(response => response.json())
            .then(data => {
                console.log(data);
                setFeaturedProducts(data);
            })
            .catch(error => console.error('Error fetching featured products:', error));
    }, []);

    useEffect(() => {
        autoPlayRef.current = goToNext;
    });

    useEffect(() => {
        startAutoPlay();
        return () => {
            if (intervalRef.current) {
                clearInterval(intervalRef.current);
            }
        };
    }, []);

    const startAutoPlay = () => {
        if (intervalRef.current) {
            clearInterval(intervalRef.current);
        }
        intervalRef.current = setInterval(() => autoPlayRef.current(), 5000);
    };

    const resetAutoPlay = () => {
        startAutoPlay();
    };

    const goToPrevious = () => {
        const isFirstItem = currentIndex === 0;
        const newIndex = isFirstItem ? featuredProducts.length - 1 : currentIndex - 1;
        setCurrentIndex(newIndex);
        resetAutoPlay();
    };

    const goToNext = () => {
        const isLastItem = currentIndex === featuredProducts.length - 1;
        const newIndex = isLastItem ? 0 : currentIndex + 1;
        setCurrentIndex(newIndex);
        resetAutoPlay();
    };

    return (
        <div className="featured-carousel-container">
            {featuredProducts.length > 0 ? (
                <div className="featured-carousel">
                    {featuredProducts.map((product, index) => (
                        <a href={`/product/${product.urlSlug}`} key={index} className={`carousel-slide ${index === currentIndex ? 'active' : ''}`} style={{ transform: `translateX(-${currentIndex * 100}%)` }}>
                            <img
                                className="carousel-image"
                                src={`../../images/${images[index % images.length]}`}
                                alt={product.name}
                            />
                            <div className="carousel-caption">
                                <h3>{product.name}</h3>
                                <p>{product.description}</p>
                                <p>Price: ${product.price}</p>
                            </div>
                        </a>
                    ))}
                    <button className="carousel-control prev" onClick={goToPrevious}>&lt;</button>
                    <button className="carousel-control next" onClick={goToNext}>&gt;</button>
                </div>
            ) : (
                <p>Loading featured products...</p>
            )}
        </div>
    );
}

export default Featured;
