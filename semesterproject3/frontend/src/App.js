import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import PM from './PM';
import JD from './JD';
import Login from './Login';

function App() {
    return (
        <BrowserRouter>
            <Routes>
                {/* Define routes for other components/pages */}
                <Route path="/pm" element={<PM />} />
                <Route path="/jd" element={<JD />} />

                {/* The default route, rendering the Login component */}
                <Route path="/" element={<Login />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;




