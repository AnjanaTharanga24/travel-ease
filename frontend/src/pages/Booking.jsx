import React, { useState, useEffect, useContext } from 'react';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';
import axios from 'axios';
import { UserContext } from '../common/UserContext';

export default function Booking() {
  const [formData, setFormData] = useState({
    destination: '',
    date: '',
    numberOfTravelers: 1,
    hotelSelection: ''
  });
  
  const [cities, setCities] = useState([]);
  const [hotels, setHotels] = useState([]);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState({ text: '', type: '' });
  const {user} = useContext(UserContext);

  useEffect(() => {
    const fetchCities = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/cities');
        setCities(response.data);
      } catch (error) {
        console.error('Error fetching cities:', error);
        setCities(['Colombo', 'Kandy', 'Galle', 'Nuwara Eliya']); 
      }
    };
    fetchCities();
  }, []);

  useEffect(() => {
    const fetchHotelsForCity = async () => {
      if (!formData.destination) return;
      
      setLoading(true);
      setMessage({ text: '', type: '' });
      
      try {
        const response = await axios.get(`http://localhost:8080/hotels/${formData.destination}`);
        setHotels(response.data.hotelNames || []);
        if (response.data.hotelNames.length === 0) {
          setMessage({ text: `No hotels found for ${formData.destination}`, type: 'info' });
        }
      } catch (error) {
        console.error('Error fetching hotels:', error);
        setHotels([]);
        setMessage({ 
          text: error.response?.data?.message || `Error fetching hotels for ${formData.destination}`,
          type: 'danger' 
        });
      } finally {
        setLoading(false);
      }
    };

    fetchHotelsForCity();
  }, [formData.destination]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: name === 'numberOfTravelers' ? parseInt(value) : value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage({ text: '', type: '' });

    try {

      const requestBody = {
        ...formData,
        userId: user.id
      };

      const response = await axios.post('http://localhost:8080/book', requestBody);
      console.log(response.data);

      setMessage({ text: 'Booking successful!', type: 'success' });
  
      setFormData({
        userId: '',
        destination: '',
        date: '',
        numberOfTravelers: 1,
        hotelSelection: ''
      });
    } catch (error) {
      console.error('Booking error:', error);
      setMessage({ 
        text: error.response?.data?.message || 'Booking failed. Please try again.',
        type: 'danger' 
      });
    } finally {
      setLoading(false);
    }
  };

  const today = new Date().toISOString().split('T')[0];

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
                      min={today}
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
                      max="20"
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
                      disabled={!formData.destination || loading}
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
                    {!loading && formData.destination && hotels.length === 0 && (
                      <div className="text-warning mt-2">
                        <small>No hotels available for this destination</small>
                      </div>
                    )}
                  </div>

                  <div className="d-grid gap-2">
                    <button 
                      type="submit" 
                      className="btn btn-primary"
                      disabled={loading}
                    >
                      {loading ? (
                        <>
                          <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                          Processing...
                        </>
                      ) : (
                        'Book Now'
                      )}
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