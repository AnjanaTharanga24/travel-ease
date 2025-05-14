import axios from "axios";
import React, { useState } from "react";

export default function Register() {
  
    const [formData, setFormData] = useState({
      name: '',
      email: '',
      username: '',
      password: ''
    });

    const handleChange = (e) => {
      const { name, value } = e.target;
      setFormData({
        ...formData,
        [name]: value
      });
    };

    const handleRegister = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/register', formData);
            console.log(response.data);
            alert('Registration successful');
        } catch (error) {
            console.log('error while register : ' , error.response?.data);
        }
    }

  return (
    <div className="container mt-5 text-start p-5">
      <form>
        <div className="form-group mb-3">
          <label className="mb-2">name</label>
          <input
            type="text"
            className="form-control"
            placeholder="Enter name"
            name="name"
            value={formData.name}
            onChange={handleChange}
          />
        </div>

        <div className="form-group mb-3">
          <label className="mb-2">Email address</label>
          <input
            type="email"
            className="form-control"
            placeholder="Enter email"
            name="email"
            value={formData.email}
            onChange={handleChange}
          />
        </div>

        <div className="form-group mb-3">
          <label className="mb-2">username</label>
          <input
            type="text"
            className="form-control"
            placeholder="Enter username"
            name="username"
            value={formData.username}
            onChange={handleChange}
          />
        </div>

        <div className="form-group mb-3">
          <label className="mb-2">password</label>
          <input
            type="text"
            className="form-control"
            placeholder="Enter password"
            name="password"
            value={formData.password}
            onChange={handleChange}
          />
        </div>

        <button type="submit" className="btn btn-primary" onClick={handleRegister}>
          Submit
        </button>
      </form>
    </div>
  );
}
