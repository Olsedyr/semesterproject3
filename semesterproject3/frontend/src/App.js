// Remove this line
import logo from './logo.svg';

import React from 'react';
import Login from './Login';
import PM from './PM'

function App() {
    return (
        <div>
            <PM />
        </div>
    );
}

export default App;













/*
function BatchList() {
  const [batches, setBatches] = useState([]);

  // fetch data from the backend API
  useEffect(() => {
      const fetchData = async () => {
          try {
            const response = await axios.get('http://localhost:8080/api/batch'); // backend endpoint URL
              setBatches(response.data);
          } catch (error) {
              console.error('Error fetching data:', error);
          }
      };

      fetchData();
  }, []);

  return (
    <div>
      <h2>Batches</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Recipe</th>
            <th>Quantity</th>
            <th>Machine Speed</th>
            <th>Start Time</th>
          </tr>
        </thead>
        <tbody>
          {batches.map(batch => (
            <tr key={batch.id}>
              <td>{batch.id}</td>
              <td>{batch.recipe}</td>
              <td>{batch.quantity}</td>
              <td>{batch.machineSpeed}</td>
              <td>{batch.startTime}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default BatchList;
*/
