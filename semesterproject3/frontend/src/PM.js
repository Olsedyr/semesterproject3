import React, { useState, useEffect, useRef } from 'react';
import Chart from 'chart.js/auto';
import jsPDF from 'jspdf';
import axios from "axios";

const Login = () => {
    const [quantity, setQuantity] = useState(0);
    const [maintenanceProgress, setMaintenanceProgress] = useState(36);
    const [selectedRecipe, setSelectedRecipe] = useState('Pilsner');
    const [batchID, setBatchID] = useState(null);
    const [batchStatus, setBatchStatus] = useState("Not In Progress");
    const [oee, setOEE] = useState(0);
    const [availability, setAvailability] = useState(0.85);
    const [performance, setPerformance] = useState(0.90);
    const [quality, setQuality] = useState(0.95);


    const [productionStartTime, setProductionStartTime] = useState(null);


    const [defectNodeValue, setDefectNodeValue] = useState(null);
    const [producedNodeValue, setProducedNodeValue] = useState(null);
    const [stateCurrentValue, setStateCurrentValue] = useState(null);
    const [recipeCurrentValue, setRecipeCurrentValue] = useState(null);
    const [sensorHumidityValue, setSensorHumidityValue] = useState(null);
    const [sensorTemperatureValue, setSensorTemperatureValue] = useState(null);
    const [sensorVibrationValue, setSensorVibrationValue] = useState(null);
    const [batchIdCurrentValue, setBatchIdCurrentValue] = useState(null);
    const [machineSpeedCurrentValue, setMachineSpeedCurrentValue] = useState(null);
    const machineSpeedInputRef = useRef(null);


    const [quantityCurrentValue, setQuantityCurrentValue] = useState(null);

    const handleSpeedChange = () => {
        const newSpeed = machineSpeedInputRef.current.value;
        setMachineSpeedCurrentValue(newSpeed);
    };

    const handleStartProduction = () => {
        generateRandomBatchID();
        setBatchStatus("In Progress");
        setProductionStartTime(new Date().toLocaleString());
    };


    const generateRandomBatchID = () => {
        const newBatchID = Math.floor(Math.random() * 1000000);
        setBatchIdCurrentValue(newBatchID);
    };

    const handleRecipeChange = (event) => {
        const selectedRecipe = event.target.value;
        setSelectedRecipe(selectedRecipe);
    };

    const handleQuantityChange = (event) => {
        const value = parseInt(event.target.value, 10) || 0;

        // Update the quantity state
        setQuantity(value);

        // Update the quantityCurrentValue state
        setQuantityCurrentValue(value);
    };

    useEffect(() => {
        const maintenanceBar = document.getElementById('maintenanceBar');
        if (maintenanceBar) {
            maintenanceBar.style.width = `${maintenanceProgress}%`;
        }
    }, [maintenanceProgress]);



    const TempOverTimeData = Array.from({ length: 11 }, (_, index) => ({
        x: index,
        y: Math.floor(Math.random() * 101),
    }));

    const calculateOEE = () => {
        const calculatedOEE = availability * performance * quality * 100;
        setOEE(calculatedOEE);
    };

    useEffect(() => {
        calculateOEE();
    }, [availability, performance, quality]);

    useEffect(() => {
        const fetchData = async (url, setValue) => {
            try {
                const response = await axios.get(url);
                setValue(response.data);
            } catch (error) {
                console.error('Error fetching node value:', error);
            }
        };

        const endpoints = [
            { url: 'http://localhost:8080/opcua/productsDefectiveSub/::Program:Cube.Admin.ProdDefectiveCount', setValue: setDefectNodeValue },
            { url: 'http://localhost:8080/opcua/productsAcceptableSub/::Program:Cube.Admin.ProdProcessedCount', setValue: setProducedNodeValue },
            { url: 'http://localhost:8080/opcua/stateCurrentSub/::Program:Cube.Status.StateCurrent', setValue: setStateCurrentValue },
            { url: 'http://localhost:8080/opcua/recipeCurrentSub/::Program:Cube.Admin.Parameter%5B0%5D.Value', setValue: setRecipeCurrentValue },
            { url: 'http://localhost:8080/opcua/sensorHumiditySub/::Program:Cube.Status.Parameter%5B2%5D.Value', setValue: setSensorHumidityValue },
            { url: 'http://localhost:8080/opcua/sensorTemperatureSub/::Program:Cube.Status.Parameter%5B3%5D.Value', setValue: setSensorTemperatureValue },
            { url: 'http://localhost:8080/opcua/sensorVibrationSub/::Program:Cube.Status.Parameter%5B4%5D.Value', setValue: setSensorVibrationValue },
            { url: 'http://localhost:8080/opcua/batchIdCurrentSub/::Program:Cube.Status.Parameter%5B0%5D.Value', setValue: setBatchIdCurrentValue },
            { url: 'http://localhost:8080/opcua/machineSpeedCurrentSub/::Program:Cube.Status.CurMachSpeed', setValue: setMachineSpeedCurrentValue },
            { url: 'http://localhost:8080/opcua/quantityCurrentSub/::Program:Cube.Status.Parameter%5B1%5D.Value', setValue: setQuantityCurrentValue },
        ];

        // Fetch node values when the component mounts
        endpoints.forEach(({ url, setValue }) => fetchData(url, setValue));

        // Fetch node values every second
        const intervalId = setInterval(() => {
            endpoints.forEach(({ url, setValue }) => fetchData(url, setValue));
        }, 1000);

        // Clear the interval on component unmount
        return () => clearInterval(intervalId);
    }, []);


    useEffect(() => {
        const TempOverTimeChart = new Chart(document.getElementById('TempOverTimeChart').getContext('2d'), {
            // ... (chart options)
        });

        return () => {
            TempOverTimeChart.destroy();
        };
    }, []);

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
        Production Start Time: ${productionStartTime}
        Recipe: ${selectedRecipe}
        Quantity Produced: ${producedNodeValue} units
        Amounts To Produce: ${quantityCurrentValue} units
        Products per Minute: ${''}
        Acceptable Product: ${''}
        Defects: ${defectNodeValue} units (${((defectNodeValue / producedNodeValue) * 100).toFixed(2)}%}%)
    `;
        // Add batch information
        pdfDoc.setFontSize(14);
        pdfDoc.setTextColor(33, 33, 33); // Set text color to black
        pdfDoc.text(batchInfoText, 20, 30);
        // Save the PDF with a specific name
        // ... (more PDF content)
        pdfDoc.save('ProductionData.pdf');
    };


    // Return JSX
    return (
        <div className="container">
            <div className="header">
                <h1>Production Manager Dashboard</h1>
            </div>
            <div className="dashboard">

                {/* Production Speed */}
                <div className="info-box">
                    <h2>Production Speed</h2>
                    <div className="box-content">

                        {/* Speed Selector */}
                        <div className="speed-selector">
                            <label htmlFor="speedSelector">Select Speed:</label>
                            <input
                                type="number"
                                id="speedSelector"
                                ref={machineSpeedInputRef}
                                value={machineSpeedCurrentValue === null ? '' : machineSpeedCurrentValue}
                                min="1"
                                max="10"
                                onChange={handleSpeedChange}
                            />
                        </div>
                    </div>
                </div>


                {/* Temperature over time */}
                <div className="info-box">
                    <h2>Temperature Over Time</h2>
                    <div className="box-content">
                        <canvas id="TempOverTimeChart"></canvas>
                    </div>
                </div>



                {/* Quantity */}
                <div className="info-box">
                    <h2>Quantity</h2>
                    <div className="quantity-box">
                        <input
                            type="number"
                            id="quantity"
                            value={quantity}
                            onChange={handleQuantityChange}
                        />
                    </div>
                </div>

                {/* Recipe */}
                <div className="info-box">
                    <h2>Recipe</h2>
                    <div className="box-content" id="recipeBox">
                        <select id="beerRecipe" className="recipe-dropdown" value={selectedRecipe} onChange={handleRecipeChange}>
                            <option value="Pilsner">Pilsner</option>
                            <option value="Classic">Classic</option>
                            <option value="IPA">IPA</option>
                            <option value="Stout">Stout</option>
                        </select>
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
                        <p className="sensor-data-item"><strong>Temperature:</strong> <span className="sensor-data-value">{sensorTemperatureValue !== null ? sensorTemperatureValue : 'Loading...'}</span></p>
                        <p className="sensor-data-item"><strong>Humidity:</strong> <span className="sensor-data-value">{sensorHumidityValue !== null ? sensorHumidityValue : 'Loading...'}</span></p>
                        <p className="sensor-data-item"><strong>Vibration:</strong> <span className="sensor-data-value">{sensorVibrationValue !== null ? sensorVibrationValue : 'Loading...'}</span></p>
                    </div>
                </div>


                {/* Current Batch Info */}
                <div className="info-box">
                    <h2>Current Batch Info</h2>
                    <div className="box-content" id="currentBatchInfoBox">


                        <p><strong>Batch ID:</strong> {batchIdCurrentValue !== null ? batchIdCurrentValue : 'Loading...'}</p>

                        <p><strong>Batch Status:</strong> {batchStatus}</p>

                        <p><strong>Machine Status:</strong> {stateCurrentValue}</p>

                        <p><strong>Production Start Time:</strong> {productionStartTime}</p>

                        <p><strong>Recipe:</strong> {recipeCurrentValue !== null ? recipeCurrentValue : 'Loading...'}</p>

                        <p><strong>Quantity Produced:</strong> {producedNodeValue !== null ? producedNodeValue : 'Loading...'} units</p>

                        <p><strong>Amounts To Produce:</strong> {quantityCurrentValue !== null ? quantityCurrentValue : 'Loading...'}</p>

                        <p><strong>Products pr. Minute:</strong> {}</p>

                        <p><strong>Acceptable Product:</strong> {}</p>

                        <p><strong>Defects:</strong> {defectNodeValue !== null ? defectNodeValue : 'Loading...'} units ({((defectNodeValue / producedNodeValue) * 100).toFixed(2)}%)</p>


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


            <div className="controls">
                <button className="button" onClick={saveProductionDataAsPDF}>Save Production Data</button>
                <button className="button" onClick={handleStartProduction}>Start Production</button>
                <button className="button" onClick={() => {}}>Stop Production</button>
                <button className="button" onClick={() => {}}>Clear</button>
                <button className="button" onClick={() => {}}>Reset</button>
                <button className="button" onClick={() => {}}>Abort</button>
            </div>
        </div>
    );
};

export default Login;
