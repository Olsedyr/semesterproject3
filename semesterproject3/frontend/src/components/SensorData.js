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

        

    const endpoints = [
        { url: 'http://localhost:8080/opcua/sensorHumiditySub', setValue: setSensorHumidityValue },
        { url: 'http://localhost:8080/opcua/sensorTemperatureSub', setValue: setSensorTemperatureValue },
        { url: 'http://localhost:8080/opcua/sensorVibrationSub', setValue: setSensorVibrationValue}
    ];
    const checkVibration = async () => {
        const vibrationValue = parseFloat(sensorVibrationValue);
        if (!isNaN(vibrationValue) && vibrationValue > 3 ) { // Check if it's a valid number and greater than 3
            await handleAbortProduction();
        }
        if (!isNaN(vibrationValue) && vibrationValue < -3 ) {
            await handleAbortProduction();
        }
    };
     const fetchDataAndUpdate = () => {
        endpoints.forEach(({ url, setValue }) => fetchData(url, setValue));
        checkVibration(); // Call checkVibration within fetchDataAndUpdate
    };

    fetchDataAndUpdate(); // Initial call to update sensor values and check vibration

    const intervalId = setInterval(fetchDataAndUpdate, 1000); // Call fetchDataAndUpdate at intervals

    return () => clearInterval(intervalId);
}, [sensorVibrationValue]); // Include sensorVibrationValue in the dependency array

    const handleAbortProduction = async () => {
        try {
          // Aborting the machine
          const machineResponse = await axios.post('http://localhost:8080/api/machine/abort');
          const machineAborted = machineResponse.data;
      
          if (machineAborted) {
            const batchAbortResponse = await axios.put('http://localhost:8080/api/batch/updateStatus?status=aborted');
            console.log('Batch aborted:', batchAbortResponse.data);
      
            // Update the finish time of the batch using the updateFinishTime endpoint
            const finishTimeResponse = await axios.put('http://localhost:8080/api/batch/updateFinishTime');
            console.log('Finish Time Updated:', finishTimeResponse.data);
          } else {
            console.log('Machine did not abort, check the machine state.');
          }
        } catch (error) {
          console.error('Error aborting machine:', error);
        }
      };
      ;

    

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