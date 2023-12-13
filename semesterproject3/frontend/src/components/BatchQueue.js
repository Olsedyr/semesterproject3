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

const BatchQueue = () => {
    const machineSpeedInputRef = useRef(null);
    const [selectedRecipe, setSelectedRecipe] = useState(null);
    const [quantity, setQuantity] = useState(null);
    const [queueQuantity, setQueueQuantity] = useState(null);
    const [machineSpeedActualProductsPerMinute, setSpeed] = useState(null);

    const [machineSpeedNextValue, setMachineSpeedNextValue] = useState(null);
    const [quantityNextValue, setQuantityNextValue] = useState(null);
    const [recipeNextValue, setRecipeNextValue] = useState(null);

    const [batchQueue, setBatchQueue] = useState([]);

    const addToQueue = (selectedRecipe, quantity, machineSpeedActualProductsPerMinute) => {
        const batch = {
            recipe: selectedRecipe,
            quantity: quantity,
            machineSpeedActualProductsPerMinute: machineSpeedActualProductsPerMinute,
        };

        const newRecipe = selectedRecipe;
        const newQuantity = quantity;
        const newSpeed = machineSpeedActualProductsPerMinute;

        if (newQuantity !== null && newSpeed !== null) {
            const Batch = { recipe: newRecipe, quantity: newQuantity, machineSpeedActualProductsPerMinute: newSpeed };

            try {

                (async () => {
                    const response = await axios.post('http://localhost:8080/api/batch/addBatchToQueue', Batch);
                    console.log(response.data);


                    setBatchQueue([...batchQueue, batch]);
                })();
            } catch (error) {
                console.error('Error adding batch to the queue:', error);

            }
        } else {
            console.error('Invalid data for the new batch.');
        }
    };

    const removeFromQueue = async (index) => {
        const updatedQueue = [...batchQueue];
        updatedQueue.splice(index, 1);
        setBatchQueue(updatedQueue);

        try {

            const response = await axios.delete(`http://localhost:8080/api/batch/removeFromQueue/${index}`);


            if (response.status === 200) {
                console.log(response.data);

            } else {
                console.error('Failed to remove batch:', response.data);

            }
        } catch (error) {
            console.error('Error removing batch:', error);

        }
    };

    const fetchQueuedBatchFromQueue = async () => {
        try {
            const response = await axios.get("http://localhost:8080/api/batch/batchQueue");
            setBatchQueue(response.data);
        } catch (error) {
            console.error('Error fetching queued batch from BatchQueue:', error);
            return null;
        }
    };


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
            fetchQueuedBatchFromQueue()
            endpoints.forEach(({ url, setValue }) => fetchData(url, setValue));
        }, 1000);

        return () => clearInterval(intervalId);
    }, []);

    const handleSpeedChange = async (event) => {
        const newSpeed = machineSpeedInputRef.current.value;

    };

    const handleQuantityChange = async (event) => {
        const value = parseInt(event.target.value, 10) || 0;
        setQuantity(value);


    };



    const handleRecipeChange = async (event) => {
        const selectedRecipe = event.target.value;
        setSelectedRecipe(selectedRecipe);

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
                <h2>Batch Queue</h2>
                <div>
                    {batchQueue.length === 0 ? (
                        <p>No batches in the queue.</p>
                    ) : (
                        <ul>
                            {batchQueue.map((batch, index) => (
                                <li key={index} className="batch-item">
                                    <strong> Recipe: </strong> {recipeTranslation[batch.recipe]} - <strong> Quantity:
                                </strong> {batch.quantity} - <strong> Machine Speed:</strong> {batch.machineSpeedActualProductsPerMinute}
                                    <button onClick={() => removeFromQueue(index)}>Remove</button>
                                </li>
                            ))}
                        </ul>
                    )}
                </div>
                <div className="add-to-queue">
                    <h2>Add Batch to Queue:</h2>
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
                <button className="add-to-queue" onClick={() => addToQueue(selectedRecipe, quantity, machineSpeedInputRef.current.value)}>Add To Queue</button>
            </div>
                </div>
            </div>
    );

};



export default BatchQueue;

