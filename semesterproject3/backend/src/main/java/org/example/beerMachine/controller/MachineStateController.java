package org.example.beerMachine.controller;

import org.example.beerMachine.OpcUA.MachineController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/machine")
public class MachineStateController {

    private final MachineController machineController;


    public MachineStateController(MachineController machineController) {
        this.machineController = machineController;
    }
    @PostMapping("/reset")
    public boolean resetMachine() {
        try {
            return machineController.resetMachine();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @PostMapping("/start")
    public boolean startMachine() {
        try {
            return machineController.startMachine();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @PostMapping("/stop")
    public boolean stopMachine() {
        try {
            return machineController.stopMachine();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @PostMapping("/abort")
    public boolean abortMachine() {
        try {
            return machineController.abortMachine();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @PostMapping("/clear")
    public boolean clearMachine() {
        try {
            return machineController.clearMachine();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
