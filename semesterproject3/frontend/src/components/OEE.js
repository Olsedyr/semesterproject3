import React, { useState, useEffect, useRef } from 'react';
import axios from "axios";

const OEE = () => {

    const [oee, setOEE] = useState(0);
    const [availability, setAvailability] = useState(0.85);
    const [performance, setPerformance] = useState(0.90);
    const [quality, setQuality] = useState(0.95);
    const [productionStartTime, setProductionStartTime] = useState(null);
    const [latestBatch, setLatestBatch] = useState(null);
    const [defectNodeValue, setDefectNodeValue] = useState(null);
    const [producedNodeValue, setProducedNodeValue] = useState(null);
    const [stateCurrentValue, setStateCurrentValue] = useState(null);
    const [recipeCurrentValue, setRecipeCurrentValue] = useState(null);
    const [machineSpeedCurrentValue, setMachineSpeedCurrentValue] = useState(null);
    const [machineSpeedCurrentProductsPerMinute, setMachineSpeedCurrentProductsPerMinute] = useState(null);
    const [quantityCurrentValue, setQuantityCurrentValue] = useState(null);
    const machineSpeedInputRef = useRef(null);
    const [selectedRecipe, setSelectedRecipe] = useState(null);
    const [quantity, setQuantity] = useState(null);


    const calculateOEE = () => {
        // Availability
        const availability = stateCurrentValue === "Operating" ? 1 : 0;

        // Performance
        const idealProductionRate = quantityCurrentValue;
        const actualProductionRate = producedNodeValue;
        const performance = actualProductionRate / idealProductionRate;

        // Quality
        const goodUnits = producedNodeValue - defectNodeValue;
        const quality = goodUnits / producedNodeValue;

        // Calculate OEE
        const calculatedOEE = availability * performance * quality * 100;
        setOEE(calculatedOEE);
    };

    useEffect(() => {
        calculateOEE();
    }, [stateCurrentValue, quantityCurrentValue, producedNodeValue, defectNodeValue]);

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
            { url: 'http://localhost:8080/opcua/productsProcessedSub', setValue: setProducedNodeValue },
            { url: 'http://localhost:8080/opcua/productsDefectiveSub', setValue: setDefectNodeValue },
            { url: 'http://localhost:8080/opcua/stateCurrentSub', setValue: setStateCurrentValue },
            { url: 'http://localhost:8080/opcua/recipeCurrentSub', setValue: setRecipeCurrentValue },
            { url: 'http://localhost:8080/opcua/machineSpeedCurrentSub', setValue: setMachineSpeedCurrentValue },
            { url: 'http://localhost:8080/opcua/machineSpeedCurrentProductsPerMinuteSub', setValue: setMachineSpeedCurrentProductsPerMinute },
            { url: 'http://localhost:8080/opcua/quantityCurrentSub', setValue: setQuantityCurrentValue },
            { url: 'http://localhost:8080/api/batch/latestBatch', setValue: setLatestBatch },

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


    // Return JSX
    return (
        <div className="info-box">
            <h2>Overall Equipment Effectiveness (OEE)</h2>
            <div className="box-content">
                <p><strong>OEE:</strong> {oee.toFixed(2)}%</p>
            </div>
        </div>
    );
};

export default OEE;