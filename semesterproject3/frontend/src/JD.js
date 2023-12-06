// PM.js
import React, { useState, useEffect } from 'react';
import './JDstyles.css';
import Chart from 'chart.js/auto';
import jsPDF from 'jspdf';
import axios from "axios";

const Login = () => {
    // State for quantity and maintenance progress
    const [quantity, setQuantity] = useState(0);
    const [maintenanceProgress, setMaintenanceProgress] = useState(36);
    const [selectedRecipe, setSelectedRecipe] = useState('Pilsner'); // Set the default recipe
    const [batchID, setBatchID] = useState(null);
    const [batchStatus, setBatchStatus] = useState("Not In Progress");
    const [productionStartTime, setProductionStartTime] = useState("Production Not Started");
    const [oee, setOEE] = useState(0);
    const [availability, setAvailability] = useState(0.85); // Placeholder value, replace with actual availability
    const [performance, setPerformance] = useState(0.90); // Placeholder value, replace with actual performance
    const [quality, setQuality] = useState(0.95); // Placeholder value, replace with actual quality


    const [currentProductionSpeed, setCurrentProductionSpeed] = useState(0);


    const [selectedOption, setSelectedOption] = useState('Start Machine');

    // State for sensor data
    const [sensorData, setSensorData] = useState({
        temperature: 0.0,
        batchID: 0.0,
        produced: 0.0,
        humidity: 0.0,
        amountToProduce: 0.0,
        acceptableProduct: 0.0,
        vibration: 0.0,
        productsPerMinute: 0.0,
        defectProduct: 0.0,
    });

    // Function to handle quantity change


    // Effect to update the width of the maintenance progress
    useEffect(() => {
        const maintenanceBar = document.getElementById('maintenanceBar');
        if (maintenanceBar) {
            maintenanceBar.style.width = `${maintenanceProgress}%`;
        }
    }, [maintenanceProgress]);



    // Effect to fetch and update sensor data
    useEffect(() => {
        // Fetch and update sensor data from your backend or any other source
        // For now, just setting dummy data
        const dummySensorData = {
            temperature: 25.5,
            batchID: 123,
            produced: 500.0,
            humidity: 45.0,
            amountToProduce: quantity,
            acceptableProduct: 480.0,
            vibration: 0.8,
            productsPerMinute: 50.0,
            defectProduct: 20.0,
        };

        setSensorData(dummySensorData);
    }, [quantity]); // Empty dependency array to run this effect once on mount



    // Placeholder data for the temp over time data
    const TempOverTimeData = Array.from({ length: 11 }, (_, index) => ({
        x: index,
        y: Math.floor(Math.random() * 101),
    }));



    // Function to calculate and update OEE
    const calculateOEE = () => {
        // Calculate OEE
        const calculatedOEE = availability * performance * quality * 100;

        // Update the state
        setOEE(calculatedOEE);
    };

    // Effect to recalculate OEE when relevant data changes
    useEffect(() => {
        calculateOEE();
    }, [availability, performance, quality]);





    // Effect to create and destroy the production speed chart
    useEffect(() => {
        const TempOverTimeChart = new Chart(document.getElementById('TempOverTimeChart').getContext('2d'), {
            type: 'line',
            data: {
                datasets: [{
                    label: 'Temperature Over Time',
                    data: TempOverTimeData,
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 2,
                    fill: false,
                }],
            },
            options: {
                scales: {
                    x: {
                        type: 'linear',
                        position: 'bottom',
                        min: 0,
                        max: 10,
                        ticks: {
                            callback: (value) => `${value}min`,
                        },
                    },
                    y: {
                        type: 'linear',
                        position: 'left',
                        min: 0,
                        max: 100,
                        ticks: {
                            callback: (value) => `${value}°C`,
                        },
                    },
                },
            },
        });

        return () => {
            TempOverTimeChart.destroy();
        };
    }, []); // Empty dependency array to run this effect once on mount





    const currentBatchInfo = {
        batchStatus: 'In Progress',
        productionStartTime: new Date(),
        recipe: 'Pilsner',
        quantityProduced: 250,
        defects: 5,
    };




    const handleRadioChange = (event) => {
        console.log('Selected Option:', event.target.value);
        setSelectedOption(event.target.value);
    };

    // Function to handle send request button click
    const handleSendRequest = async (e) => {
        e.preventDefault()
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



    const saveProductionDataAsPDF = () => {
        console.log('Button clicked!');

        // Create a new instance of jsPDF
        const pdfDoc = new jsPDF();

        // Title
        pdfDoc.setFontSize(20);
        pdfDoc.setTextColor(33, 33, 33); // Set text color to black
        pdfDoc.text('Batch Information', 20, 20);

        // Batch Information
        const batchInfoText = `
        Production Start Time: ${currentBatchInfo.productionStartTime.toLocaleString()}
        Recipe: ${selectedRecipe}
        Quantity Produced: ${currentBatchInfo.quantityProduced} units
        Amounts To Produce: ${quantity} units
        Products per Minute: ${currentBatchInfo.productsPerMinute}
        Acceptable Product: ${currentBatchInfo.acceptableProduct}
        Defects: ${currentBatchInfo.defects} units (${((currentBatchInfo.defects / currentBatchInfo.quantityProduced) * 100).toFixed(2)}%)
    `;

        // Add batch information
        pdfDoc.setFontSize(14);
        pdfDoc.setTextColor(33, 33, 33); // Set text color to black
        pdfDoc.text(batchInfoText, 20, 30);

        // Save the PDF with a specific name
        pdfDoc.save('ProductionData.pdf');
    };


    // Return JSX
    return (
        <div className="container">
            <div className="header">
                <h1>Junior Developer Dashboard</h1>
            </div>
            <div className="dashboard">

                {/* Production Speed */}
                <div className="info-box">
                    <h2>Production Speed</h2>
                    <div className="box-content">
                        {/* Display the current production speed */}
                        <p><strong>Current Production Speed:</strong> {currentProductionSpeed}</p>
                    </div>
                </div>


                {/* Temperature over time */}
                <div className="info-box">
                    <h2>Temperature Over Time</h2>
                    <div className="box-content">
                        <canvas id="TempOverTimeChart"></canvas>
                    </div>
                </div>



                {/* Maintenance Bar */}
                <div className="info-box">
                    <h2>Maintenance Bar</h2>
                    <div className="maintenance-bar" id="maintenanceBar">
                        <div id="maintenanceBar" className="maintenance-progress"></div>
                    </div>
                </div>

                {/* Ingredients */}
                <div className="info-box">
                    <h2>Ingredients</h2>
                    <div className="box-content ingredients-box">
                        <div className="ingredient-progress">
                            <h3>Barley</h3>
                            <div className="progress-bar-container">
                                <div className="progress-bar" style={{ width: '30%' }}></div>
                                <div className="progress-labels">
                                    <span>0%</span>
                                    <span>100%</span>
                                </div>
                            </div>
                        </div>
                        <div className="ingredient-progress">
                            <h3>Hops</h3>
                            <div className="progress-bar-container">
                                <div className="progress-bar" style={{ width: '30%' }}></div>
                                <div className="progress-labels">
                                    <span>0%</span>
                                    <span>100%</span>
                                </div>
                            </div>
                        </div>
                        <div className="ingredient-progress">
                            <h3>Malt</h3>
                            <div className="progress-bar-container">
                                <div className="progress-bar" style={{ width: '30%' }}></div>
                                <div className="progress-labels">
                                    <span>0%</span>
                                    <span>100%</span>
                                </div>
                            </div>
                        </div>
                        <div className="ingredient-progress">
                            <h3>Wheat</h3>
                            <div className="progress-bar-container">
                                <div className="progress-bar" style={{ width: '30%' }}></div>
                                <div className="progress-labels">
                                    <span>0%</span>
                                    <span>100%</span>
                                </div>
                            </div>
                        </div>
                        <div className="ingredient-progress">
                            <h3>Yeast</h3>
                            <div className="progress-bar-container">
                                <div className="progress-bar" style={{ width: '30%' }}></div>
                                <div className="progress-labels">
                                    <span>0%</span>
                                    <span>100%</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                {/* Sensor Data */}
                <div className="info-box">
                    <h2>Sensor Data</h2>
                    <div className="box-content" id="sensorDataBox">
                        <p className="sensor-data-item"><strong>Temperature:</strong> <span className="sensor-data-value">{sensorData.temperature}</span></p>
                        <p className="sensor-data-item"><strong>Humidity:</strong> <span className="sensor-data-value">{sensorData.humidity}</span></p>
                        <p className="sensor-data-item"><strong>Vibration:</strong> <span className="sensor-data-value">{sensorData.vibration}</span></p>
                    </div>
                </div>


                {/* Current Batch Info */}
                <div className="info-box">
                    <h2>Current Batch Info</h2>
                    <div className="box-content" id="currentBatchInfoBox">

                        <p><strong>Batch ID:</strong> {batchID}</p>


                        <p><strong>Batch Status:</strong> {batchStatus}</p>

                        <p><strong>Production Start Time:</strong> {productionStartTime}</p>

                        <p><strong>Recipe:</strong> {selectedRecipe}</p>

                        <p><strong>Quantity Produced:</strong> {currentBatchInfo.quantityProduced} units</p>

                        <p><strong>Amounts To Produce:</strong> {quantity}</p>

                        <p><strong>Products pr. Minute:</strong> {currentBatchInfo.productsPerMinute}</p>

                        <p><strong>Acceptable Product:</strong> {currentBatchInfo.acceptableProduct}</p>

                        <p><strong>Defects:</strong> {currentBatchInfo.defects} units ({((currentBatchInfo.defects / currentBatchInfo.quantityProduced) * 100).toFixed(2)}%)</p>


                    </div>
                </div>

                {/* OEE */}
                <div className="info-box">
                    <h2>Overall Equipment Effectiveness (OEE)</h2>
                    <div className="box-content">
                        <p><strong>OEE:</strong> {oee.toFixed(2)}%</p>
                    </div>
                </div>
            </div>

            {/* Info-box with Radio Buttons and Send Request Button */}
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
                            <label htmlFor="option1">Stop Machine</label>
                        </div>
                        <div>
                            <input
                                type="radio"
                                id="option2"
                                value="Stop Machine"
                                checked={selectedOption === 'Stop Machine'}
                                onChange={handleRadioChange}
                            />
                            <label htmlFor="option2">Start Machine</label>
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






    <div className="controls">
                <button className="button" onClick={saveProductionDataAsPDF}>Save Production Data</button>
            </div>
        </div>
    );
};

export default Login;
