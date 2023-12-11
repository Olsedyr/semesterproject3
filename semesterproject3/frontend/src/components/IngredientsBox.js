import React, { useState, useEffect } from 'react';
import axios from "axios";


const IngredientsBox = () => {

    const [ingredientBarley, setIngredientBarley] = useState(null);
    const [ingredientHops, setIngredientHops] = useState(null);
    const [ingredientMalt, setIngredientMalt] = useState(null);
    const [ingredientWheat, setIngredientWheat] = useState(null);
    const [ingredientYeast, setIngredientYeast] = useState(null);


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

            { url: 'http://localhost:8080/opcua/ingredientBarley', setValue: setIngredientBarley },
            { url: 'http://localhost:8080/opcua/ingredientHops', setValue: setIngredientHops },
            { url: 'http://localhost:8080/opcua/ingredientMalt', setValue: setIngredientMalt },
            { url: 'http://localhost:8080/opcua/ingredientWheat', setValue: setIngredientWheat },
            { url: 'http://localhost:8080/opcua/ingredientYeast', setValue: setIngredientYeast },
        ];



        endpoints.forEach(({ url, setValue }) => fetchData(url, setValue));
        const intervalId = setInterval(() => {
            endpoints.forEach(({ url, setValue }) => fetchData(url, setValue));
        }, 1000);

        return () => clearInterval(intervalId);
    }, []);

    const calculateBarleyPercentage = () => {
        if (ingredientBarley !== null) {
            const maxValue = 35000; // Maximum value
            const percentage = (ingredientBarley / maxValue) * 100;
            return percentage.toFixed(2);
        }
        return 0;
    };
    const calculateHopsPercentage = () => {
        if (ingredientHops !== null) {
            const maxValue = 35000;
            const percentage = (ingredientHops / maxValue) * 100;
            return percentage.toFixed(2);
        }
        return 0;
    };
    const calculateMaltPercentage = () => {
        if (ingredientMalt !== null) {
            const maxValue = 35000;
            const percentage = (ingredientMalt / maxValue) * 100;
            return percentage.toFixed(2);
        }
        return 0;
    };
    const calculateWheatPercentage = () => {
        if (ingredientWheat !== null) {
            const maxValue = 35000;
            const percentage = (ingredientWheat / maxValue) * 100;
            return percentage.toFixed(2);
        }
        return 0;
    };
    const calculateYeastPercentage = () => {
        if (ingredientYeast !== null) {
            const maxValue = 35000;
            const percentage = (ingredientYeast / maxValue) * 100;
            return percentage.toFixed(2);
        }
        return 0;
    };


    return (
        <div className="info-box">
            <h2>Ingredients</h2>
            <div className="box-content ingredients-box">
                <div className="ingredient-progress">
                    <h3>Barley</h3>
                    <div className="progress-bar-container">
                        <div className="progress-bar" style={{ width: `${calculateBarleyPercentage()}%` }}></div>
                        <div className="progress-labels">
                            <span>0%</span>
                            <span>100%</span>
                        </div>
                    </div>
                </div>
                <div className="ingredient-progress">
                    <h3>Hops</h3>
                    <div className="progress-bar-container">
                    <div className="progress-bar" style={{ width: `${calculateHopsPercentage()}%` }}></div>
                        <div className="progress-labels">
                            <span>0%</span>
                            <span>100%</span>
                        </div>
                    </div>
                </div>
                <div className="ingredient-progress">
                    <h3>Malt</h3>
                    <div className="progress-bar-container">
                    <div className="progress-bar" style={{ width: `${calculateMaltPercentage()}%` }}></div>
                        <div className="progress-labels">
                            <span>0%</span>
                            <span>100%</span>
                        </div>
                    </div>
                </div>
                <div className="ingredient-progress">
                    <h3>Wheat</h3>
                    <div className="progress-bar-container">
                    <div className="progress-bar" style={{ width: `${calculateWheatPercentage()}%` }}></div>
                        <div className="progress-labels">
                            <span>0%</span>
                            <span>100%</span>
                        </div>
                    </div>
                </div>
                <div className="ingredient-progress">
                    <h3>Yeast</h3>
                    <div className="progress-bar-container">
                    <div className="progress-bar" style={{ width: `${calculateYeastPercentage()}%` }}></div>
                        <div className="progress-labels">
                            <span>0%</span>
                            <span>100%</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default IngredientsBox;