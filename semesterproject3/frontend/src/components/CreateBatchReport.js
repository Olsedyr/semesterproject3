import React, { useState } from 'react';
import axios from 'axios';
import jsPDF from 'jspdf';

// Map of recipe IDs to their names
const recipeTranslation = {
  0: 'Pilsner',
  1: 'Wheat',
  2: 'IPA',
  3: 'Stout',
  4: 'Ale',
  5: 'Alcohol Free',
};

function CreateBatchReport() {
  const [batchId, setBatchId] = useState('');


  const handleInputChange = (event) => {
    setBatchId(event.target.value);
  };


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
        dateTimeArray[6] / 1000000    // Nanoseconds to milliseconds
      )
    );

    return date.toLocaleString('da-DK');
  };


  const saveProductionDataAsPDF = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/batch/${batchId}`);
      const { data: batch } = response;

      // Create a new instance of jsPDF
      const pdfDoc = new jsPDF();


      // Title
      pdfDoc.setFontSize(20);
      pdfDoc.setTextColor(33, 33, 33); // Set text color to black
      pdfDoc.text('Batch Information', 70, 20, null, null, 'center');

      // Batch Information
      pdfDoc.setTextColor(33, 33, 33); // Set text color back to black
      pdfDoc.setFontSize(12);

      const batchInfoText = [
        `Batch ID: ${batch.id}`,
        `Production Start Time: ${formatTime(batch.startTime)}`,
        `Production Stop Time: ${formatTime(batch.finishTime)}`,
        `Batch status: ${batch.status}`,
        `Recipe ID and Name: ${batch.recipe} - ${recipeTranslation[batch.recipe]}`,
        `Machine speed (Products per Minute): ${batch.machineSpeedActualProductsPerMinute}`,
        `Machine speed (normalized 0-100): ${batch.machineSpeedActualNormalized}`,
        `Amounts To Produce: ${batch.quantity} units`,
        `Products Produced (total): ${batch.processedProductsTotal} units`,
        `Acceptable Product: ${batch.acceptableProducts} units`,
        `Defect products: ${batch.defectProducts} units (${((batch.defectProducts / batch.processedProductsTotal) * 100).toFixed(2)}%)`,
        `Temperature Mean: ${batch.temperatureMean}`,
        `Temperature Min: ${batch.temperatureLowest}`,
        `Temperature Max: ${batch.temperatureHighest}`,
        `Humidity Mean: ${batch.humidityMean}`,
        `Humidity Min: ${batch.humidityLowest}`,
        `Humidity Max: ${batch.humidityHighest}`,
        `Vibration Mean: ${batch.vibrationMean}`,
        `Vibration Min: ${batch.vibrationLowest}`,
        `Vibration Max: ${batch.vibrationHighest}`,

      ];

      // Set the number of columns
      const numColumns = 1;
      // Calculate the width of each column
      const colWidth = pdfDoc.internal.pageSize.width / numColumns;
      // Calculate the height of each row
      const rowHeight = pdfDoc.getTextDimensions('Sample').h + 2;
      // Set vertical and horizontal spacing
      const verticalSpacing = 1;

      // Add batch information to the grid layout
      batchInfoText.forEach((info, index) => {
        const col = index % numColumns;
        const row = Math.floor(index / numColumns);
        const x = col * colWidth;
        const y = 30 + row * (1.5 * rowHeight + verticalSpacing);

        // Break lines if text is too long
        const lines = pdfDoc.splitTextToSize(info, colWidth - 20);
        lines.forEach((line, lineIndex) => {
          pdfDoc.text(line, x + 10, y + lineIndex * rowHeight);
        });
      });

      // Save the PDF with a specific name
      pdfDoc.save(`Batch_Report_${batchId}.pdf`);


    } catch (error) {
      console.error('Error fetching batch data:', error);
    }

  };


  return (
    <div>
      <label htmlFor="batchId">Enter Batch ID:</label>
      <input
        type="text"
        id="batchId"
        value={batchId}
        onChange={handleInputChange}
      />
      <button onClick={saveProductionDataAsPDF}>Download Batch Report</button>
    </div>
  );
}

export default CreateBatchReport;
