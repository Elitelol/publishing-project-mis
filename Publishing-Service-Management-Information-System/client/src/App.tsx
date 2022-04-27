import React from 'react';
import './App.css';
import Login from "./components/Login";
import SignUp from "./components/SignUp";
import Nav from "./components/Nav";
import Hero from "./components/Hero";

function App() {

  return (
    <div>
      <Nav/>
        <Hero/>
    </div>
  );
}

export default App;
