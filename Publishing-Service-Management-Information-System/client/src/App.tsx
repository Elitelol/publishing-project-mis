import React from 'react';
import './App.css';
import Nav from "./components/Nav";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import MainPage from "./Page/MainPage";
import DashboardPage from "./Page/DashboardPage";
import ProtectedRoute from "./route/ProtectedRoute";

function App() {

  return (
    <BrowserRouter>
      <Nav/>
        <Routes>
            <Route path = "/" element={<MainPage/>}/>
            <Route path = "/dashboard" element={<ProtectedRoute/>}>
                <Route path = "/dashboard" element={<DashboardPage/>}/>
            </Route>
        </Routes>
    </BrowserRouter>
  );
}

export default App;
