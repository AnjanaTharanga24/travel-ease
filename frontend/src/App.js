import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';  
import Home from './pages/Home';
import Booking from './pages/Booking';
import Budget from './pages/Budget';
import Packages from './pages/Packages';
import Register from './pages/Register';
import Login from './pages/Login';
import { UserProvider } from './common/UserContext';

function App() {
  return (
    <UserProvider>
      <div className="App">
        <Router>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/booking" element={<Booking />} />
            <Route path="/budget" element={<Budget />} />
            <Route path="/packages" element={<Packages />} />
            <Route path="/register" element={<Register />} />
            <Route path="/login" element={<Login />} />
          </Routes>
        </Router>
      </div>
    </UserProvider>
  );
}

export default App;