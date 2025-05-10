import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';  
import Home from './pages/Home';

function App() {
  return (
    <div className="App">
     <Router>
        <Routes>
          <Route path="/" element={<Home/>} />
          {/* <Route path="/" element={<h1>About</h1>} /> */}
        </Routes>
      </Router>
    </div>
  );
}

export default App;
