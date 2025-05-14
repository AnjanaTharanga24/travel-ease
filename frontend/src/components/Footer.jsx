import React from 'react'

export default function Footer() {
  return (
    <div>

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
    </div>
  )
}
