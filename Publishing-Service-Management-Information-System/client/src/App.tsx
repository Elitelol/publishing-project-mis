import React from 'react';
import './App.css';
import Nav from "./components/Nav";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import MainPage from "./Page/MainPage";
import DashboardPage from "./Page/DashboardPage";

function App() {

  return (
    <BrowserRouter>
      <Nav/>
        <Routes>
            <Route path = "/" element={<MainPage/>}/>
            <Route path = "/dashboard" element={<DashboardPage/>}/>
        </Routes>
    </BrowserRouter>
  );
}

export default App;
