import React, {useState} from 'react';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Login from './pages/login/Login';
import Signup from './pages/signup/Signup';
import Home from './pages/home/home'
import Admin from './pages/admin';
import Products from './pages/products/products';
import ProductDetail from './pages/products/productDetail';
import Cart from './pages/cart/cart';
import Layout from './components/layout/layout';
import 'bootstrap-icons/font/bootstrap-icons.css';

function App() {
    const [isAuth, setIsAuth] = useState(() => {
        const saved = localStorage.getItem("isAuth");
        const initialValue = JSON.parse(saved);
        return initialValue || false;
    });
    const allowAuth = async (doAllow) => {
        setIsAuth(doAllow);
        localStorage.setItem("isAuth", JSON.stringify(doAllow));
    };
    return (
        <>
            <Layout>
                <Router>
                    <Routes>
                        <Route exact path="/login" element={<Login authentication={allowAuth}/>}/>
                        <Route exact path="/signup" element={<Signup/>}/>
                        <Route path="/home" element={<Home/>} exact/>
                        <Route path="/" element={<Home/>} exact/>
                        <Route path="/admin" component={Admin} exact/>
                        <Route path="/products" element={<Products/>} exact/>
                        <Route path="/products/:id" element={<ProductDetail/>}/>
                        <Route path="/cart" element={<Cart/>} exact/>
                    </Routes>
                </Router>
            </Layout>
        </>
    );
}

export default App;
