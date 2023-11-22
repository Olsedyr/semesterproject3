import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Login from './Login';
import OpcUaValuesDisplay from './components/OpcUaValuesDisplay';
import './App.css';

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <Routes>
          <Route index element={<Login />} />
          <Route path="OpcUaValuesDisplay" element={<OpcUaValuesDisplay />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}
export default App;
/*





function App() {
  return (
      <div>
          <Login />
      </div>
  );
}
export default App;

*/