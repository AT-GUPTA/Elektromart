import React from 'react';
import Navigation from './navbar';

function Layout({children, isAuth, roleId, logout}) {
    return (
        <>
            <Navigation isAuth={isAuth} roleId={roleId} logout={logout}/>
            <div className="container mt-5">
                {children}
            </div>
        </>
    );
}

export default Layout;
