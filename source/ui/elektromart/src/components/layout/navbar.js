import React from 'react';


function Navigation({children}) {
  return (
    <nav class="navbar navbar-expand-lg bg-body-tertiary px-5">
      <div class="container-fluid">
        <a class="navbar-brand" href="/home">Elektromart</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse d-flex justify-content-end" id="navbarSupportedContent">
          <ul className="navbar-nav ml-auto">
            <li class="nav-item">
              <a class="nav-link" href="/admin">Admin</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="/products">Product Catalog</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="/cart">Cart</a>
            </li><li class="nav-item">
              <a class="nav-link" href="/login">Login/Sign up</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>

  );
}

export default Navigation;