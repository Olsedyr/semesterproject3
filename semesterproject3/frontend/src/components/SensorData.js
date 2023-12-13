import React, { useState, useEffect } from 'react';
import axios from 'axios';


const SensorData = () => {
    const [sensorHumidityValue, setSensorHumidityValue] = useState(null);
    const [sensorTemperatureValue, setSensorTemperatureValue] = useState(null);
    const [sensorVibrationValue, setSensorVibrationValue] = useState(null);


    useEffect(() => {
        const fetchData = async (url, setValue) => {
            try {
                const response = await axios.get(url);
                setValue(response.data);
            } catch (error) {
                console.error('Error fetching node value:', error);
            }
        };

        const stopMachine = async () => {
            try {
                await axios.post("http://localhost:8080/machinecontroller/stop")
                console.log('Stopped the machine');
            } catch (error) {
                console.error('Error stopping the machine:', error);
            }
        };

        const endpoints = [
            { url: 'http://localhost:8080/opcua/sensorHumiditySub', setValue: setSensorHumidityValue },
            { url: 'http://localhost:8080/opcua/sensorTemperatureSub', setValue: setSensorTemperatureValue },
            {
                url: 'http://localhost:8080/opcua/sensorVibrationSub',
                setValue: (value) => {
                    setSensorVibrationValue(value);
                    if (value > 3) {
                        stopMachine();
                    }
                }
            },
        ];

        endpoints.forEach(({ url, setValue }) => fetchData(url, setValue));
        const intervalId = setInterval(() => {
            endpoints.forEach(({ url, setValue }) => fetchData(url, setValue));
        }, 1000);

        return () => clearInterval(intervalId);
    }, []);


    return (
        <div className="info-box">
            <h2>Sensor Data</h2>
            <div className="box-content" id="sensorDataBox">
                <p className="sensor-data-item"><strong>Temperature:</strong> <span className="sensor-data-value">{sensorTemperatureValue !== null ? sensorTemperatureValue : 'Loading...'}</span></p>
                <p className="sensor-data-item"><strong>Humidity:</strong> <span className="sensor-data-value">{sensorHumidityValue !== null ? sensorHumidityValue : 'Loading...'}</span></p>
                <p className="sensor-data-item"><strong>Vibration:</strong> <span className="sensor-data-value">{sensorVibrationValue !== null ? sensorVibrationValue : 'Loading...'}</span></p>
            </div>
        </div>
    );
};

export default SensorData;