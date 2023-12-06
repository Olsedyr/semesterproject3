import React, { useState, useEffect } from 'react';
import axios from 'axios';

const Request = () => {
    const [messages, setMessages] = useState([]);

    const fetchData = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/requests/all');
            console.log('Fetched data:', response.data);
            setMessages(response.data);
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    };

    const clearData = async () => {
        try {
            await axios.delete('http://localhost:8080/api/requests/clear');
            console.log('Data cleared successfully');
            // Fetch data again after clearing
            fetchData();
        } catch (error) {
            console.error('Error clearing data:', error);
        }
    };

    useEffect(() => {
        // Fetch data initially
        fetchData();

        // Fetch data every 10 seconds
        const intervalId = setInterval(() => {
            fetchData();
        }, 10000);

        // Clear the interval on component unmount
        return () => clearInterval(intervalId);
    }, []);

    // Return JSX
    return (
        <div className="info-box">
            <h2>Machine Request: </h2>
            <div className="box-content">
                {/* Message Box */}
                <div className="message-box">
                    {/* Render messages here */}
                    {messages.map((message, index) => (
                        <p className="message" key={index}>
                            <strong>Request {index + 1}:</strong> JD sent request to&nbsp;
                            {message.selectedOption}
                        </p>
                    ))}
                    {messages.length === 0 && <p className="message">No messages available</p>}
                </div>
                {/* Clear Button */}
                <button className="button" onClick={clearData}>
                    Clear Data
                </button>
            </div>
        </div>
    );
};

export default Request;