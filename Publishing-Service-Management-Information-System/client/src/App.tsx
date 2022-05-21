import React, {useContext} from 'react';
import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import DashboardPage from "./pages/DashboardPage";
import ProtectedRoute from "./route/ProtectedRoute";
import SignUpPage from "./pages/SignUpPage";
import PublicationDetailsPage from "./pages/PublicationDetailsPage";
import ContractPage from "./pages/ContractPage";
import BudgetPage from "./pages/BudgetPage";
import TaskPage from "./pages/TaskPage";
import TaskDetailsPage from "./pages/TaskDetailsPage";
import AttachmentPage from "./pages/AttachmentPage";
import UnmanagedWorksPage from "./pages/UnmanagedWorksPage";
import ProjectsPage from "./pages/ProjectsPage";
import UsersPage from "./pages/UsersPage";
import UserDetailsPage from "./pages/UserDetailsPage";
import AccountDetailsPage from "./pages/AccountDetailsPage";
import WorksPage from "./pages/WorksPage";;

function App() {

  return (
      <>
          <BrowserRouter>
              <Routes>
                  <Route path = "/" element={<LoginPage/>}/>
                  <Route path = "/signup" element={<SignUpPage/>}/>
                  <Route path = "/dashboard" element={<ProtectedRoute/>}>
                      <Route path = "/dashboard" element={<DashboardPage/>}/>
                  </Route>
                  <Route path = "/account" element={<ProtectedRoute/>}>
                      <Route path = "/account" element={<AccountDetailsPage/>}/>
                  </Route>
                  <Route path = "/unmanagedWorks" element={<ProtectedRoute/>}>
                      <Route path = "/unmanagedWorks" element={<UnmanagedWorksPage/>}/>
                  </Route>
                  <Route path = "/projects" element={<ProtectedRoute/>}>
                      <Route path = "/projects" element={<ProjectsPage/>}/>
                  </Route>
                  <Route path = "/users" element={<ProtectedRoute/>}>
                      <Route path = "/users" element={<UsersPage/>}/>
                  </Route>
                  <Route path = "/:role/:id" element={<ProtectedRoute/>}>
                      <Route path = "/:role/:id" element={<UserDetailsPage/>}/>
                  </Route>
                  <Route path = "/:role/:id/works" element={<ProtectedRoute/>}>
                      <Route path = "/:role/:id/works" element={<WorksPage/>}/>
                  </Route>
                  <Route path = "/publication/:id" element={<ProtectedRoute/>}>
                      <Route path = "/publication/:id" element={<PublicationDetailsPage/>}/>
                  </Route>
                  <Route path = "/:id/attachments" element={<ProtectedRoute/>}>
                      <Route path = "/:id/attachments" element={<AttachmentPage/>}/>
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
                  <Route path = "/task/:id" element={<ProtectedRoute/>}>
                      <Route path = "/task/:id" element={<TaskDetailsPage/>}/>
                  </Route>
              </Routes>
          </BrowserRouter>
      </>
  );
}

export default App;
