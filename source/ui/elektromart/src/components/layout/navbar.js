import React from 'react';
import "../../styles/navbar.css";
import 'bootstrap-icons/font/bootstrap-icons.css';
import 'bootstrap/dist/css/bootstrap.min.css'; // Import Bootstrap CSS
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import Swal from "sweetalert2";

function Navigation({isAuth, roleId}) {
    return (
        <div>
            <header className="banner" role="banner">
                <nav className="navbar" role="navigation" aria-label="menu">
                    <a href="/home" className="logo">
                        <img src="/images/elektromart.png"/>
                    </a>
                    <ul className="menuNav">
                       <li className="dropdown nav-link nav-link-fade-up">
                        <a href="/home">Home</a>
                      </li>
                        <li className="dropdown nav-link nav-link-fade-up transition-all duration-700">
                            <a href="/products">Products</a>
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
                                {isAuth ? (
                                    <a className="dropdown-item" href="#" onClick={() => {
                                        localStorage.removeItem("isAuth");
                                        localStorage.removeItem("cart_id");
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
                                {roleId === "2" && <a className="dropdown-item" href="/admin">Admin</a>}
                            </div>
                        </div>
                    </ul>
                </nav>
            </header>
        </div>
    );
}

export default Navigation;