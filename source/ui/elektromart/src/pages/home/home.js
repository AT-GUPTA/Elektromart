import React from 'react';
import Featured from './featured';
import '../../styles/home.css';

function Home() {
    return (
        <div className='home-container'>
            <header className='home-header'>
                <h1>Welcome to Elektromart!</h1>
                <p>Explore our wide range of electronic products.</p>
            </header>
            <Featured />
            <div className='catalog-link'>
                <h3>Discover More</h3>
                <a className='btn btn-primary' href="/products">View Product Catalog</a>
            </div>
        </div>
    );
}

export default Home;