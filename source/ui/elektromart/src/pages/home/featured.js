import React from 'react';
import '../../styles/featured.css'

function Featured() {
    return (
        <div className='mt-5 featured-container'>
            <div className='d-flex justify-content-center'>
            <h2><u>Featured Products</u></h2>
            </div>
            <div class="d-flex justify-content-between gap-3">
                <div className='d-flex justify-content-between align-items-center'>
                    <div style={{marginRight: "30px"}}>
                        <button className="left-arrow">&#9666;</button>
                    </div>
                    <div>
                        <h3>Iphone 12</h3>
                        <img src="/images/iphone.jpg" alt="iPhone 12" width="200px" height="150px" />
                    </div>
                </div>
                <div>
                    <h3>Pixel 7</h3>
                    <img src="/images/pixel.jpg" alt="Pixel 7" className="featured-image" width="200px" height="150px" />
                </div>
                <div>
                    <h3>Samsung Galaxy S21</h3>
                    <img src="/images/galaxy.jpg" alt="Samsung Galaxy S21" className="featured-image" width="200px" height="150px" />
                </div>
                <div className='d-flex justify-content-between align-items-center'>
                    <div>
                        <h3>Nokia Solid state 2</h3>
                        <img src="/images/nokia.jpg" alt="iPhone 12" width="200px" height="150px" />
                    </div>
                    <div className="">
                        <button>&#9656;</button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Featured;
