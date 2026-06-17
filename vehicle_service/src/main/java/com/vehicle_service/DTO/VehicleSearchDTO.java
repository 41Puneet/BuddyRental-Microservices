package com.vehicle_service.DTO;
import com.vehicle_service.Enums.*;


public class VehicleSearchDTO {
    private String brand;
    private VehicleType vehicleType;
    private FuelType fuelType;
    private TransmissionType transmissionType;
    private Double minPrice;
    private Double maxPrice;
    private Integer manufacturingYear;
    private String city;


    public VehicleSearchDTO(){

    }
    public VehicleSearchDTO(String brand,VehicleType vehicleType,FuelType fuelType,TransmissionType transmissionType,Double minPrice,Double maxPrice,Integer manufacturingYear,String city){
        this.brand=brand;
        this.vehicleType=vehicleType;
        this.fuelType=fuelType;
        this.transmissionType=transmissionType;
        this.minPrice=minPrice;
        this.maxPrice=maxPrice;
        this.manufacturingYear=manufacturingYear;
        this.city=city;
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
    public Double getMinPrice() {
        return minPrice;
    }
    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }
    public Double getMaxPrice() {
        return maxPrice;
    }
    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
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

    
}
