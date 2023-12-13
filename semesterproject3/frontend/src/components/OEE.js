import React, { useState, useEffect, useRef } from 'react';
import axios from "axios";

const OEE = () => {

    const [oee, setOEE] = useState(0);

    const [seconds, setSeconds] = useState(null);
    const [quantity, setQuantity] = useState(null);
    const [speedNormalised, setSpeedNormalised] = useState(null);
    const [speedActual, setSpeedActual] = useState(null);
    const [AcceptableProducts, setAcceptableProducts] = useState(null);
    const [DefectProducts, setDefectProducts] = useState(null);



    const calculateOEE = () => {

        // Availability
        const availability = (((seconds) / (3.8 * quantity)))

        // Performance
        const performance = ((speedNormalised / speedActual) )

        // Quality
        const quality = (((AcceptableProducts - DefectProducts) / quantity))

        // Calculate OEE
        const calculatedOEE = (availability * performance * quality) * 100;

        setOEE(calculatedOEE);
    };


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
            { url: 'http://localhost:8080/api/batch/latestBatchTimeDifference', setValue: setSeconds },
            { url: 'http://localhost:8080/api/batch/latestBatchQuantity', setValue: setQuantity },
            { url: 'http://localhost:8080/api/batch/latestBatchSpeedNormalised', setValue: setSpeedNormalised },
            { url: 'http://localhost:8080/api/batch/latestBatchSpeedActual', setValue: setSpeedActual },
            { url: 'http://localhost:8080/api/batch/latestBatchAcceptableProducts', setValue: setAcceptableProducts },
            { url: 'http://localhost:8080/api/batch/latestBatchDefectProducts', setValue: setDefectProducts},

        ];

        const fetchDataAndCalculateOEE = async () => {
            endpoints.forEach(({ url, setValue }) => fetchData(url, setValue));
            calculateOEE();
        };

        fetchDataAndCalculateOEE()

        // Fetch node values every second
        const intervalId = setInterval(() => {
            endpoints.forEach(({ url, setValue }) => fetchData(url, setValue));
            console.log(oee)
        }, 1000);

        // Clear the interval on component unmount
        return () => clearInterval(intervalId);
    }, [oee]);


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