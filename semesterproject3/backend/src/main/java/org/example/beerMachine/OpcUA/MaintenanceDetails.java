package org.example;

public class MaintenanceDetails{
    private String packMLState;
    private int stopReasonID;
    private int maintenanceStatusBar;


    public MaintenanceDetails() {
    }


    public MaintenanceDetails(String packMLState, int stopReasonID, int maintenanceStatusBar) {
        this.packMLState = "packMLState";
        this.stopReasonID = stopReasonID;
        this.maintenanceStatusBar = maintenanceStatusBar;
    }

    public String getPackMLState() {
        return packMLState;
    }

    public void setPackMLState(String packMLState) {
        this.packMLState = packMLState;
    }

    public int getStopReasonID() {
        return stopReasonID;
    }

    public void setStopReasonID(int stopReasonID) {
        this.stopReasonID = stopReasonID;
    }

    public int getMaintenanceStatusBar() {
        return maintenanceStatusBar;
    }

    public void setMaintenanceStatusBar(int maintenanceStatusBar) {
        this.maintenanceStatusBar = maintenanceStatusBar;
    }
}