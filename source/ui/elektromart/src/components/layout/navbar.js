import React from 'react';
import "../../styles/navbar.css";
import 'bootstrap-icons/font/bootstrap-icons.css';
import 'bootstrap/dist/css/bootstrap.min.css'; // Import Bootstrap CSS
import 'bootstrap/dist/js/bootstrap.bundle.min.js'; // Import Bootstrap JavaScript


function Navigation() {
  return (
    <div>
      <header className="banner" role="banner">
        <nav className="navbar" role="navigation" aria-label="menu">
          <a href="/home" className="logo">
            <img src="/images/elektromart.png" />
          </a>
          <ul className="menuNav">
            <li className="dropdown nav-link nav-link-fade-up transition-all duration-700">
              <a href="/products">Products</a>
            </li>
            <li className="dropdown nav-link nav-link-fade-up">
              <a href="/products">Featured</a>
            </li>
            <p className='navLine absolute bg-red-600 w-1 font-extralight h-9 z-50'></p>
            <a href="/cart" className="navIcon">
              <i className="bi bi-cart"></i>
            </a>
            <div className="navItem dropdown" style={{paddingTop: "15px"}}>
              <a
                href="#"
                className="navIcon dropdown-toggle"
                id="accountDropdown"
                role="button"
                data-bs-toggle="dropdown"
                aria-haspopup="true"
                aria-expanded="false"
              >
                <i className="bi bi-person-circle"></i>
              </a>
              <div className="dropdown-menu" aria-labelledby="accountDropdown">
                <a className="dropdown-item" href="/login">Customer Login</a>
                <a className="dropdown-item" href="/admin">Admin</a>
              </div>
            </div>
          </ul>
        </nav>
      </header>
    </div>
  );
}

export default Navigation;