import React, { useState } from "react";
import Navbar from "../components/Navbar";
import Footer from "../components/Footer";

export default function Packages() {
  const [selectedCity, setSelectedCity] = useState("");
  const [hotelQuery, setHotelQuery] = useState("");
  const [packages, setPackages] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [activeSearch, setActiveSearch] = useState(null);
  const [cities] = useState(['Colombo', 'Kandy', 'Galle', 'Nuwara Eliya']);

  const searchByCity = async (e) => {
    e.preventDefault();
    if (!selectedCity) return;
    
    setLoading(true);
    setError(null);
    setActiveSearch('city');
    
    try {
      const response = await fetch(`http://localhost:8080/packages/city/${selectedCity}`);
      if (!response.ok) {
        throw new Error(`No packages found for ${selectedCity}`);
      }
      const data = await response.json();
      setPackages(data);
    } catch (err) {
      setError(err.message);
      setPackages([]);
    } finally {
      setLoading(false);
    }
  };

  const searchByHotel = async (e) => {
    e.preventDefault();
    if (!hotelQuery.trim()) return;
    
    setLoading(true);
    setError(null);
    setActiveSearch('hotel');
    
    try {
      const response = await fetch(`http://localhost:8080/packages/hotel/${hotelQuery}`);
      if (!response.ok) {
        throw new Error(`No packages found for ${hotelQuery}`);
      }
      const data = await response.json();
      setPackages(data);
    } catch (err) {
      setError(err.message);
      setPackages([]);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="d-flex flex-column min-vh-100">
      <Navbar />
      
      <main className="container my-5 flex-grow-1">
        <div className="row justify-content-center">
          <div className="col-md-8">
            <h2 className="mb-4 text-center">Search Travel Packages</h2>
            
            <div className="card shadow-sm mb-4">
              <div className="card-header bg-light">
                <h3 className="h5 mb-0">Search by City</h3>
              </div>
              <div className="card-body">
                <form onSubmit={searchByCity}>
                  <div className="input-group">
                    <select
                      className="form-select"
                      value={selectedCity}
                      onChange={(e) => setSelectedCity(e.target.value)}
                      disabled={loading && activeSearch !== 'city'}
                      required
                    >
                      <option value="">Select a city</option>
                      {cities.map((city) => (
                        <option key={city} value={city}>{city}</option>
                      ))}
                    </select>
                    <button 
                      type="submit" 
                      className="btn btn-primary"
                      disabled={loading && activeSearch !== 'city'}
                    >
                      {loading && activeSearch === 'city' ? 'Searching...' : 'Search'}
                    </button>
                  </div>
                </form>
              </div>
            </div>
            
            <div className="card shadow-sm mb-4">
              <div className="card-header bg-light">
                <h3 className="h5 mb-0">Search by Hotel Name</h3>
              </div>
              <div className="card-body">
                <form onSubmit={searchByHotel}>
                  <div className="input-group">
                    <input
                      type="text"
                      className="form-control"
                      placeholder="Enter hotel name (e.g. Grand Hyatt)"
                      value={hotelQuery}
                      onChange={(e) => setHotelQuery(e.target.value)}
                      disabled={loading && activeSearch !== 'hotel'}
                    />
                    <button 
                      type="submit" 
                      className="btn btn-primary"
                      disabled={loading && activeSearch !== 'hotel'}
                    >
                      {loading && activeSearch === 'hotel' ? 'Searching...' : 'Search'}
                    </button>
                  </div>
                </form>
              </div>
            </div>
            
            <div className="mt-4">
              {error && (
                <div className="alert alert-danger">
                  {error}
                </div>
              )}
              
              {packages.length > 0 && (
                <div className="card shadow-sm">
                  <div className="card-header bg-light">
                    <h3 className="h5 mb-0">
                      {activeSearch === 'city' 
                        ? `Packages in ${selectedCity}`
                        : `Packages at ${hotelQuery}`}
                    </h3>
                  </div>
                  
                  <div className="card-body p-0">
                    <div className="list-group list-group-flush">
                      {packages.map((pkg) => (
                        <div key={pkg.id} className="list-group-item p-4">
                          <div className="d-flex justify-content-between align-items-start">
                            <div>
                              <h4 className="h5">{pkg.packageName}</h4>
                              <p className="text-muted mb-1">
                                <i className="bi bi-geo-alt-fill me-2"></i>
                                {pkg.destination}
                              </p>
                              <p className="text-muted mb-2">
                                <i className="bi bi-building me-2"></i>
                                {pkg.hotelName}
                              </p>
                              <p className="mb-2">{pkg.description}</p>
                            </div>
                            <div className="text-end">
                              <span className="h4 text-primary">${pkg.price}</span>
                              <div className="mt-2">
                                <button className="btn btn-sm btn-outline-primary">
                                  View Details
                                </button>
                              </div>
                            </div>
                          </div>
                        </div>
                      ))}
                    </div>
                  </div>
                </div>
              )}
            </div>
          </div>
        </div>
      </main>
      
      <Footer />
    </div>
  );
}