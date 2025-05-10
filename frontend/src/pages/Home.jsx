import React from 'react';
import { Link } from 'react-router-dom';
import '../css/home.css';

const Home = () => {
  return (
    <>
      {/* Navigation Bar */}
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
                <Link className="nav-link" to="/packages">Packages</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/hotels">Hotels</Link>
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

      {/* Hero Carousel */}
      <div id="heroCarousel" className="carousel slide carousel-fade mb-5" data-bs-ride="carousel">
        <div className="carousel-indicators">
          <button type="button" data-bs-target="#heroCarousel" data-bs-slide-to="0" className="active"></button>
          <button type="button" data-bs-target="#heroCarousel" data-bs-slide-to="1"></button>
          <button type="button" data-bs-target="#heroCarousel" data-bs-slide-to="2"></button>
        </div>
        <div className="carousel-inner">
          <div className="carousel-item active">
            <div className="hero-slide slide-1">
              <div className="container">
                <div className="carousel-caption text-start">
                  <h1 className="display-4 fw-bold">Discover Amazing Destinations</h1>
                  <p className="lead">Explore the world with our exclusive travel packages</p>
                  <Link to="/packages" className="btn btn-primary btn-lg">View Packages</Link>
                </div>
              </div>
            </div>
          </div>
          <div className="carousel-item">
            <div className="hero-slide slide-2">
              <div className="container">
                <div className="carousel-caption text-end">
                  <h1 className="display-4 fw-bold">Luxury Stays</h1>
                  <p className="lead">Find the perfect hotel for your dream vacation</p>
                  <Link to="/hotels" className="btn btn-primary btn-lg">Browse Hotels</Link>
                </div>
              </div>
            </div>
          </div>
          <div className="carousel-item">
            <div className="hero-slide slide-3">
              <div className="container">
                <div className="carousel-caption">
                  <h1 className="display-4 fw-bold">Plan Your Budget</h1>
                  <p className="lead">Calculate your travel expenses with our smart tool</p>
                  <Link to="/budget" className="btn btn-primary btn-lg">Budget Calculator</Link>
                </div>
              </div>
            </div>
          </div>
        </div>
        <button className="carousel-control-prev" type="button" data-bs-target="#heroCarousel" data-bs-slide="prev">
          <span className="carousel-control-prev-icon"></span>
          <span className="visually-hidden">Previous</span>
        </button>
        <button className="carousel-control-next" type="button" data-bs-target="#heroCarousel" data-bs-slide="next">
          <span className="carousel-control-next-icon"></span>
          <span className="visually-hidden">Next</span>
        </button>
      </div>

      {/* Search Section */}
      <div className="container search-box mb-5 py-4 px-4 shadow rounded">
        <h2 className="text-center mb-4">Find Your Perfect Getaway</h2>
        <form>
          <div className="row g-3">
            <div className="col-md-4">
              <input type="text" className="form-control" placeholder="Destination City" />
            </div>
            <div className="col-md-3">
              <input type="date" className="form-control" placeholder="Check-in Date" />
            </div>
            <div className="col-md-3">
              <input type="date" className="form-control" placeholder="Check-out Date" />
            </div>
            <div className="col-md-2">
              <button type="submit" className="btn btn-primary w-100">Search</button>
            </div>
          </div>
        </form>
      </div>

      {/* Featured Packages */}
      <div className="container mb-5">
        <h2 className="text-center mb-5">Featured Travel Packages</h2>
        <div className="row g-4">
          {[1, 2, 3].map((idx) => (
            <div key={idx} className="col-md-4">
              <div className="card h-100 shadow-sm">
                <img 
                  src={`https://source.unsplash.com/random/300x200?travel,${idx}`} 
                  className="card-img-top" 
                  alt={`Travel Package ${idx}`}
                />
                <div className="card-body d-flex flex-column">
                  <h5 className="card-title">Premium Package {idx}</h5>
                  <p className="card-text flex-grow-1">
                    Experience luxury with our all-inclusive package including flights, 5-star hotels, and guided tours.
                  </p>
                  <div className="d-flex justify-content-between align-items-center">
                    <span className="h5 text-primary">$999</span>
                    <Link to="#" className="btn btn-outline-primary">View Details</Link>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
        <div className="text-center mt-4">
          <Link to="/packages" className="btn btn-link">
            View All Packages <i className="fas fa-arrow-right ms-2"></i>
          </Link>
        </div>
      </div>

      {/* Why Choose Us */}
      <section className="py-5 bg-light">
        <div className="container">
          <h2 className="text-center mb-5">Why Choose TravelEase</h2>
          <div className="row g-4">
            <div className="col-md-4 text-center">
              <div className="p-4">
                <i className="fas fa-shield-alt fa-3x text-primary mb-3"></i>
                <h3>Secure Booking</h3>
                <p>Your transactions are 100% secure with our encrypted payment system.</p>
              </div>
            </div>
            <div className="col-md-4 text-center">
              <div className="p-4">
                <i className="fas fa-headset fa-3x text-primary mb-3"></i>
                <h3>24/7 Support</h3>
                <p>Our travel experts are available round the clock to assist you.</p>
              </div>
            </div>
            <div className="col-md-4 text-center">
              <div className="p-4">
                <i className="fas fa-tag fa-3x text-primary mb-3"></i>
                <h3>Best Price Guarantee</h3>
                <p>We guarantee the best prices for all our travel packages.</p>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Testimonials */}
      <div className="container my-5">
        <h2 className="text-center mb-5">What Our Customers Say</h2>
        <div className="row g-4">
          {[
            {
              name: "Sarah Johnson",
              comment: "The travel package to Bali was amazing! Everything was perfectly arranged.",
              rating: 5
            },
            {
              name: "Michael Chen",
              comment: "Great service and support throughout our European tour. Highly recommended!",
              rating: 4
            },
            {
              name: "Emily Rodriguez",
              comment: "The budget calculator helped us plan our trip perfectly. Will use again!",
              rating: 5
            }
          ].map((testimonial, idx) => (
            <div key={idx} className="col-md-4">
              <div className="card h-100 shadow-sm">
                <div className="card-body d-flex flex-column">
                  <div className="mb-3">
                    {[...Array(testimonial.rating)].map((_, i) => (
                      <i key={i} className="fas fa-star text-warning"></i>
                    ))}
                  </div>
                  <p className="card-text flex-grow-1">"{testimonial.comment}"</p>
                  <div className="card-footer bg-white border-0">
                    <strong>- {testimonial.name}</strong>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* Newsletter */}
      <section className="py-5 bg-primary text-white">
        <div className="container text-center">
          <h2 className="mb-4">Subscribe to Our Newsletter</h2>
          <p className="lead mb-4">Get the latest travel deals and updates directly to your inbox</p>
          <form className="mx-auto" style={{ maxWidth: "500px" }}>
            <div className="mb-3">
              <input type="email" className="form-control form-control-lg" placeholder="Enter your email" />
            </div>
            <button type="submit" className="btn btn-light btn-lg">Subscribe</button>
          </form>
        </div>
      </section>

      {/* Footer */}
      <footer className="bg-dark text-white py-5">
        <div className="container">
          <div className="row">
            <div className="col-md-4 mb-4 mb-md-0">
              <h5 className="mb-3">TravelEase</h5>
              <p>Making travel planning easy and enjoyable since 2023.</p>
              <div className="social-icons">
                <a href="#" className="text-white me-3"><i className="fab fa-facebook-f"></i></a>
                <a href="#" className="text-white me-3"><i className="fab fa-twitter"></i></a>
                <a href="#" className="text-white me-3"><i className="fab fa-instagram"></i></a>
                <a href="#" className="text-white"><i className="fab fa-linkedin-in"></i></a>
              </div>
            </div>
            <div className="col-md-2 mb-4 mb-md-0">
              <h5 className="mb-3">Quick Links</h5>
              <ul className="list-unstyled">
                <li className="mb-2"><a href="#" className="text-white">Home</a></li>
                <li className="mb-2"><a href="#" className="text-white">Packages</a></li>
                <li className="mb-2"><a href="#" className="text-white">Hotels</a></li>
                <li className="mb-2"><a href="#" className="text-white">About Us</a></li>
              </ul>
            </div>
            <div className="col-md-3 mb-4 mb-md-0">
              <h5 className="mb-3">Support</h5>
              <ul className="list-unstyled">
                <li className="mb-2"><a href="#" className="text-white">FAQs</a></li>
                <li className="mb-2"><a href="#" className="text-white">Contact Us</a></li>
                <li className="mb-2"><a href="#" className="text-white">Privacy Policy</a></li>
                <li className="mb-2"><a href="#" className="text-white">Terms of Service</a></li>
              </ul>
            </div>
            <div className="col-md-3">
              <h5 className="mb-3">Contact Info</h5>
              <ul className="list-unstyled">
                <li className="mb-2"><i className="fas fa-map-marker-alt me-2"></i> 123 Travel St, Adventure City</li>
                <li className="mb-2"><i className="fas fa-phone me-2"></i> +1 (555) 123-4567</li>
                <li className="mb-2"><i className="fas fa-envelope me-2"></i> info@travelease.com</li>
              </ul>
            </div>
          </div>
          <hr className="my-4" />
          <div className="text-center">
            <p className="mb-0">&copy; {new Date().getFullYear()} TravelEase. All rights reserved.</p>
          </div>
        </div>
      </footer>
    </>
  );
};

export default Home;