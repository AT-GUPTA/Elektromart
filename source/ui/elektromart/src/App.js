import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './pages/login';
import Signup from './pages/signup';
import Home from './pages/home/home'
import Admin from './pages/admin';
import Products from './pages/products/products';
import ProductDetail from './pages/products/productDetail';
import Cart from './pages/cart';
import Layout from './components/layout/layout';


function App() {
  return (
    <>
      <Layout>
        <Router>
          <Routes>
              <Route path="/login" component={Login} />
              <Route path="/signup" component={Signup} />
              <Route path="/home" element={<Home />}exact />
              <Route path="/" element={<Home />} exact />
              <Route path="/admin" component={Admin} exact />
              <Route path="/products" element={<Products />} exact />
              <Route path="/products/:id" element={<ProductDetail />} />
              <Route path="/cart" component={Cart} exact />
          </Routes>
        </Router>
      </Layout>
      </>
  );
}

export default App;
