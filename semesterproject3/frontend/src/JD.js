import React, { useState, useEffect, useRef } from 'react';
import './JDstyles.css';
import CurrentBatchInfo from './components/CurrentBatchInfo';
import SensorData from './components/SensorData';
import IngredientsBox from './components/IngredientsBox';
import MaintenanceBox from './components/MaintenanceBox';
import ControlButtons from './components/ControlButtons';
import OEE from './components/OEE';
import TemperatureOverTime from './components/TemperatureOverTime';
import NextBatchConfig from './components/NextBatchConfig';
import RadioButtonRequest from './components/RadioButtonRequest'
import SaveProductionData from "./components/SaveProductionData";
import BatchList from './components/BatchList';


const JD = () => {

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
                <RadioButtonRequest/>

                {/* Batch List */}
                <BatchList/>


                <SaveProductionData/>

            </div>

        </div>
    );
};

export default JD;