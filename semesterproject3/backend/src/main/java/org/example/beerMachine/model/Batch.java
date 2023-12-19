package org.example.beerMachine.model;

import jakarta.persistence.*;
import org.example.beerMachine.OpcUA.MachineController;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@RestController
@Entity
@Table(name = "batch")
public class Batch {
    @Id
    @SequenceGenerator(
            name = "batch_sequence",
            sequenceName = "batch_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "batch_generator"
    )
    private Long id;
    private float recipe;
    private float quantity;
    private float machineSpeedActualProductsPerMinute;
    private float machineSpeedActualNormalized;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private int processedProductsTotal;
    private int acceptableProducts;
    private int defectProducts;
    private float temperatureLowest;
    private float temperatureHighest;
    private float temperatureMean;
    private float humidityLowest;
    private float humidityHighest;
    private float humidityMean;
    private float vibrationLowest;
    private float vibrationHighest;
    private float vibrationMean;
    private float ingredientBarleyStart;
    private float ingredientBarleyStop;
    private float ingredientHopsStart;
    private float ingredientHopsStop;
    private float ingredientMaltStart;
    private float ingredientMaltStop;
    private float ingredientWheatStart;
    private float ingredientWheatStop;
    private float ingredientYeastStart;
    private float ingredientYeastStop;
    private String status;

    public Batch() {
    }


    public Batch(float recipe, float quantity, float ingredientBarleyStart, float ingredientHopsStart, float ingredientMaltStart, float ingredientWheatStart, float ingredientYeastStart, float ingredientBarleyStop, float ingredientHopsStop, float ingredientMaltStop, float ingredientWheatStop, float ingredientYeastStop) {
        this.recipe = recipe;
        this.quantity = quantity;

    }

    public Batch(int recipe, int quantity, int machineSpeed) {
        this.recipe = recipe;
        this.quantity = quantity;
        this.machineSpeedActualProductsPerMinute = machineSpeed;
    }

    @Override
    public String toString() {
        return "Batch{" +
                "id=" + id +
                ", recipe=" + recipe +
                ", quantity=" + quantity +
                ", startTime=" + startTime +
                '}';
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public float getRecipe() {
        return recipe;
    }

    public void setRecipe(float recipe) {
        this.recipe = recipe;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getMachineSpeedActualProductsPerMinute() {
        return machineSpeedActualProductsPerMinute;
    }

    public void setMachineSpeedActualProductsPerMinute(float machineSpeedActualProductsPerMinute) {
        this.machineSpeedActualProductsPerMinute = machineSpeedActualProductsPerMinute;
    }

    public float getMachineSpeedActualNormalized() {
        return machineSpeedActualNormalized;
    }

    public void setMachineSpeedActualNormalized(float machineSpeedActualNormalized) {
        this.machineSpeedActualNormalized = machineSpeedActualNormalized;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public int getProcessedProductsTotal() {
        return processedProductsTotal;
    }

    public void setProcessedProductsTotal(int processedProductsTotal) {
        this.processedProductsTotal = processedProductsTotal;
    }

    public int getAcceptableProducts() {
        return acceptableProducts;
    }

    public void setAcceptableProducts(int acceptableProducts) {
        this.acceptableProducts = acceptableProducts;
    }

    public int getDefectProducts() {
        return defectProducts;
    }

    public void setDefectProducts(int defectProducts) {
        this.defectProducts = defectProducts;
    }

    public float getTemperatureLowest() {
        return temperatureLowest;
    }

    public void setTemperatureLowest(float temperatureLowest) {
        this.temperatureLowest = temperatureLowest;
    }

    public float getTemperatureHighest() {
        return temperatureHighest;
    }

    public void setTemperatureHighest(float temperatureHighest) {
        this.temperatureHighest = temperatureHighest;
    }

    public float getTemperatureMean() {
        return temperatureMean;
    }

    public void setTemperatureMean(float temperatureMean) {
        this.temperatureMean = temperatureMean;
    }

    public float getHumidityLowest() {
        return humidityLowest;
    }

    public void setHumidityLowest(float humidityLowest) {
        this.humidityLowest = humidityLowest;
    }

    public float getHumidityHighest() {
        return humidityHighest;
    }

    public void setHumidityHighest(float humidityHighest) {
        this.humidityHighest = humidityHighest;
    }

    public float getHumidityMean() {
        return humidityMean;
    }

    public void setHumidityMean(float humidityMean) {
        this.humidityMean = humidityMean;
    }

    public float getVibrationLowest() {
        return vibrationLowest;
    }

    public void setVibrationLowest(float vibrationLowest) {
        this.vibrationLowest = vibrationLowest;
    }

    public float getVibrationHighest() {
        return vibrationHighest;
    }

    public void setVibrationHighest(float vibrationHighest) {
        this.vibrationHighest = vibrationHighest;
    }

    public float getVibrationMean() {
        return vibrationMean;
    }

    public void setVibrationMean(float vibrationMean) {
        this.vibrationMean = vibrationMean;
    }

    public float getIngredientBarleyStart() {
        return ingredientBarleyStart;
    }

    public void setIngredientBarleyStart(float ingredientBarleyStart) {
        this.ingredientBarleyStart = ingredientBarleyStart;
    }

    public float getIngredientBarleyStop() {
        return ingredientBarleyStop;
    }

    public void setIngredientBarleyStop(float ingredientBarleyStop) {
        this.ingredientBarleyStop = ingredientBarleyStop;
    }

    public float getIngredientHopsStart() {
        return ingredientHopsStart;
    }

    public void setIngredientHopsStart(float ingredientHopsStart) {
        this.ingredientHopsStart = ingredientHopsStart;
    }

    public float getIngredientHopsStop() {
        return ingredientHopsStop;
    }

    public void setIngredientHopsStop(float ingredientHopsStop) {
        this.ingredientHopsStop = ingredientHopsStop;
    }

    public float getIngredientMaltStart() {
        return ingredientMaltStart;
    }

    public void setIngredientMaltStart(float ingredientMaltStart) {
        this.ingredientMaltStart = ingredientMaltStart;
    }

    public float getIngredientMaltStop() {
        return ingredientMaltStop;
    }

    public void setIngredientMaltStop(float ingredientMaltStop) {
        this.ingredientMaltStop = ingredientMaltStop;
    }

    public float getIngredientWheatStart() {
        return ingredientWheatStart;
    }

    public void setIngredientWheatStart(float ingredientWheatStart) {
        this.ingredientWheatStart = ingredientWheatStart;
    }

    public float getIngredientWheatStop() {
        return ingredientWheatStop;
    }

    public void setIngredientWheatStop(float ingredientWheatStop) {
        this.ingredientWheatStop = ingredientWheatStop;
    }

    public float getIngredientYeastStart() {
        return ingredientYeastStart;
    }

    public void setIngredientYeastStart(float ingredientYeastStart) {
        this.ingredientYeastStart = ingredientYeastStart;
    }

    public float getIngredientYeastStop() {
        return ingredientYeastStop;
    }

    public void setIngredientYeastStop(float ingredientYeastStop) {
        this.ingredientYeastStop = ingredientYeastStop;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // set the startTime before the entity is persisted for the first time
    @PrePersist
    protected void onCreate() throws ExecutionException, InterruptedException {
        MachineController machineController = new MachineController();
        this.recipe = machineController.readRecipeCurrent();
        this.quantity = machineController.readQuantityCurrent();
        this.machineSpeedActualProductsPerMinute = machineController.readMachineSpeedCurrentProductsPerMinute();
        this.machineSpeedActualNormalized = machineController.readMachineSpeedCurrent();
        this.startTime = LocalDateTime.now();
        this.ingredientBarleyStart = machineController.readIngredientBarley();
        this.ingredientHopsStart = machineController.readIngredientHops();
        this.ingredientMaltStart = machineController.readIngredientMalt();
        this.ingredientWheatStart = machineController.readIngredientWheat();
        this.ingredientYeastStart = machineController.readIngredientYeast();
        this.status = "started";
        this.temperatureHighest = machineController.readTemperature();
        this.temperatureLowest = machineController.readTemperature();
        this.humidityHighest = machineController.readTemperature();
        this.humidityLowest = machineController.readTemperature();
        this.vibrationHighest = machineController.readVibration();
        this.vibrationLowest = machineController.readVibration();

    }
}
