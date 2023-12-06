import React, { useState, useEffect, useRef } from 'react';
import './PMstyles.css';
import CurrentBatchInfo from './components/CurrentBatchInfo';
import SensorData from './components/SensorData';
import IngredientsBox from './components/IngredientsBox';
import MaintenanceBox from './components/MaintenanceBox';
import ControlButtons from './components/ControlButtons';
import OEE from './components/OEE';
import TemperatureOverTime from './components/TemperatureOverTime';
import NextBatchConfig from './components/NextBatchConfig';
//import MessageRequest from './components/MessageRequest';


const PM = () => {

    // Return JSX
    return (
        <div className="container">
            <div className="header">
                <h1>Production Manager Dashboard</h1>
            </div>
            <div className="dashboard">


                {/* Temperature over time */}
                <TemperatureOverTime />

                {/* Maintenance Bar */}
                <MaintenanceBox />

                {/* Ingredients */}
                <IngredientsBox />

                {/* Sensor Data */}
                <SensorData />

                {/* Current Batch Info */}
                <CurrentBatchInfo />

                {/* OEE */}
                <OEE />

                {/* Message Request */}


                {/* Next Batch */}
                <NextBatchConfig />
            </div>


            
            <ControlButtons />
        </div>
    );
};

export default PM;