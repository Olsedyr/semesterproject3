import React, { useState, useRef } from 'react';
import axios from 'axios';
import SaveProductionData from './SaveProductionData';

const ControlButtons = () => {
  

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
      // Stopping the machine
      const machineResponse = await axios.post('http://localhost:8080/api/machine/stop');
      const machineStopped = machineResponse.data;
  
      if (machineStopped) {
        const batchStopResponse = await axios.put('http://localhost:8080/api/batch/updateStatus?status=stopped');
        console.log('Batch Stopped:', batchStopResponse.data);
  
        // Update the finish time of the batch using the updateFinishTime endpoint
        const finishTimeResponse = await axios.put('http://localhost:8080/api/batch/updateFinishTime');
        console.log('Finish Time Updated:', finishTimeResponse.data);
      } else {
        console.log('Machine did not stop, check the machine state.');
      }
    } catch (error) {
      console.error('Error stopping machine:', error);
    }
  };

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




  return (
      <div className="controls">
        <SaveProductionData/>
        <button className="button" onClick={handleStartProduction}>Start Production</button>
        <button className="button" onClick={handleStopProduction}>Stop Production</button>
        <button className="button" onClick={handleClearProduction}>Clear</button>
        <button className="button" onClick={handleResetProduction}>Reset</button>
        <button className="button" onClick={handleAbortProduction}>Abort</button>
      </div>
    
  );
};

export default ControlButtons;
