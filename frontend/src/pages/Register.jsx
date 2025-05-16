import axios from "axios";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Register() {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    username: "",
    password: ""
  });
  
  const [step, setStep] = useState(1);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const nextStep = (e) => {
    e.preventDefault();
    setStep(2);
  };

  const prevStep = (e) => {
    e.preventDefault();
    setStep(1);
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const response = await axios.post("http://localhost:8080/register", formData);
      console.log(response.data);
      setLoading(false);
      alert("Registration successful");
      navigate("/login"); // Assuming you have a login route
    } catch (error) {
      setLoading(false);
      console.log("error while register : ", error.response?.data);
    }
  };

  const renderStepIndicator = () => {
    return (
      <div className="d-flex justify-content-center mb-4">
        <div className="d-flex align-items-center">
          <div className={`rounded-circle d-flex align-items-center justify-content-center ${step === 1 ? "bg-primary" : "bg-success"}`} 
               style={{ width: "40px", height: "40px", color: "white" }}>
            <i className={step === 1 ? "bi bi-person" : "bi bi-check-lg"}></i>
          </div>
          <div className={`progress mx-2`} style={{ width: "100px", height: "5px" }}>
            <div className={`progress-bar ${step === 2 ? "bg-primary" : "bg-success"}`} role="progressbar" style={{ width: step === 1 ? "50%" : "100%" }}></div>
          </div>
          <div className={`rounded-circle d-flex align-items-center justify-content-center ${step === 2 ? "bg-primary" : "bg-light text-muted"}`} 
               style={{ width: "40px", height: "40px", color: step === 2 ? "white" : "inherit" }}>
            <i className="bi bi-key"></i>
          </div>
        </div>
      </div>
    );
  };

  return (
    <div className="container py-5">
      <div className="row justify-content-center">
        <div className="col-md-8 col-lg-6">
          <div className="card border-0 shadow-lg">
            <div className="card-body p-5">
              {/* Card Header with Logo */}
              <div className="text-center mb-4">
                <div className="bg-primary text-white d-inline-flex align-items-center justify-content-center rounded-circle mb-3" 
                     style={{ width: "70px", height: "70px" }}>
                  <i className="bi bi-person-plus-fill" style={{ fontSize: "2rem" }}></i>
                </div>
                <h2 className="fw-bold">Create Account</h2>
                <p className="text-muted">Join our community today</p>
              </div>
              
              {renderStepIndicator()}
              
              {/* Registration Form */}
              <form>
                {step === 1 ? (
                  <>
                    <div className="form-floating mb-3">
                      <input
                        type="text"
                        className="form-control"
                        id="name"
                        placeholder="Full Name"
                        name="name"
                        value={formData.name}
                        onChange={handleChange}
                        required
                      />
                      <label htmlFor="name">
                        <i className="bi bi-person-badge me-2"></i>Full Name
                      </label>
                    </div>

                    <div className="form-floating mb-3">
                      <input
                        type="email"
                        className="form-control"
                        id="email"
                        placeholder="Email Address"
                        name="email"
                        value={formData.email}
                        onChange={handleChange}
                        required
                      />
                      <label htmlFor="email">
                        <i className="bi bi-envelope me-2"></i>Email Address
                      </label>
                    </div>

                    <button
                      type="button"
                      className="btn btn-primary w-100 py-3 fw-bold text-uppercase mt-3"
                      onClick={nextStep}
                    >
                      Continue <i className="bi bi-arrow-right ms-2"></i>
                    </button>
                  </>
                ) : (
                  <>
                    <div className="form-floating mb-3">
                      <input
                        type="text"
                        className="form-control"
                        id="username"
                        placeholder="Username"
                        name="username"
                        value={formData.username}
                        onChange={handleChange}
                        required
                      />
                      <label htmlFor="username">
                        <i className="bi bi-at me-2"></i>Username
                      </label>
                    </div>

                    <div className="form-floating mb-4">
                      <input
                        type="password"
                        className="form-control"
                        id="password"
                        placeholder="Password"
                        name="password"
                        value={formData.password}
                        onChange={handleChange}
                        required
                      />
                      <label htmlFor="password">
                        <i className="bi bi-lock me-2"></i>Password
                      </label>
                      <div className="password-strength mt-2">
                        <div className="progress" style={{ height: "8px" }}>
                          <div 
                            className={`progress-bar ${formData.password.length > 8 ? "bg-success" : formData.password.length > 4 ? "bg-warning" : "bg-danger"}`} 
                            style={{ width: `${Math.min(formData.password.length * 10, 100)}%` }}
                          ></div>
                        </div>
                        <small className="text-muted">Password strength: {formData.password.length === 0 ? "Not set" : formData.password.length > 8 ? "Strong" : formData.password.length > 4 ? "Medium" : "Weak"}</small>
                      </div>
                    </div>

                    <div className="form-check mb-4">
                      <input className="form-check-input" type="checkbox" id="termsCheck" />
                      <label className="form-check-label" htmlFor="termsCheck">
                        I agree to the <a href="#" className="text-decoration-none">Terms of Service</a> and <a href="#" className="text-decoration-none">Privacy Policy</a>
                      </label>
                    </div>

                    <div className="d-flex gap-2 mt-3">
                      <button
                        type="button"
                        className="btn btn-outline-secondary py-3 fw-bold w-50"
                        onClick={prevStep}
                      >
                        <i className="bi bi-arrow-left me-2"></i> Back
                      </button>
                      <button
                        type="submit"
                        className="btn btn-primary py-3 fw-bold text-uppercase w-50"
                        onClick={handleRegister}
                        disabled={loading}
                      >
                        {loading ? (
                          <>
                            <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                            Processing...
                          </>
                        ) : (
                          <>
                            Register <i className="bi bi-check-circle ms-2"></i>
                          </>
                        )}
                      </button>
                    </div>
                  </>
                )}
              </form>
              
              {/* Optional: Login link */}
              <div className="mt-4 text-center">
                <p>Already have an account? <a href="/login" className="text-decoration-none fw-bold">Login</a></p>
              </div>
              
              {/* Optional: Social registration */}
              <div className="my-4">
                <div className="text-center mb-3">
                  <span className="bg-white px-2 position-relative" style={{ zIndex: 1 }}>Or register with</span>
                  <hr className="position-relative" style={{ marginTop: "-0.8rem" }} />
                </div>
                <div className="d-flex justify-content-center gap-3">
                  <button className="btn btn-outline-primary flex-grow-1">
                    <i className="bi bi-google me-2"></i>Google
                  </button>
                  <button className="btn btn-outline-primary flex-grow-1">
                    <i className="bi bi-facebook me-2"></i>Facebook
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}