import axios from "axios";
import React, { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import { UserContext } from "../common/UserContext";

export default function Login() {
  const [formData, setFormData] = useState({
    username: "",
    password: ""
  });

  const { setUser } = useContext(UserContext);
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8080/login", formData);
      console.log(response.data);
      setUser(response.data);
      alert("Login successful");
      navigate("/");
    } catch (error) {
      console.log("error while login : ", error.response?.data);
    }
  };

  return (
    <div className="container py-5">
      <div className="row justify-content-center">
        <div className="col-md-8 col-lg-6">
          <div className="card border-0 shadow-lg">
            <div className="card-body p-5">
              {/* Card Header with Logo */}
              <div className="text-center mb-4">
                <div className="bg-primary text-white d-inline-flex align-items-center justify-content-center rounded-circle mb-3" style={{ width: "70px", height: "70px" }}>
                  <i className="bi bi-person-fill" style={{ fontSize: "2rem" }}></i>
                </div>
                <h2 className="fw-bold">Welcome Back</h2>
                <p className="text-muted">Please enter your credentials to login</p>
              </div>
              
              {/* Login Form */}
              <form>
                <div className="form-floating mb-3">
                  <input
                    type="text"
                    className="form-control"
                    id="username"
                    placeholder="Username"
                    name="username"
                    value={formData.username}
                    onChange={handleChange}
                  />
                  <label htmlFor="username">
                    <i className="bi bi-person me-2"></i>Username
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
                  />
                  <label htmlFor="password">
                    <i className="bi bi-lock me-2"></i>Password
                  </label>
                </div>

                <div className="d-flex justify-content-between align-items-center mb-4">
                  <div className="form-check">
                    <input className="form-check-input" type="checkbox" id="rememberMe" />
                    <label className="form-check-label" htmlFor="rememberMe">
                      Remember me
                    </label>
                  </div>
                  <a href="#" className="text-decoration-none">Forgot password?</a>
                </div>

                <button
                  type="submit"
                  className="btn btn-primary w-100 py-3 fw-bold text-uppercase"
                  onClick={handleLogin}
                >
                  Login <i className="bi bi-box-arrow-in-right ms-2"></i>
                </button>
              </form>
              
              {/* Optional: Social login or signup link */}
              <div className="mt-4 text-center">
                <p className="mb-2">Don't have an account? <a href="/register" className="text-decoration-none fw-bold">Sign Up</a></p>
                <div className="d-flex justify-content-center gap-3 mt-3">
                  <button className="btn btn-outline-secondary rounded-circle">
                    <i className="bi bi-google"></i>
                  </button>
                  <button className="btn btn-outline-secondary rounded-circle">
                    <i className="bi bi-facebook"></i>
                  </button>
                  <button className="btn btn-outline-secondary rounded-circle">
                    <i className="bi bi-twitter"></i>
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