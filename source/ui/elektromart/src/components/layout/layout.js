import React from 'react';
import Navigation from './navbar';

function Layout({isAuth, children}) {
    return (
        <>
        <Navigation isAuth={isAuth}/>
        <div className="container mt-5">
            {children}
        </div>
        </>
    );
}

export default Layout;