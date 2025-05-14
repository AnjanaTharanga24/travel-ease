import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';  
import Home from './pages/Home';
import Booking from './pages/Booking';
import Budget from './pages/Budget';
import Packages from './pages/Packages';

function App() {
  return (
    <div className="App">
     <Router>
        <Routes>
          <Route path="/" element={<Home/>} />
          <Route path="/booking" element={<Booking/>} />
          <Route path="/budget" element={<Budget/>} />
          <Route path="/packages" element={<Packages/>} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
