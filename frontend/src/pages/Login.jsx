import axios from "axios";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Login() {

    const [formData, setFormData] = useState({
      username: '',
      password: ''
    })

    const navigate = useNavigate();

    const handleChange = (e) => {
      const { name, value } = e.target;
      setFormData({
        ...formData,
        [name]: value
      })
    }

    const handleLogin = async (e) =>{
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/login', formData);
            console.log(response.data);
            alert('Login successful');
            navigate('/')
        } catch (error) {
            console.log('error while login : ' , error.response?.data);
        }
    }

  return (
    <div className="container mt-5 text-start p-5">
      <form>
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

        <button
          type="submit"
          className="btn btn-primary"
          onClick={handleLogin}
        >
          Login
        </button>
      </form>
    </div>
  );
}
