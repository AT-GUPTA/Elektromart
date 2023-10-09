import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './pages/login';
import Signup from './pages/signup';
import Home from './pages/home/home'
import Admin from './pages/admin';
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
              <Route path="/admin" component={Admin} exact />
              <Route path="/products" component={Products} exact />
              <Route path="/cart" component={Cart} exact />
              <Route path="/product/:productId" element={<SingleProduct />} />
          </Routes>
        </Router>
      </Layout>
      </>
  );
}

export default App;
