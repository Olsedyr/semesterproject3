import React from 'react';
import { Route, Navigate } from 'react-router-dom';

const PrivateRoute = ({ element, authenticated, redirectTo }) => {
    return authenticated ? (
        element
    ) : (
        <Navigate to={redirectTo} replace state={{ from: window.location.pathname }} />
    );
};

export default PrivateRoute;
