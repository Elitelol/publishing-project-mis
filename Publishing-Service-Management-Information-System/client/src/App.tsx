import React from 'react';
import './App.css';
import Nav from "./components/Nav";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import DashboardPage from "./pages/DashboardPage";
import ProtectedRoute from "./route/ProtectedRoute";
import SignUpPage from "./pages/SignUpPage";

function App() {

  return (
    <BrowserRouter>
      <Nav/>
        <Routes>
            <Route path = "/" element={<LoginPage/>}/>
            <Route path = "/signup" element={<SignUpPage/>}/>
            <Route path = "/dashboard" element={<ProtectedRoute/>}>
                <Route path = "/dashboard" element={<DashboardPage/>}/>
            </Route>
        </Routes>
    </BrowserRouter>
  );
}

export default App;
