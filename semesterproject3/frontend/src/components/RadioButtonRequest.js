import React, { useState } from "react";
import axios from "axios";

const RadioRequest = () => {
    const [selectedOption, setSelectedOption] = useState(null);

    const handleRadioChange = (event) => {
        console.log('Selected Option:', event.target.value);
        setSelectedOption(event.target.value);
    };

    // Function to handle send request button click
    const handleSendRequest = async (e) => {
        e.preventDefault();
        try {
            if (!selectedOption) {
                alert('Please select an option before sending a request.');
                return;
            }

            console.log('Request payload:', { selectedOption }); // Log the payload

            await axios.post('http://localhost:8080/api/requests/save', { selectedOption }, { timeout: 5000 });

            console.log('Request sent successfully');
        } catch (error) {
            console.error('Error sending request:', error);
        }
    };

    return (
        <div className="info-box">
            <h2>Request Options</h2>
            <div className="box-content">
                <form>
                    <div>
                        <input
                            type="radio"
                            id="option1"
                            value="Start Machine"
                            checked={selectedOption === 'Start Machine'}
                            onChange={handleRadioChange}
                        />
                        <label htmlFor="option1">Start Machine</label>
                    </div>
                    <div>
                        <input
                            type="radio"
                            id="option2"
                            value="Stop Machine"
                            checked={selectedOption === 'Stop Machine'}
                            onChange={handleRadioChange}
                        />
                        <label htmlFor="option2">Stop Machine</label>
                    </div>
                    <div>
                        <input
                            type="radio"
                            id="option3"
                            value="Start Maintenance"
                            checked={selectedOption === 'Start Maintenance'}
                            onChange={handleRadioChange}
                        />
                        <label htmlFor="option3">Start Maintenance</label>
                    </div>
                    <button className="button request-button" onClick={handleSendRequest}>Send Request</button>
                </form>
            </div>
        </div>
    );
};

export default RadioRequest;