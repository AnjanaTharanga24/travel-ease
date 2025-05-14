import React from 'react'
import { Link } from 'react-router-dom'

export default function Navbar() {
  return (
    <div>
         <nav className="navbar navbar-expand-lg navbar-dark bg-dark py-3 sticky-top">
        <div className="container">
          <Link className="navbar-brand d-flex align-items-center" to="/">
            <i className="fas fa-plane-departure me-2"></i>
            <span className="fw-bold">TravelEase</span>
          </Link>
          <button 
            className="navbar-toggler" 
            type="button" 
            data-bs-toggle="collapse" 
            data-bs-target="#navbarNav"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav me-auto">
              <li className="nav-item">
                <Link className="nav-link active" to="/">Home</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/booking">Booking</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/packages">Packages</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/budget">Budget Calculator</Link>
              </li>
            </ul>
            <div className="d-flex">
              <Link to="/login" className="btn btn-outline-light me-2">Login</Link>
              <Link to="/register" className="btn btn-primary">Register</Link>
            </div>
          </div>
        </div>
      </nav>
    </div>
  )
}
