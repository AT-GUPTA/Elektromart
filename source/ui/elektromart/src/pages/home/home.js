import React from 'react';
import Featured from './featured';
import '../../styles/home.css'

function Home() {
    return (
        <>
            <div className='d-flex justify-content-between welcome-container'>
                    <h1>Welcome to Elektromart!</h1>
                <div className='border p-3'>
                    <h3>Get started by visiting our <a href="/products">product catalog</a></h3>
                </div>
            </div>
            <Featured />
        </>
    );
}

export default Home;