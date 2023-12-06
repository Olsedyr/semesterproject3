import React, { useState, useEffect, useRef } from 'react';
import Chart from 'chart.js/auto';
import axios from "axios";


const TemperatureOverTime = () => {


    const TempOverTimeData = Array.from({ length: 11 }, (_, index) => ({
        x: index,
        y: Math.floor(Math.random() * 101),
    }));

    useEffect(() => {
        const TempOverTimeChart = new Chart(document.getElementById('TempOverTimeChart').getContext('2d'), {
            // ... (chart options)
        });

        return () => {
            TempOverTimeChart.destroy();
        };
    }, []);



    // Return JSX
    return (
                <div className="info-box">
                    <h2>Temperature Over Time</h2>
                    <div className="box-content">
                        <canvas id="TempOverTimeChart"></canvas>
                    </div>
                </div>
            
    );
};

export default TemperatureOverTime;