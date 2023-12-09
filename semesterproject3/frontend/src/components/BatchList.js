import { useState, useEffect } from 'react';
import axios from 'axios';

// Map of recipe IDs to their names
const recipeTranslation = {
  0: 'Pilsner',
  1: 'Wheat',
  2: 'IPA',
  3: 'Stout',
  4: 'Ale',
  5: 'Alcohol Free',
};

function BatchList() {
  const [batches, setBatches] = useState([]);

  
  const formatStartTime = (dateTimeArray) => {
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

    return date.toLocaleString('da-DK', {
      
      /* year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit',
      hour12: false, // Use 24-hour format */
    });
  };

  


  // fetch data from the backend API
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/batch'); // backend endpoint URL

        // Sort batches by descending ID and get the last 10
        const sortedLastTenBatches = response.data
          .sort((a, b) => b.id - a.id)
          .slice(0, 10);

        
        const formattedBatches = sortedLastTenBatches.map((batch) => ({
          ...batch,
          startTime: formatStartTime(batch.startTime), // format Start Time

          recipe: recipeTranslation[batch.recipe], // Use recipe names instead of recipe IDs
        }));
        setBatches(formattedBatches);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData();
  }, []);




  return (

    <div className="info-box">
      <h2>Previous 10 Batches</h2>
      <div className="box-content">

        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Recipe</th>
              <th>Quantity</th>
              <th>Speed</th>
              <th>Start Time</th>
              <th>Acceptable</th>
              <th>Defect</th>
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
                <td>{batch.acceptableProducts}</td>
                <td>{batch.defectProducts}</td>
              </tr>
            ))}
          </tbody>
        </table>


      </div>
    </div>




  );
}

export default BatchList;