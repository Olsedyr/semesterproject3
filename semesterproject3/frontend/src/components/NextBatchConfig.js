import axios from "axios";
import React, { useState, useEffect, useRef } from 'react';

// Map of recipe IDs to their names
const recipeTranslation = {
    0: 'Pilsner',
    1: 'Wheat',
    2: 'IPA',
    3: 'Stout',
    4: 'Ale',
    5: 'Alcohol Free',
};

const NextBatchConfig = () => {
    const machineSpeedInputRef = useRef(null);
    const [selectedRecipe, setSelectedRecipe] = useState(null);
    const [quantity, setQuantity] = useState(null);

    const [machineSpeedNextValue, setMachineSpeedNextValue] = useState(null);
    const [quantityNextValue, setQuantityNextValue] = useState(null);
    const [recipeNextValue, setRecipeNextValue] = useState(null);


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
            { url: 'http://localhost:8080/opcua/recipeNextSub', setValue: setRecipeNextValue },
            { url: 'http://localhost:8080/opcua/machineSpeedNextSub', setValue: setMachineSpeedNextValue },
            { url: 'http://localhost:8080/opcua/quantityNextSub', setValue: setQuantityNextValue },
        ];

        endpoints.forEach(({ url, setValue }) => fetchData(url, setValue));
        const intervalId = setInterval(() => {
            endpoints.forEach(({ url, setValue }) => fetchData(url, setValue));
        }, 1000);

        return () => clearInterval(intervalId);
    }, []);

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

    const handleQuantityChange = async (event) => {
        const value = parseInt(event.target.value, 10) || 0;
        setQuantity(value);

        try {
            const response = await axios.post(`http://localhost:8080/api/machine/writeQuantityValue/${value}`);
            console.log('Quantity of next batch changed successfully:', response.data);
        } catch (error) {
            console.error('Error changing quantity:', error);
        }
    };



    const handleRecipeChange = async (event) => {
        const selectedRecipe = event.target.value;
        setSelectedRecipe(selectedRecipe);

        try {
            const response = await axios.post(`http://localhost:8080/api/machine/writeRecipeValue/${selectedRecipe}`);
            console.log('Recipe changed successfully:', response.data);
        } catch (error) {
            console.error('Error changing recipe:', error);
        }
    };



    const getRecipeDescription = () => {
        if (recipeNextValue !== null && recipeTranslation[recipeNextValue] !== undefined) {
            return recipeTranslation[recipeNextValue];
        }
        return 'Loading...';
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        const speedValue = machineSpeedInputRef.current.value;
        const quantityValue = event.target.elements.quantity.value;
        const recipeValue = event.target.elements.beerRecipe.value;

        // Call the respective functions here with the form values
        handleSpeedChange(speedValue);
        handleQuantityChange(quantityValue);
        handleRecipeChange(recipeValue);
    };

    return (
        <div className="info-box">
            <h2>Configure next batch</h2>
            <h5>Set the values for the next batch:</h5>
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
                
                {/* Quantity */}
                <div className="quantity-box">
                    <label htmlFor="speedSelector">Quantity:</label>
                    <input
                        type="number"
                        id="quantity"
                        value={quantity}
                        onChange={handleQuantityChange}
                    />
                </div>
               
                {/* Recipe */}
                <div className="box-content" id="recipeBox">
                    <label htmlFor="speedSelector">Recipe:</label>
                    <select
                        id="beerRecipe"
                        className="recipe-dropdown"
                        value={selectedRecipe}
                        onChange={handleRecipeChange}
                    >
                        <option value="">Select Recipe</option>
                        <option value="0">Pilsner</option>
                        <option value="1">Wheat</option>
                        <option value="2">IPA</option>
                        <option value="3">Stout</option>
                        <option value="4">Ale</option>
                        <option value="5">Alcohol Free</option>
                    </select>
                </div>
                <br />
                <h5>Next batch values read from the machine:</h5>
                <p>Next Batch Machine Speed (Products Per Minute): {machineSpeedNextValue !== null ? machineSpeedNextValue : 'Loading...'}</p>
                <p>Next Batch Quantity : {quantityNextValue !== null ? quantityNextValue : 'Loading...'}</p>
                <p>Next Batch Recipe: {getRecipeDescription()}</p>
            </div>
        </div>
    );
    
};



export default NextBatchConfig;

