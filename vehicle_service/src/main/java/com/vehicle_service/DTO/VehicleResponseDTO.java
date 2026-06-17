package com.vehicle_service.DTO;

import com.vehicle_service.Enums.FuelType;
import com.vehicle_service.Enums.TransmissionType;
import com.vehicle_service.Enums.VehicleType;

public class VehicleResponseDTO {

    private String vehicleNumber;
    private String ownerId;
    private String brand;
    private Double pricePerDay;
    private Double securityPrice;
    private Double AdvancePayment;
    private Integer manufacturingYear;
    private String city;
    private VehicleType vehicleType;
    private FuelType fuelType;
    private TransmissionType transmissionType;
    private Boolean isAvailable;

    public VehicleResponseDTO(){

    }
    public VehicleResponseDTO(String vehicleNumber,String ownerId,String brand,VehicleType vehicleType,FuelType fuelType,TransmissionType transmissionType,Double pricePerDay,Double securityPrice,Double AdvancePayment,Integer manufacturingYear,String city,Boolean isAvailable){
        this.vehicleNumber=vehicleNumber;
        this.ownerId=ownerId;
        this.brand=brand;
        this.vehicleType=vehicleType;
        this.fuelType=fuelType;
        this.transmissionType=transmissionType;
        this.pricePerDay=pricePerDay;
        this.securityPrice=securityPrice;
        this.AdvancePayment=AdvancePayment;
        this.manufacturingYear=manufacturingYear;
        this.city=city;
        this.isAvailable=isAvailable;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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
        return AdvancePayment;
    }

    public void setAdvancePayment(Double AdvancePayment) {
        this.AdvancePayment = AdvancePayment;
    }

    public Integer getManufacturingYear() {
        return manufacturingYear;
    }

    public void setManufacturingYear(Integer manufacturingYear) {
        this.manufacturingYear = manufacturingYear;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public Boolean getIsAvailable() {
        return isAvailable;
    }
    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    
}

