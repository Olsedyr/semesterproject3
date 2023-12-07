import React, { useState } from 'react';
import { BrowserRouter, Routes, Route, Outlet, Navigate } from 'react-router-dom';
import PM from './PM';
import JD from './JD';
import Login from './Login';
import OpcUaValuesDisplay from './components/OpcUaValuesDisplay';
import PrivateRoute from './components/PrivateRoute';

function App() {
    const [authenticated, setAuthenticated] = useState(false);

    //Function to check authentication status
    const checkAuthentication = () => {
        return authenticated;
    };

    return (
        <BrowserRouter>
            <Routes>
                <Route
                    path="/"
                    element={
                        checkAuthentication() ? (
                            <Navigate to="/pm" replace />
                        ) : (
                            <Login setAuthenticated={setAuthenticated} />
                        )
                    }
                />
                <Route
                    path="/pm/*"
                    element={
                        <PrivateRoute
                            element={<PM />}
                            authenticated={checkAuthentication()}
                            redirectTo="/"
                        />
                    }
                />
                <Route
                    path="/jd/*"
                    element={
                        <PrivateRoute
                            element={<JD />}
                            authenticated={checkAuthentication()}
                            redirectTo="/"
                        />
                    }
                />
                <Route path="/OpcUaValuesDisplay" element={<OpcUaValuesDisplay />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
