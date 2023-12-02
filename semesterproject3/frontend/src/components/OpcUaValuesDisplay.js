import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';

const OpcUaValuesDisplay = () => {
  const [defectNodeValue, setDefectNodeValue] = useState(null);
  const [producedNodeValue, setProducedNodeValue] = useState(null);
  const [stateCurrentValue, setStateCurrentValue] = useState(null);
  const [recipeCurrentValue, setRecipeCurrentValue] = useState(null);
  const [recipeNextValue, setRecipeNextValue] = useState(null);
  const [sensorHumidityValue, setSensorHumidityValue] = useState(null);
  const [sensorTemperatureValue, setSensorTemperatureValue] = useState(null);
  const [sensorVibrationValue, setSensorVibrationValue] = useState(null);
  const [batchIdCurrentValue, setBatchIdCurrentValue] = useState(null);
  const [machineSpeedCurrentValue, setMachineSpeedCurrentValue] = useState(null);
  const [machineSpeedCurrentProductsPerMinute, setMachineSpeedCurrentProductsPerMinute] = useState(null);
  const [machineSpeedNextValue, setMachineSpeedNextValue] = useState(null);
  const [quantityCurrentValue, setQuantityCurrentValue] = useState(null);
  const [quantityNextValue, setQuantityNextValue] = useState(null);
  const [ingredientBarley, setIngredientBarley] = useState(null);
  const [ingredientHops, setIngredientHops] = useState(null);
  const [ingredientMalt, setIngredientMalt] = useState(null);
  const [ingredientWheat, setIngredientWheat] = useState(null);
  const [ingredientYeast, setIngredientYeast] = useState(null);
  const [maintenanceCounter, setMaintenanceCounter] = useState(null);
  const [maintenanceState, setMaintenanceState] = useState(null);
  const [maintenanceTrigger, setMaintenanceTrigger] = useState(null);
  const machineSpeedInputRef = useRef(null);
  const [selectedRecipe, setSelectedRecipe] = useState(null);

  const [quantity, setQuantity] = useState(null);



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
      { url: 'http://localhost:8080/opcua/recipeNextSub', setValue: setRecipeNextValue },
      { url: 'http://localhost:8080/opcua/sensorHumiditySub', setValue: setSensorHumidityValue },
      { url: 'http://localhost:8080/opcua/sensorTemperatureSub', setValue: setSensorTemperatureValue },
      { url: 'http://localhost:8080/opcua/sensorVibrationSub', setValue: setSensorVibrationValue },
      { url: 'http://localhost:8080/api/batch/latestBatchId', setValue: setBatchIdCurrentValue }, // not a subscription value, but the latest batch id from the database
      { url: 'http://localhost:8080/opcua/machineSpeedCurrentSub', setValue: setMachineSpeedCurrentValue },
      { url: 'http://localhost:8080/opcua/machineSpeedCurrentProductsPerMinuteSub', setValue: setMachineSpeedCurrentProductsPerMinute },
      { url: 'http://localhost:8080/opcua/machineSpeedNextSub', setValue: setMachineSpeedNextValue },
      { url: 'http://localhost:8080/opcua/quantityCurrentSub', setValue: setQuantityCurrentValue },
      { url: 'http://localhost:8080/opcua/quantityNextSub', setValue: setQuantityNextValue },
      { url: 'http://localhost:8080/opcua/ingredientBarley', setValue: setIngredientBarley },
      { url: 'http://localhost:8080/opcua/ingredientHops', setValue: setIngredientHops },
      { url: 'http://localhost:8080/opcua/ingredientMalt', setValue: setIngredientMalt },
      { url: 'http://localhost:8080/opcua/ingredientWheat', setValue: setIngredientWheat },
      { url: 'http://localhost:8080/opcua/ingredientYeast', setValue: setIngredientYeast },
      { url: 'http://localhost:8080/opcua/maintenanceCounter', setValue: setMaintenanceCounter },
      { url: 'http://localhost:8080/opcua/maintenanceState', setValue: setMaintenanceState },
      { url: 'http://localhost:8080/opcua/maintenanceTrigger', setValue: setMaintenanceTrigger },
    ];



    endpoints.forEach(({ url, setValue }) => fetchData(url, setValue));
    const intervalId = setInterval(() => {
      endpoints.forEach(({ url, setValue }) => fetchData(url, setValue));
    }, 1000);

    return () => clearInterval(intervalId);
  }, []);

  // Machine commands
  const sendMachineCommand = async (command) => {
    try {
      const response = await axios.post(`http://localhost:8080/api/machine/${command}`);
      console.log(response.data);
    } catch (error) {
      console.error(`Error ${command}ing machine:`, error);
    }
  };

  const handleResetProduction = () => sendMachineCommand('reset');
  //const handleStartProduction = () => sendMachineCommand('start');
  //const handleStopProduction = () => sendMachineCommand('stop');
  //const handleAbortProduction = () => sendMachineCommand('abort');
  const handleClearProduction = () => sendMachineCommand('clear');


  const handleStartProduction = async () => {
    try {
      // Starting the machine
      const machineResponse = await axios.post('http://localhost:8080/api/machine/start');
      const machineStarted = machineResponse.data;

      if (machineStarted) {
        // Payload for the batch creation
        const batchPayload = {};

        // Adding a batch to the database, but only if the machine started successfully
        const batchResponse = await axios.post('http://localhost:8080/api/batch', batchPayload);

        // Reset speed, recipe and quantity for next batch
        setSelectedRecipe(""); // Reset selected recipe
        machineSpeedInputRef.current.value = null; // Reset machine speed input field
        setQuantity(null); // Reset quantity
        // Update the value attribute of the quantity input field
        const quantityInput = document.getElementById('quantity');
        if (quantityInput) {
          quantityInput.value = ''; // Reset quantity input field
        }

        console.log('Batch added:', batchResponse.data); // Process the response data as needed
      } else {
        console.log('Machine did not start, check the machine state.');
      }
    } catch (error) {
      console.error('Error starting machine:', error);
    }
  };

  const handleStopProduction = async () => {
    try {
      // Starting the machine
      const machineResponse = await axios.post('http://localhost:8080/api/machine/stop');
      const machineStopped = machineResponse.data;

      if (machineStopped) {
        // Change the status of the latest batch to stopped
        const batchResponse = await axios.put('http://localhost:8080/api/batch/updateStatus?status=stopped');
        console.log('Batch Stopped:', batchResponse.data);
      } else {
        console.log('Machine did not stop, check the machine state.');
      }
    } catch (error) {
      console.error('Error stopping machine:', error);
    }
  };

  const handleAbortProduction = async () => {
    try {
      // Starting the machine
      const machineResponse = await axios.post('http://localhost:8080/api/machine/abort');
      const machineStopped = machineResponse.data;

      if (machineStopped) {
        // Change the status of the latest batch to stopped
        const batchResponse = await axios.put('http://localhost:8080/api/batch/updateStatus?status=aborted');
        console.log('Batch Stopped:', batchResponse.data);
      } else {
        console.log('Machine did not abort, check the machine state.');
      }
    } catch (error) {
      console.error('Error aborting machine:', error);
    }
  };

  // Update Database with subscription values
  const updateDatabase = () => {
    fetch('http://localhost:8080/opcua/saveBatch', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({})
    })
      .then(response => {
        // Handle the response if needed
        console.log('Save Batch Response:', response);
      })
      .catch(error => {
        // Handle errors if the request fails
        console.error('Error saving batch:', error);
      });

    fetch('http://localhost:8080/opcua/saveBatchStatusFinished', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({})
    })
      .then(response => {
        // Handle the response if needed
        console.log('Save Batch Status Finished Response:', response);
      })
      .catch(error => {
        // Handle errors if the request fails
        console.error('Error saving batch status finished:', error);
      });
  };

  setInterval(updateDatabase, 10000);


  const handleSpeedChange = async (event) => {
    const newSpeed = machineSpeedInputRef.current.value;
    try {
      // Make a POST request to change the speed value
      const response = await axios.post(`http://localhost:8080/api/machine/writeMachineSpeedValue/${newSpeed}`);
      console.log('Speed of next batch changed successfully:', response.data);
      // Handle the response or update state if needed
    } catch (error) {
      console.error('Error changing speed:', error);
      // Handle errors
    }
  };


  const handleRecipeChange = async (event) => {
    const selectedRecipe = event.target.value;
    setSelectedRecipe(selectedRecipe);
    try {
      // Make a POST request to change the recipe value
      const response = await axios.post(`http://localhost:8080/api/machine/writeRecipeValue/${selectedRecipe}`);
      console.log('Recipe changed successfully:', response.data);
      // Handle the response or update state if needed
    } catch (error) {
      console.error('Error changing recipe:', error);
      // Handle errors
    }
  };

  const handleQuantityChange = async (event) => {
    const value = parseInt(event.target.value, 10) || 0;

    // Update the quantity state
    setQuantity(value);

    try {
      // Make a POST request to change the quantity value
      const response = await axios.post(`http://localhost:8080/api/machine/writeQuantityValue/${value}`);
      console.log('Quantity of next batch changed successfully:', response.data);
      // Handle the response or update state if needed
    } catch (error) {
      console.error('Error changing quantity:', error);
      // Handle errors
    }
  };





  return (

    <div>
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
                min="0"
                max="600"
                onChange={handleSpeedChange}
              />
            </div>
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
              <option value="">Select Recipe</option> {/* Default option */}
              <option value="0">Pilsner</option> {/* Value corresponds to OPC UA recipe IDs */}
              <option value="1">Wheat</option>
              <option value="2">IPA</option>
              <option value="3">Stout</option>
              <option value="4">Ale</option>
              <option value="5">Alcohol Free</option>
            </select>
          </div>
        </div>



      </div>


      <h2>OPC UA Node Values</h2>
      <div>
        <p>Total Processed Node Value: {producedNodeValue !== null ? producedNodeValue : 'Loading...'}</p>
        <p>Acceptable Node Value: {producedNodeValue - defectNodeValue !== null ? producedNodeValue - defectNodeValue : 'Loading...'}</p>
        <p>Defect Node Value: {defectNodeValue !== null ? defectNodeValue : 'Loading...'}</p>
        <p>State Current Value: {stateCurrentValue !== null ? stateCurrentValue : 'Loading...'}</p>
        <p>Recipe Current Value: {recipeCurrentValue !== null ? recipeCurrentValue : 'Loading...'}</p>
        <p>Recipe Next Value: {recipeNextValue !== null ? recipeNextValue : 'Loading...'}</p>
        <p>Batch ID Current Value: {batchIdCurrentValue !== null ? batchIdCurrentValue : 'null'}</p>
        <p>Machine Speed Current Normalized Value (0-100): {machineSpeedCurrentValue !== null ? machineSpeedCurrentValue : 'Loading...'}</p>
        <p>Machine Speed Current (Products Per Minute): {machineSpeedCurrentProductsPerMinute !== null ? machineSpeedCurrentProductsPerMinute : 'Loading...'}</p>
        <p>Machine Speed Next Value (Products Per Minute): {machineSpeedNextValue !== null ? machineSpeedNextValue : 'Loading...'}</p>
        <p>Quantity Current Value: {quantityCurrentValue !== null ? quantityCurrentValue : 'Loading...'}</p>
        <p>Quantity Next Value: {quantityNextValue !== null ? quantityNextValue : 'Loading...'}</p>
        <p>Sensor Humidity Value: {sensorHumidityValue !== null ? sensorHumidityValue : 'Loading...'}</p>
        <p>Sensor Temperature Value: {sensorTemperatureValue !== null ? sensorTemperatureValue : 'Loading...'}</p>
        <p>Sensor Vibration Value: {sensorVibrationValue !== null ? sensorVibrationValue : 'Loading...'}</p>
        <p>Ingredient Barley: {ingredientBarley !== null ? ingredientBarley : 'Loading...'}</p>
        <p>Ingredient Hops: {ingredientHops !== null ? ingredientHops : 'Loading...'}</p>
        <p>Ingredient Malt: {ingredientMalt !== null ? ingredientMalt : 'Loading...'}</p>
        <p>Ingredient Wheat: {ingredientWheat !== null ? ingredientWheat : 'Loading...'}</p>
        <p>Ingredient Yeast: {ingredientYeast !== null ? ingredientYeast : 'Loading...'}</p>
        <p>Maintenance Counter: {maintenanceCounter !== null ? maintenanceCounter : 'Loading...'}</p>
        <p>Maintenance State: {maintenanceState !== null ? maintenanceState : 'Loading...'}</p>
        <p>Maintenance Trigger: {maintenanceTrigger !== null ? maintenanceTrigger : 'Loading...'}</p>
      </div>
      <div className="controls">
        <button className="button" onClick={() => { }}>Save Production Data</button>
        <button className="button" onClick={handleStartProduction}>Start Production</button>
        <button className="button" onClick={handleStopProduction}>Stop Production</button>
        <button className="button" onClick={handleClearProduction}>Clear</button>
        <button className="button" onClick={handleResetProduction}>Reset</button>
        <button className="button" onClick={handleAbortProduction}>Abort</button>
      </div>
    </div>
  );
};

export default OpcUaValuesDisplay;