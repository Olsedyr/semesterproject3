import React, { useState, useEffect } from 'react';
import axios from 'axios';

// Map of status codes to their descriptions
const statusCodeTranslation = {
    0: 'Deactivated',
    1: 'Clearing',
    2: 'Stopped',
    3: 'Starting',
    4: 'Idle',
    5: 'Suspended',
    6: 'Executing',
    7: 'Stopping',
    8: 'Aborting',
    9: 'Aborted',
    10: 'Holding',
    11: 'Held',
    15: 'Resetting',
    16: 'Completing',
    17: 'Complete',
    18: 'Deactivating',
    19: 'Activating',
  };

  const recipeTranslation = {
    0: 'Pilsner',
    1: 'Wheat',
    2: 'IPA',
    3: 'Stout',
    4: 'Ale',
    5: 'Alcohol Free',
};

const CurrentBatchInfo = () => {
    const [defectNodeValue, setDefectNodeValue] = useState(null);
    const [producedNodeValue, setProducedNodeValue] = useState(null);
    const [stateCurrentValue, setStateCurrentValue] = useState(null);
    const [batchIdCurrentValue, setBatchIdCurrentValue] = useState(null);
    const [machineSpeedCurrentValue, setMachineSpeedCurrentValue] = useState(null);
    const [machineSpeedCurrentProductsPerMinute, setMachineSpeedCurrentProductsPerMinute] = useState(null);
    const [quantityCurrentValue, setQuantityCurrentValue] = useState(null);
    const [recipeCurrentValue, setRecipeCurrentValue] = useState(null);

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
            { url: 'http://localhost:8080/api/batch/latestBatchId', setValue: setBatchIdCurrentValue }, // not a subscription value, but the latest batch id from the database
            { url: 'http://localhost:8080/opcua/machineSpeedCurrentSub', setValue: setMachineSpeedCurrentValue },
            { url: 'http://localhost:8080/opcua/machineSpeedCurrentProductsPerMinuteSub', setValue: setMachineSpeedCurrentProductsPerMinute },
            { url: 'http://localhost:8080/opcua/quantityCurrentSub', setValue: setQuantityCurrentValue },
            { url: 'http://localhost:8080/opcua/recipeCurrentSub', setValue: setRecipeCurrentValue },

        ];



        endpoints.forEach(({ url, setValue }) => fetchData(url, setValue));
        const intervalId = setInterval(() => {
            endpoints.forEach(({ url, setValue }) => fetchData(url, setValue));
        }, 1000);

        return () => clearInterval(intervalId);
    }, []);

    const getStatusDescription = () => {
        if (stateCurrentValue !== null && statusCodeTranslation[stateCurrentValue] !== undefined) {
          return statusCodeTranslation[stateCurrentValue];
        }
        return 'null';
      };

    const getRecipeDescription = () => {
    if (recipeCurrentValue !== null && recipeTranslation[recipeCurrentValue] !== undefined) {
        return recipeTranslation[recipeCurrentValue];
    }
    return 'null';
    };

    return (
        <div className="info-box">
            <h2>Current batch</h2>
            <div className="box-content" id="currentBatchInfoBox">

                <p><strong>Batch ID:</strong> {batchIdCurrentValue !== null ? batchIdCurrentValue : 'Null'}</p>
                <p><strong>Batch Status (Value):</strong> {getStatusDescription()} ({stateCurrentValue})</p>
                <p><strong>Recipe Name (ID):</strong> {getRecipeDescription()} ({recipeCurrentValue !== null ? recipeCurrentValue :  'Loading...'}) </p>
                <p><strong>Total Processed Node Value:</strong> {producedNodeValue !== null ? producedNodeValue : 'Loading...'}</p>
                <p><strong>Acceptable Node Value:</strong> {producedNodeValue - defectNodeValue !== null ? producedNodeValue - defectNodeValue : 'Loading...'}</p>
                <p><strong>Defect Node Value:</strong> {defectNodeValue !== null ? defectNodeValue : 'Loading...'}</p>
                <p><strong>Amounts To Produce:</strong> {quantityCurrentValue !== null ? quantityCurrentValue : 'Loading...'}</p>
                <p><strong>Machine Speed Current Normalized Value (0-100):</strong> {machineSpeedCurrentValue !== null ? machineSpeedCurrentValue : 'Loading...'}</p>
                <p><strong>Machine Speed Current (Products Per Minute):</strong> {machineSpeedCurrentProductsPerMinute !== null ? machineSpeedCurrentProductsPerMinute : 'Loading...'}</p>
                
            </div>
        </div>
    );
};




export default CurrentBatchInfo;
