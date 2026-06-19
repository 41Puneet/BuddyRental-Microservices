package com.vehicle_service.DTO;
import com.vehicle_service.Enums.FuelType;
import com.vehicle_service.Enums.TransmissionType;
import com.vehicle_service.Enums.VehicleType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
public class VehicleRequestDTO {
    
    @NotBlank
    private String vehicleNumber;
    @NotBlank
    private String brand;
    private String model;
    @NotBlank
    private String city;
    @NotNull
    private VehicleType vehicleType;
    @NotNull
    private FuelType fuelType;
    @NotNull
    private TransmissionType transmissionType;
    @Positive
    @NotNull
    private Double pricePerDay;
    @Positive
    private Double securityPrice;
    @Positive
    private Double advancePayment;
    @NotNull
    private Integer manufacturingYear;

    private boolean isAvailable;
    

    public VehicleRequestDTO(){

    }
    public VehicleRequestDTO(String vehicleNumber,String brand,String model,String city,VehicleType vehicleType,FuelType fuelType,TransmissionType transmissionType,Double pricePerDay,Double securityPrice,Double advancePayment,Integer manufacturingYear,boolean isAvailable){
        this.vehicleNumber=vehicleNumber;
        
        this.brand=brand;
        this.model=model;
        this.vehicleType=vehicleType;
        this.city=city;
        this.fuelType=fuelType;
        this.transmissionType=transmissionType;
        this.pricePerDay=pricePerDay;
        this.securityPrice=securityPrice;
        this.advancePayment=advancePayment;
        this.manufacturingYear=manufacturingYear;
        this.isAvailable=isAvailable;
        
    }
    public boolean getAvailable() {
        return isAvailable;
    }
    public void setAvaiable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    public String getVehicleNumber() {
        return vehicleNumber;
    }
    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public VehicleType getVehicleType() {
        return vehicleType;
    }
    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }
    public FuelType getFuelType() {
        return fuelType;
    }
    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }
    public TransmissionType getTransmissionType() {
        return transmissionType;
    }
    public void setTransmissionType(TransmissionType transmissionType) {
        this.transmissionType = transmissionType;
    }
    public Double getPricePerDay() {
        return pricePerDay;
    }
    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }
    public Double getSecurityPrice() {
        return securityPrice;
    }
    public void setSecurityPrice(Double securityPrice) {
        this.securityPrice = securityPrice;
    }
    public Double getAdvancePayment() {
        return advancePayment;
    }
    public void setAdvancePayment(Double advancePayment) {
        this.advancePayment = advancePayment;
    }
    public Integer getManufacturingYear() {
        return manufacturingYear;
    }
    public void setManufacturingYear(Integer manufacturingYear) {
        this.manufacturingYear = manufacturingYear;
    }
    
    
}
