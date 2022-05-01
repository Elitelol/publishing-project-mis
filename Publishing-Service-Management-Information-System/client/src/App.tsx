import React, {useContext} from 'react';
import './App.css';
import Nav from "./components/Nav";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import DashboardPage from "./pages/DashboardPage";
import ProtectedRoute from "./route/ProtectedRoute";
import SignUpPage from "./pages/SignUpPage";
import Navbar from "./components/Navbar";
import PublicationPage from "./pages/PublicationPage";
import {UserContext} from "./auth";
import SideMenu from "./components/SideMenu";

function App() {

    const[context, setContext] = useContext(UserContext);

  return (
      <>
          <BrowserRouter>
              <Navbar/>
              <SideMenu/>
              <Routes>
                  <Route path = "/" element={<LoginPage/>}/>
                  <Route path = "/signup" element={<SignUpPage/>}/>
                  <Route path = "/dashboard" element={<ProtectedRoute/>}>
                      <Route path = "/dashboard" element={<DashboardPage/>}/>
                  </Route>
                  <Route path = "/publication" element={<ProtectedRoute/>}>
                      <Route path = "/publication" element={<PublicationPage/>}/>
                  </Route>
              </Routes>
          </BrowserRouter>
      </>
  );
}

export default App;
