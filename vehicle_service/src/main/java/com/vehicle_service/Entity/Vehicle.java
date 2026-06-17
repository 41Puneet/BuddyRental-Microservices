package com.vehicle_service.Entity;
import com.vehicle_service.Enums.FuelType;
import com.vehicle_service.Enums.TransmissionType;
import com.vehicle_service.Enums.VehicleType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Table;



@Entity
@Table(name="vehicleEntity")
public class Vehicle {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long vehicleId;
    private String vehicleNumber;
    private String ownerId;
    private String brand;
    private String model;
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;
    @Enumerated(EnumType.STRING)
    private TransmissionType transmissionType;
    private Double pricePerDay;
    private Double securityPrice;
    private Double AdvancePayment;
    private Integer manufacturingYear;
    private String city;
    private boolean isAvailable;


    public Vehicle(){

    }
    public Vehicle(Long vehicleId,String vehicleNumber,String ownerId,String brand,String model,VehicleType vehicleType,FuelType fuelType,TransmissionType transmissionType,Double pricePerDay,Double securityPrice,Double AdvancePayment,Integer manufacturingYear,String city,boolean isAvailable){
        this.vehicleId=vehicleId;
        this.vehicleNumber=vehicleNumber;
        this.ownerId=ownerId;
        this.brand=brand;
        this.model=model;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getVehicleId() {
        return vehicleId;
    }
    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
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

    public boolean isIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    
    }

