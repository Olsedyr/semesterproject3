import React, { useState, useEffect } from 'react';
import axios from 'axios';
import CreateBatchReport from './CreateBatchReport';

// Map of recipe IDs to their names
const recipeTranslation = {
  0: 'Pilsner',
  1: 'Wheat',
  2: 'IPA',
  3: 'Stout',
  4: 'Ale',
  5: 'Alcohol Free',
};

function AllBatchesTable() {
  const [batches, setBatches] = useState([]);

    // Set Time Format
    const formatTime = (dateTimeArray) => {
      const date = new Date(
        // Date.UTC(year, monthIndex, day, hour, minute, second, millisecond)
        Date.UTC( 
          dateTimeArray[0],     // Year
          dateTimeArray[1] - 1, // Month is 0-based
          dateTimeArray[2],     // Day
          dateTimeArray[3],     // Hour
          dateTimeArray[4],     // Minute
          dateTimeArray[5],     // Second
          dateTimeArray[6]/ 1000000    // Nanoseconds to milliseconds
        )
      );
  
      return date.toLocaleString('da-DK');
    };
  
  
    // Fetch data from the backend API
    const fetchData = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/batch'); // Backend endpoint URL
  
        // Sort batches by descending ID
        const sortedLastTenBatches = response.data
          .sort((a, b) => b.id - a.id)
  
        const formattedBatches = sortedLastTenBatches.map((batch) => ({
          ...batch,
          startTime: formatTime(batch.startTime), // Format Start Time
          finishTime: formatTime(batch.startTime),
          recipe: recipeTranslation[batch.recipe], // Use recipe names instead of recipe IDs
        }));
        setBatches(formattedBatches);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
      
    };
  
    useEffect(() => {
      fetchData(); // Initial data fetch on component mount
    }, []); 


  return (

    <div className="container">
      <div className="header">
        <h1>Batch Data</h1>
      </div>
      <div className="dashboard">

        <table className="AllBatchesTable">
          <thead>
            <tr>
              <th>ID</th>
              <th>Recipe</th>
              <th>Quantity</th>
              <th>Speed</th>
              <th>Start Time</th>
              <th>Stop Time</th>
              <th>Produced products (total)</th>
              <th>Acceptable</th>
              <th>Defect</th>
              <th>Temperature mean</th>
              <th>Temperature min</th>
              <th>Temperature max</th>
              <th>Humidity mean</th>
              <th>Humidity min</th>
              <th>Humidity max</th>
              <th>Vibration mean</th>
              <th>Vibration min</th>
              <th>Vibration max</th>
            </tr>
          </thead>
          <tbody>
            {batches.map(batch => (
              <tr key={batch.id}>
                <td>{batch.id}</td>
                <td>{batch.recipe}</td>
                <td>{batch.quantity}</td>
                <td>{batch.machineSpeedActualProductsPerMinute}</td>
                <td>{batch.startTime}</td>
                <td>{batch.finishTime}</td>
                <td>{batch.processedProductsTotal}</td>
                <td>{batch.acceptableProducts}</td>
                <td>{batch.defectProducts}</td>
                <td>{batch.temperatureMean}</td>
                <td>{batch.temperatureLowest}</td>
                <td>{batch.temperatureHighest}</td>
                <td>{batch.humidityMean}</td>
                <td>{batch.humidityLowest}</td>
                <td>{batch.humidityHighest}</td>
                <td>{batch.vibrationMean}</td>
                <td>{batch.vibrationLowest}</td>
                <td>{batch.vibrationHighest}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <CreateBatchReport />
    </div>
  );




};

export default AllBatchesTable;
