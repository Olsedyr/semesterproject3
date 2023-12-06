import React, { useState, useEffect } from 'react';
import axios from "axios";


const MaintenanceBox = () => {

    const [maintenanceCounter, setMaintenanceCounter] = useState(null);
    //const [maintenanceState, setMaintenanceState] = useState(null);
    const [maintenanceTrigger, setMaintenanceTrigger] = useState(null);

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
            { url: 'http://localhost:8080/opcua/maintenanceCounter', setValue: setMaintenanceCounter },
            //{ url: 'http://localhost:8080/opcua/maintenanceState', setValue: setMaintenanceState },
            { url: 'http://localhost:8080/opcua/maintenanceTrigger', setValue: setMaintenanceTrigger },
        ];



        endpoints.forEach(({ url, setValue }) => fetchData(url, setValue));
        const intervalId = setInterval(() => {
            endpoints.forEach(({ url, setValue }) => fetchData(url, setValue));
        }, 1000);

        return () => clearInterval(intervalId);
    }, []);


    const calculateMaintenancePercentage = () => {
        if (maintenanceCounter !== null && maintenanceTrigger !== null) {
            const Percentage = (maintenanceCounter / maintenanceTrigger) * 100;
            return Percentage > 100 ? 100 : Percentage; // Percentage shoud not doesn't exceed 100%
        }
        return 0;
    };

    return (
        <div className="info-box">
            <h2>Maintenance Bar</h2>
            <div className="maintenance-bar" id="maintenanceBar" style={{ width: `100%` }}>
                <div
                    id="maintenanceProgress"
                    className="maintenance-progress"
                    style={{ width: `${calculateMaintenancePercentage()}%` }}
                ></div>
            </div>
        </div>
    );
};

export default MaintenanceBox;