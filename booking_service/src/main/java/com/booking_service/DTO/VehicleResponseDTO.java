package com.booking_service.DTO;
import java.util.UUID;

public class VehicleResponseDTO {
    private String vehicleNumber;
    private UUID vehicleId;
    private Double pricePerDay;
    private String brand;
    private String model;

    public VehicleResponseDTO(){

    }
    public VehicleResponseDTO(String vehicleNumber,UUID vehicleId,Double pricePerDay,String brand,String model){
        this.vehicleNumber=vehicleNumber;
        this.vehicleId=vehicleId;
        this.pricePerDay=pricePerDay;
        this.brand=brand;
        this.model=model;
    }
    public String getVehicleNumber() {
        return vehicleNumber;
    }
    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }
    public UUID getVehicleId() {
        return vehicleId;
    }
    public void setVehicleId(UUID vehicleId) {
        this.vehicleId = vehicleId;
    }
    public Double getPricePerDay() {
        return pricePerDay;
    }
    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
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
    

}
