import React, {useState} from 'react';
import {BrowserRouter as Router, Route, Routes, Navigate} from 'react-router-dom';
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

function App() {

    const [roleId, setRoleId] = useState(null);

    const [isAuth, setIsAuth] = useState(() => {
        const saved = localStorage.getItem("isAuth");
        const initialValue = JSON.parse(saved);
        return initialValue || false;
    });
    const allowAuth = async (doAllow, roleId = null) => {
        setIsAuth(doAllow);
        setRoleId(roleId);
        localStorage.setItem("isAuth", JSON.stringify(doAllow));
    };

    return (
        <>
            <Layout isAuth={isAuth} roleId={roleId}>
                <Router>
                    <Routes>
                        <Route exact path="/login" element={<Login authentication={allowAuth} setRoleId={setRoleId}/>}/>
                        <Route exact path="/signup" element={<Signup/>}/>
                        <Route path="/home" element={<Home/>} exact/>
                        <Route path="/" element={<Home/>} exact/>
                        <Route
                            path="/admin"
                            element={isAuth ? <Admin /> : <Navigate to="/home" replace={true} />}
                        />
                        <Route path="/admin" element={<Admin/>} exact/>
                        <Route path="/product/:urlSlug" element={<SingleProduct/>}/> 
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
