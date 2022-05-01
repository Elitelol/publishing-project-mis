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
import ContractPage from "./pages/ContractPage";
import BudgetPage from "./pages/BudgetPage";
import TaskPage from "./pages/TaskPage";

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
                  <Route path = "/publication/:id" element={<ProtectedRoute/>}>
                      <Route path = "/publication/:id" element={<PublicationPage/>}/>
                  </Route>
                  <Route path = "/:id/contract" element={<ProtectedRoute/>}>
                      <Route path = "/:id/contract" element={<ContractPage/>}/>
                  </Route>
                  <Route path = "/:id/budget" element={<ProtectedRoute/>}>
                      <Route path = "/:id/budget" element={<BudgetPage/>}/>
                  </Route>
                  <Route path = "/:id/task" element={<ProtectedRoute/>}>
                      <Route path = "/:id/task" element={<TaskPage/>}/>
                  </Route>
              </Routes>
          </BrowserRouter>
      </>
  );
}

export default App;
