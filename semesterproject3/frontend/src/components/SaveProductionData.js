import React, { useState, useEffect } from 'react';
import jsPDF from 'jspdf';
import axios from "axios";

// Map of recipe IDs to their names
const recipeTranslation = {
    0: 'Pilsner',
    1: 'Wheat',
    2: 'IPA',
    3: 'Stout',
    4: 'Ale',
    5: 'Alcohol Free',
};

const SaveProductionData = () => {

    const [productionStartTime, setProductionStartTime] = useState(null);
    const [defectNodeValue, setDefectNodeValue] = useState(null);
    const [producedNodeValue, setProducedNodeValue] = useState(null);
    const [recipeCurrentValue, setRecipeCurrentValue] = useState(null);
    const [batchIdCurrentValue, setBatchIdCurrentValue] = useState(null);
    const [machineSpeedCurrentValue, setMachineSpeedCurrentValue] = useState(null);
    const [machineSpeedCurrentProductsPerMinute, setMachineSpeedCurrentProductsPerMinute] = useState(null);
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
            { url: 'http://localhost:8080/opcua/productsProcessedSub', setValue: setProducedNodeValue },
            { url: 'http://localhost:8080/opcua/productsDefectiveSub', setValue: setDefectNodeValue },
            { url: 'http://localhost:8080/opcua/recipeCurrentSub', setValue: setRecipeCurrentValue },
            { url: 'http://localhost:8080/api/batch/latestBatchId', setValue: setBatchIdCurrentValue }, // not a subscription value, but the latest batch id from the database
            { url: 'http://localhost:8080/opcua/machineSpeedCurrentSub', setValue: setMachineSpeedCurrentValue },
            { url: 'http://localhost:8080/opcua/machineSpeedCurrentProductsPerMinuteSub', setValue: setMachineSpeedCurrentProductsPerMinute },
            { url: 'http://localhost:8080/opcua/quantityCurrentSub', setValue: setQuantityCurrentValue },
            { url: 'http://localhost:8080/api/batch/latestBatchStartTime', setValue: setProductionStartTime },

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



    const getRecipeDescription = () => {
        if (recipeCurrentValue !== null && recipeTranslation[recipeCurrentValue] !== undefined) {
            return recipeTranslation[recipeCurrentValue];
        }
        return 'Loading...';
    };


    const saveProductionDataAsPDF = () => {
        console.log('Button clicked!');
        // Create a new instance of jsPDF
        const pdfDoc = new jsPDF();

        // Set background color
        pdfDoc.setFillColor(0, 0, 0); // Black

        // Title
        pdfDoc.setTextColor(255, 255, 255); // White text
        pdfDoc.setDrawColor(255, 255, 255); // White border
        pdfDoc.setLineWidth(1); // Border width
        pdfDoc.rect(10, 10, 190, 15, 'F'); // Filled rectangle
        pdfDoc.text('Batch Information', 20, 20);

        // Batch Information
        pdfDoc.setTextColor(255, 255, 255); // White text
        pdfDoc.setFontSize(14);

        const batchInfoText = [
            `Batch ID: ${batchIdCurrentValue}`,
            `Production Start Time: ${productionStartTime}`,
            `Recipe: ${recipeCurrentValue} ${getRecipeDescription()}`,
            `Quantity Produced: ${producedNodeValue} units`,
            `Amounts To Produce: ${quantityCurrentValue} units`,
            `Machine speed (Products per Minute): ${machineSpeedCurrentProductsPerMinute}`,
            `Machine speed (normalized 0-100): ${machineSpeedCurrentValue}`,
            `Acceptable Product: ${producedNodeValue - defectNodeValue} units`,
            `Defect products: ${defectNodeValue} units (${((defectNodeValue / producedNodeValue) * 100).toFixed(2)}%)`
        ];

        // Calculate the number of columns
        const numColumns = 2;
        // Calculate the width of each column
        const colWidth = 80; // Adjusted width
        // Calculate the height of each row
        const rowHeight = pdfDoc.getTextDimensions('Sample').h + 2;
        // Set vertical and horizontal spacing
        const verticalSpacing = 10;
        const horizontalSpacing = 20;

        // Add batch information to the grid layout
        batchInfoText.forEach((info, index) => {
            const col = index % numColumns;
            const row = Math.floor(index / numColumns);
            const x = 20 + col * (colWidth + horizontalSpacing); // Adjust the horizontal spacing
            const y = 30 + row * (3 * rowHeight + verticalSpacing); // Adjust the vertical spacing

            // Break lines if text is too long
            const lines = pdfDoc.splitTextToSize(info, colWidth);
            lines.forEach((line, lineIndex) => {
                pdfDoc.text(line, x, y + lineIndex * rowHeight);
            });
        });

        // Save the PDF with a specific name
        pdfDoc.save('ProductionData.pdf');
    };


    // Return JSX
    return (
        
            <button className="button" onClick={saveProductionDataAsPDF}>Save Production Data</button>
        

    );
};

export default SaveProductionData;
