import React from 'react';
import Navigation from './navbar';

function Layout({children, isAuth, roleId}) {
    return (
        <>
            <Navigation isAuth={isAuth} roleId={roleId}/>
            <div className="container mt-5">
                {children}
            </div>
        </>
    );
}

export default Layout;
