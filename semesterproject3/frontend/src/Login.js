import React, { useState } from 'react';
import LoginPageBackground from './LoginPageBackground.png';
import axios from 'axios';


const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = async () => {
        try {
            const response = await axios.post('http://localhost:8080/api/users/login', {
                username,
                password,
            });

            if (response && response.data) {
                // Handle successful login, e.g., store the token in local storage
                console.log('Login successful', response.data);
                return "GUI/PMPage";
            } else {
                // Handle unexpected response format
                console.error('Unexpected response format', response);
            }

        } catch (error) {
            // Handle login error
            if (error.response) {
                // The request was made and the server responded with a status code
                // that falls out of the range of 2xx
                console.error('Login failed', error.response.data);
            } else if (error.request) {
                // The request was made but no response was received
                console.error('No response received', error.request);
            } else {
                // Something happened in setting up the request that triggered an Error
                console.error('Error during request setup', error.message);
            }
        }

        console.log(`Logging in with username: ${username} and password: ${password}`);
    };


    return (
        <div style={{
            background: `url("${LoginPageBackground}")`,
            backgroundSize: 'cover',
            backgroundPosition: 'center',
            backgroundRepeat: 'no-repeat',
            fontFamily: 'Arial, sans-serif',
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            justifyContent: 'center',
            height: '100vh'
        }}>
            <header>
                <h1>Company Name</h1>
            </header>
            <form style={{
                maxWidth: '400px',
                padding: '20px',
                textAlign: 'center',
                backgroundColor: 'rgba(255, 255, 255, 0.8)',
                borderRadius: '10px',
                marginTop: '60px'
            }}>
                <label style={{display: 'block', textAlign: 'left'}}>
                    <span style={{marginBottom: '5px', display: 'block'}}>Username:</span>
                    <input type="text" placeholder="Username" onChange={(e) => setUsername(e.target.value)} style={{
                        width: '100%',
                        boxSizing: 'border-box',
                        padding: '10px',
                        margin: '10px 0',
                        border: '1px solid #ccc',
                        borderRadius: '5px'
                    }}/>
                </label>
                <label style={{display: 'block', textAlign: 'left'}}>
                    <span style={{marginBottom: '5px', display: 'block'}}>Password:</span>
                    <input type="password" placeholder="Password" onChange={(e) => setPassword(e.target.value)}
                           style={{
                               width: '100%',
                               boxSizing: 'border-box',
                               padding: '10px',
                               margin: '10px 0',
                               border: '1px solid #ccc',
                               borderRadius: '5px'
                           }}/>
                </label>
                <button type="button" onClick={handleLogin} style={{
                    backgroundColor: '#007bff',
                    color: '#fff',
                    border: 'none',
                    padding: '10px 20px',
                    borderRadius: '5px',
                    cursor: 'pointer'
                }}>Sign in
                </button>
                <p><a href="#">Forgot password? Click Here</a></p>
                <img src={LoginPageBackground} alt="Login Background" style={{display: 'none'}}/>
            </form>
        </div>
    );
};

export default Login;

