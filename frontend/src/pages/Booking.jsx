import React, { useState, useEffect } from 'react';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';

export default function Booking() {
  const [formData, setFormData] = useState({
    userId: '',
    destination: '',
    date: '',
    numberOfTravelers: 1,
    hotelSelection: ''
  });
  
  const [cities, setCities] = useState([]);
  const [hotels, setHotels] = useState([]);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState({ text: '', type: '' });

  // Load available cities when component mounts
  useEffect(() => {
    // This would fetch cities from your backend in a real implementation
    // For demo purposes, using placeholder data
    setCities(['New York', 'Paris', 'Tokyo', 'London', 'Rome', 'Dubai']);
  }, []);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: name === 'numberOfTravelers' ? parseInt(value) : value
    });

    // If destination changes, fetch hotels for that city
    if (name === 'destination') {
      fetchHotelsForCity(value);
    }
  };

  const fetchHotelsForCity = (city) => {
    if (!city) return;
    
    setLoading(true);
    fetch(`/hotels/${city}`)
      .then(response => {
        if (!response.ok) {
          throw new Error('City not found');
        }
        return response.json();
      })
      .then(data => {
        setHotels(data.hotelNames || []);
        setLoading(false);
      })
      .catch(error => {
        console.error('Error fetching hotels:', error);
        setHotels([]);
        setLoading(false);
        setMessage({ text: `No hotels found for ${city}`, type: 'danger' });
      });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage({ text: '', type: '' });

    fetch('/book', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formData),
    })
      .then(response => {
        if (!response.ok) {
          return response.json().then(err => {
            throw new Error(err.message || 'Booking failed');
          });
        }
        return response.json();
      })
      .then(data => {
        setLoading(false);
        setMessage({ text: 'Booking successful!', type: 'success' });
        // Reset form or redirect
      })
      .catch(error => {
        setLoading(false);
        setMessage({ text: error.message, type: 'danger' });
      });
  };

  return (
    <div>
      <Navbar />

      <div className="container my-5">
        <div className="row justify-content-center">
          <div className="col-md-8">
            <div className="card">
              <div className="card-header bg-primary text-white">
                <h2 className="mb-0">Book Your Travel</h2>
              </div>
              <div className="card-body">
                {message.text && (
                  <div className={`alert alert-${message.type}`} role="alert">
                    {message.text}
                  </div>
                )}

                <form onSubmit={handleSubmit}>
                  <div className="mb-3">
                    <label htmlFor="userId" className="form-label">User ID</label>
                    <input
                      type="text"
                      className="form-control"
                      id="userId"
                      name="userId"
                      value={formData.userId}
                      onChange={handleInputChange}
                      required
                    />
                  </div>

                  <div className="mb-3">
                    <label htmlFor="destination" className="form-label">Destination</label>
                    <select
                      className="form-select"
                      id="destination"
                      name="destination"
                      value={formData.destination}
                      onChange={handleInputChange}
                      required
                    >
                      <option value="">Select a destination</option>
                      {cities.map((city) => (
                        <option key={city} value={city}>
                          {city}
                        </option>
                      ))}
                    </select>
                  </div>

                  <div className="mb-3">
                    <label htmlFor="date" className="form-label">Travel Date</label>
                    <input
                      type="date"
                      className="form-control"
                      id="date"
                      name="date"
                      value={formData.date}
                      onChange={handleInputChange}
                      required
                    />
                  </div>

                  <div className="mb-3">
                    <label htmlFor="numberOfTravelers" className="form-label">Number of Travelers</label>
                    <input
                      type="number"
                      className="form-control"
                      id="numberOfTravelers"
                      name="numberOfTravelers"
                      min="1"
                      value={formData.numberOfTravelers}
                      onChange={handleInputChange}
                      required
                    />
                  </div>

                  <div className="mb-3">
                    <label htmlFor="hotelSelection" className="form-label">Hotel</label>
                    <select
                      className="form-select"
                      id="hotelSelection"
                      name="hotelSelection"
                      value={formData.hotelSelection}
                      onChange={handleInputChange}
                      required
                      disabled={!formData.destination || hotels.length === 0}
                    >
                      <option value="">Select a hotel</option>
                      {hotels.map((hotel) => (
                        <option key={hotel} value={hotel}>
                          {hotel}
                        </option>
                      ))}
                    </select>
                    {loading && formData.destination && (
                      <div className="text-muted mt-2">
                        <small>Loading hotels...</small>
                      </div>
                    )}
                  </div>

                  <div className="d-grid gap-2">
                    <button 
                      type="submit" 
                      className="btn btn-primary"
                      disabled={loading}
                    >
                      {loading ? 'Processing...' : 'Book Now'}
                    </button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>

      <Footer />
    </div>
  );
}