import React, { useState, useEffect, useContext } from 'react';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';
import axios from 'axios';
import { UserContext } from '../common/UserContext';

export default function Budget() {
  const [formData, setFormData] = useState({
    city: '',
    hotelName: '',
    packageName: '',
    numberOfDays: 1,
    numberOfTravelers: 1
  });
  
  const [cities, setCities] = useState([]);
  const [hotels, setHotels] = useState([]);
  const [packages, setPackages] = useState([]);
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState({ text: '', type: '' });
  const { user } = useContext(UserContext);

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
      if (!formData.city) {
        setHotels([]);
        return;
      }
      
      setLoading(true);
      setMessage({ text: '', type: '' });
      
      try {
        const response = await axios.get(`http://localhost:8080/hotels/${formData.city}`);
        setHotels(response.data.hotelNames || []);
        if (response.data.hotelNames.length === 0) {
          setMessage({ 
            text: `No hotels found for ${formData.city}`, 
            type: 'info' 
          });
        }
      } catch (error) {
        console.error('Error fetching hotels:', error);
        setHotels([]);
        setMessage({ 
          text: error.response?.data?.message || `Error fetching hotels for ${formData.city}`,
          type: 'danger' 
        });
      } finally {
        setLoading(false);
      }
    };

    fetchHotelsForCity();
  }, [formData.city]);

  useEffect(() => {
    const fetchPackagesForCityAndHotel = async () => {
      if (!formData.city || !formData.hotelName) {
        setPackages([]);
        return;
      }
      
      setLoading(true);
      setMessage({ text: '', type: '' });
      
      try {
        const response = await axios.get(`http://localhost:8080/packages/${formData.city}/${formData.hotelName}`);
        setPackages(response.data || []);
        if (response.data.length === 0) {
          setMessage({ 
            text: `No packages found for ${formData.hotelName} in ${formData.city}`, 
            type: 'info' 
          });
        }
      } catch (error) {
        console.error('Error fetching packages:', error);
        setPackages([]);
        setMessage({ 
          text: error.response?.data?.message || `Error fetching packages for ${formData.hotelName} in ${formData.city}`,
          type: 'danger' 
        });
      } finally {
        setLoading(false);
      }
    };

    fetchPackagesForCityAndHotel();
  }, [formData.city, formData.hotelName]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: name.includes('number') ? parseInt(value) : value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage({ text: '', type: '' });
    setResult(null);

    try {
      const requestBody = {
        ...formData,
        userId: user?.id || null
      };

      const response = await axios.post('http://localhost:8080/budget', requestBody);
      setResult(response.data);
      setMessage({ 
        text: 'Budget calculated successfully!', 
        type: 'success' 
      });
    } catch (error) {
      console.error('Budget calculation error:', error);
      setMessage({ 
        text: error.response?.data?.message || 'Budget calculation failed. Please try again.',
        type: 'danger' 
      });
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
            <div className="card shadow">
              <div className="card-header bg-primary text-white">
                <h2 className="h4 mb-0">Travel Budget Calculator</h2>
              </div>
              
              <div className="card-body">
                {message.text && (
                  <div className={`alert alert-${message.type}`}>
                    {message.text}
                  </div>
                )}

                <form onSubmit={handleSubmit}>
                  <div className="row g-3">
                    {/* City Selection */}
                    <div className="col-md-6">
                      <label htmlFor="city" className="form-label">City</label>
                      <select
                        id="city"
                        name="city"
                        className="form-select"
                        value={formData.city}
                        onChange={handleInputChange}
                        required
                      >
                        <option value="">Select a city</option>
                        {cities.map(city => (
                          <option key={city} value={city}>{city}</option>
                        ))}
                      </select>
                    </div>
                    
                    {/* Hotel Name */}
                    <div className="col-md-6">
                      <label htmlFor="hotelName" className="form-label">Hotel Name</label>
                      <select
                        id="hotelName"
                        name="hotelName"
                        className="form-select"
                        value={formData.hotelName}
                        onChange={handleInputChange}
                        required
                        disabled={!formData.city || loading}
                      >
                        <option value="">Select a hotel</option>
                        {hotels.map(hotel => (
                          <option key={hotel} value={hotel}>{hotel}</option>
                        ))}
                      </select>
                      {loading && formData.city && !formData.hotelName && (
                        <div className="text-muted mt-1">
                          <small>Loading hotels...</small>
                        </div>
                      )}
                      {!loading && formData.city && hotels.length === 0 && (
                        <div className="text-warning mt-1">
                          <small>No hotels available for this city</small>
                        </div>
                      )}
                    </div>
                    
                    {/* Package Name */}
                    <div className="col-md-6">
                      <label htmlFor="packageName" className="form-label">Package Name</label>
                      <select
                        id="packageName"
                        name="packageName"
                        className="form-select"
                        value={formData.packageName}
                        onChange={handleInputChange}
                        required
                        disabled={!formData.hotelName || loading}
                      >
                        <option value="">Select a package</option>
                        {packages.map(pkg => (
                          <option key={pkg.id} value={pkg.packageName}>
                            {pkg.packageName} (${pkg.price})
                          </option>
                        ))}
                      </select>
                      {loading && formData.hotelName && (
                        <div className="text-muted mt-1">
                          <small>Loading packages...</small>
                        </div>
                      )}
                      {!loading && formData.hotelName && packages.length === 0 && (
                        <div className="text-warning mt-1">
                          <small>No packages available for this hotel</small>
                        </div>
                      )}
                    </div>
                    
                    {/* Number of Days */}
                    <div className="col-md-3">
                      <label htmlFor="numberOfDays" className="form-label">Number of Days</label>
                      <input
                        type="number"
                        id="numberOfDays"
                        name="numberOfDays"
                        className="form-control"
                        min="1"
                        value={formData.numberOfDays}
                        onChange={handleInputChange}
                        required
                      />
                    </div>
                    
                    {/* Number of Travelers */}
                    <div className="col-md-3">
                      <label htmlFor="numberOfTravelers" className="form-label">Number of Travelers</label>
                      <input
                        type="number"
                        id="numberOfTravelers"
                        name="numberOfTravelers"
                        className="form-control"
                        min="1"
                        value={formData.numberOfTravelers}
                        onChange={handleInputChange}
                        required
                      />
                    </div>
                    
                    {/* Submit Button */}
                    <div className="col-12">
                      <button 
                        type="submit" 
                        className="btn btn-primary"
                        disabled={loading || !formData.packageName}
                      >
                        {loading ? (
                          <>
                            <span className="spinner-border spinner-border-sm me-2" role="status"></span>
                            Calculating...
                          </>
                        ) : 'Calculate Budget'}
                      </button>
                    </div>
                  </div>
                </form>
                
                {/* Results */}
                {result && (
                  <div className="card mt-4 border-primary">
                    <div className="card-header bg-light">
                      <h3 className="h5 mb-0">Budget Calculation Results</h3>
                    </div>
                    <div className="card-body">
                      <div className="row">
                        <div className="col-md-6">
                          <p><strong>City:</strong> {result.city}</p>
                          <p><strong>Hotel:</strong> {result.hotelName}</p>
                          <p><strong>Package:</strong> {result.packageName}</p>
                        </div>
                        <div className="col-md-6">
                          <p><strong>Duration:</strong> {result.numberOfDays} days</p>
                          <p><strong>Travelers:</strong> {result.numberOfTravelers} people</p>
                          <h4 className="mt-3 text-primary">
                            <strong>Total Budget:</strong> ${result.totalBudget.toFixed(2)}
                          </h4>
                        </div>
                      </div>
                    </div>
                  </div>
                )}
              </div>
            </div>
          </div>
        </div>
      </main>
      
      <Footer />
    </div>
  );
}