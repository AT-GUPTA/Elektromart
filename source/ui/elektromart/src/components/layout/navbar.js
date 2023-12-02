import React from 'react';
import "../../styles/navbar.css";
import 'bootstrap-icons/font/bootstrap-icons.css';
import 'bootstrap/dist/css/bootstrap.min.css'; // Import Bootstrap CSS
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import Swal from "sweetalert2";

function Navigation({ isAuth, roleId, logout }) {

    const handleMyOrdersClick = (e) => {
        if (!isAuth) {
            e.preventDefault(); // Prevents the default anchor action
            Swal.fire({
                title: "Unauthorized",
                text: "Please log in to access your orders",
                icon: "warning",
                confirmButtonText: "Login"
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = '/login';
                }
            });
        }
        // If isAuth is true, nothing happens and the default action proceeds
    };

    return (
        <div>
            <header className="banner" role="banner">
                <nav className="navbar" role="navigation" aria-label="menu">
                    <a href="/home" className="logo">
                        <img src="/images/elektromart.png" />
                    </a>
                    <ul className="menuNav">
                        <li className="dropdown nav-link nav-link-fade-up">
                            <a href="/home">Home</a>
                        </li>
                        <li className="dropdown nav-link nav-link-fade-up transition-all duration-700">
                            <a href="/products">Products</a>
                        </li>
                        {isAuth && ( // Assuming Claim Order should be visible only to logged-in users
                            <li className="dropdown nav-link nav-link-fade-up transition-all duration-700">
                                {/* Replace with <Link> if using react-router-dom */}
                                <a href="/claim-order">Claim Order</a>
                            </li>
                        )}
                        {(roleId === "2" || roleId === 2) && 
                        <li className="dropdown nav-link nav-link-fade-up transition-all duration-700">
                            <a href="/admin">Admin</a>
                        </li>
                        }
                        <p className='navLine absolute bg-red-600 w-1 font-extralight h-9 z-50'></p>
                        <a href="/cart" className="navIcon">
                            <i className="bi bi-cart"></i>
                        </a>
                        <div className="navItem dropdown" style={{ paddingTop: "15px" }}>
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
                                {localStorage.getItem("username") && (
                                    <span className="dropdown-item font-weight-bold text-success bg-light rounded fst-italic">
                                        Hi {localStorage.getItem("username")}
                                    </span>
                                )}
                                <a href="/orders" className="dropdown-item" onClick={handleMyOrdersClick}>My Orders</a>
                                {isAuth ? (
                                    <a className="dropdown-item" href="#" onClick={() => {
                                        logout();
                                        Swal.fire({
                                            title: "Logged Out Successfully!",
                                            text: "You've been successfully logged out",
                                            icon: "success",
                                            confirmButtonText: "Login Again"
                                        })
                                    }}>Logout</a>
                                    
                                ) : (
                                    <a className="dropdown-item" href="/login">Login</a>
                                )}
                                {isAuth && <a href="/account" className="dropdown-item" onClick={handleMyOrdersClick}>Change Passcode</a>}
                            </div>
                        </div>
                    </ul>
                </nav>
            </header>
        </div>
    );
}

export default Navigation;