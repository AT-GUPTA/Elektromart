import React from 'react';
import Navigation from './navbar';

function Layout({children}) {
    return (
        <>
        <Navigation />
        <div className="container mt-5">
            {children}
        </div>
        </>
    );
}

export default Layout;