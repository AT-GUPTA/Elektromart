import React, {useState} from 'react';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Login from './pages/login/Login';
import Signup from './pages/signup/Signup';
import Home from './pages/home/home'
<<<<<<< HEAD
import Admin from './pages/admin/admin';
import Products from './pages/products';
import Cart from './pages/cart';
import Layout from './components/layout/layout';
import SingleProduct from './pages/singleProduct/singleProduct';


function App() {
  return (
    <>
      <Layout>
        <Router>
          <Routes>
              <Route path="/login" component={Login} />
              <Route path="/signup" component={Signup} />
              <Route path="/home" component={Home} exact />
              <Route path="/" element={<Home />} exact />
              <Route path="/admin" element={<Admin />} exact />
              <Route path="/products" component={Products} exact />
              <Route path="/cart" component={Cart} exact />
              <Route path="/product/:productId" element={<SingleProduct />} />
          </Routes>
        </Router>
      </Layout>
      </>
  );
=======
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
>>>>>>> origin/master
}

export default App;
