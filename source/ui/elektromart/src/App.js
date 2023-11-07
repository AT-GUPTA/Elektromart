import React, {useState} from 'react';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Login from './pages/login/Login';
import Signup from './pages/signup/Signup';
import Home from './pages/home/home'
import Admin from './pages/admin/admin';
import SingleProduct from './pages/singleProduct/singleProduct';
import Products from './pages/products/products';
import ProductDetail from './pages/products/productDetail';
import Cart from './pages/cart/cart';
import Layout from './components/layout/layout';
import 'bootstrap-icons/font/bootstrap-icons.css';
import swal from 'sweetalert2';
import Orders from './pages/order/orders';
import OrderDetail from './pages/order/orderDetail';
import OrderPlaced from './pages/order/orderPlaced';
import Checkout from './pages/order/checkout';

function App() {

    const [isAuth, setIsAuth] = useState(() => {
        const saved = localStorage.getItem("isAuth");
        const initialValue = JSON.parse(saved);
        return initialValue || false;
    });

    const [roleId, setRoleId] = useState(() => {
        const saved = localStorage.getItem("roleId");
        const initialValue = JSON.parse(saved);
        return initialValue || null;
    });

    const allowAuth = async (doAllow, roleId) => {
        setIsAuth(doAllow);
        setRoleId(roleId);
        localStorage.setItem("isAuth", JSON.stringify(doAllow));
        localStorage.setItem("roleId", JSON.stringify(roleId));
    };

    const logout = () => {
        localStorage.removeItem("isAuth");
        localStorage.removeItem("roleId"); // Clear roleId
        localStorage.removeItem("cart_id");
        localStorage.removeItem("id");
        localStorage.removeItem("secret");
        localStorage.clear();
        sessionStorage.clear();
        setIsAuth(false);
        setRoleId(null); // Reset roleId state as well
    };

    function AdminRoute({isAuth}) {
        if (!isAuth) {
            swal.fire("Unauthorized!", "You don't have permission to access this page.", "error").then(() => {
                window.location.replace("/home");
            });
            return null;
        }

        return <Admin/>;
    }

    function OrderRoute({isAuth}) {
        if (!isAuth) {
            swal.fire({
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
        return <Orders/>
    }

    function CheckoutRoute({isAuth}) {
        if (!isAuth) {
            swal.fire({
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
        return <Checkout/>
    }

    return (
        <>
            <Layout isAuth={isAuth} roleId={roleId} logout={logout}>
                <Router>
                    <Routes>
                        <Route exact path="/login" element={<Login authentication={allowAuth} setRoleId={setRoleId}/>}/>
                        <Route exact path="/signup" element={<Signup/>}/>
                        <Route path="/home" element={<Home/>} exact/>
                        <Route path="/" element={<Home/>} exact/>
                        <Route path="/admin" element={<AdminRoute isAuth={isAuth}/>}/>
                        <Route path="/product/:urlSlug" element={<SingleProduct/>}/>
                        <Route path="/products" element={<Products/>} exact/>
                        <Route path="/products/:id" element={<ProductDetail/>}/>
                        <Route path="/cart" element={<Cart isAuth={isAuth}/>} exact/>
                        <Route path="/orders" element={<OrderRoute isAuth={isAuth}/>}/>
                        <Route path="/orders/:id" element={<OrderDetail/>} isAuth={isAuth} exact/>
                        <Route path="/checkout" element={<CheckoutRoute isAuth={isAuth}/>}/>
                        <Route path="/order-placed" element={<OrderPlaced isAuth={isAuth}/>}/>
                    </Routes>
                </Router>
            </Layout>
        </>
    );
}

export default App;
