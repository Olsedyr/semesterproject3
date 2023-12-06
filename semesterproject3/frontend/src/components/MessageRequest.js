import React, { useState, useEffect } from 'react';
import axios from 'axios';

const Request = () => {
    const [messages, setMessages] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/requests/all');
                console.log('Fetched data:', response.data);
                setMessages(response.data);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchData();
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
                            {message.selectedOption}
                        </p>
                    ))}
                    {messages.length === 0 && <p className="message">No messages available</p>}
                </div>
            </div>
        </div>
    );
};

export default Request;
