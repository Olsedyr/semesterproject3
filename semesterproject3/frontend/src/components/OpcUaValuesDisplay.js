import React, { useState, useEffect } from 'react';
import axios from 'axios';

const OpcUaValuesDisplay = () => {
  const [defectNodeValue, setDefectNodeValue] = useState(null);
  const [producedNodeValue, setProducedNodeValue] = useState(null);
  const [stateCurrentValue, setStateCurrentValue] = useState(null);
  const [recipeCurrentValue, setRecipeCurrentValue] = useState(null);
  const [sensorHumidityValue, setSensorHumidityValue] = useState(null);
  const [sensorTemperatureValue, setSensorTemperatureValue] = useState(null);
  const [sensorVibrationValue, setSensorVibrationValue] = useState(null);
  const [batchIdCurrentValue, setBatchIdCurrentValue] = useState(null);
  const [machineSpeedCurrentValue, setMachineSpeedCurrentValue] = useState(null);
  const [quantityCurrentValue, setQuantityCurrentValue] = useState(null);

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

  return (
    <div>
      <h2>OPC UA Node Values</h2>
      <div>
        <p>Produced Node Value: {producedNodeValue !== null ? producedNodeValue : 'Loading...'}</p>
        <p>Defect Node Value: {defectNodeValue !== null ? defectNodeValue : 'Loading...'}</p>
        <p>State Current Value: {stateCurrentValue !== null ? stateCurrentValue : 'Loading...'}</p>
        <p>Recipe Current Value: {recipeCurrentValue !== null ? recipeCurrentValue : 'Loading...'}</p>
        <p>Batch ID Current Value: {batchIdCurrentValue !== null ? batchIdCurrentValue : 'Loading...'}</p>
        <p>Machine Speed Current Value: {machineSpeedCurrentValue !== null ? machineSpeedCurrentValue : 'Loading...'}</p>
        <p>Quantity Current Value: {quantityCurrentValue !== null ? quantityCurrentValue : 'Loading...'}</p>
        <p>Sensor Humidity Value: {sensorHumidityValue !== null ? sensorHumidityValue : 'Loading...'}</p>
        <p>Sensor Temperature Value: {sensorTemperatureValue !== null ? sensorTemperatureValue : 'Loading...'}</p>
        <p>Sensor Vibration Value: {sensorVibrationValue !== null ? sensorVibrationValue : 'Loading...'}</p>
      </div>
    </div>
  );
};

export default OpcUaValuesDisplay;
