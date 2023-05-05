package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;

import java.util.*;

public class HotelManagementService {
    HotelManagementRepository repository = new HotelManagementRepository();

    public String addHotel(Hotel hotel){

        String ans = repository.addHotel(hotel);

        return ans;
    }


    public Integer addUser(User user) {
        int adharNo = repository.addUser(user);

        return adharNo;
    }

    public String getHotelWithMostFacility() {

        List<Hotel> hotels = repository.getAllHotels();
        if (hotels.isEmpty()) {
            return "";
        }

        // Map to store the number of facilities for each hotel
        Map<String, Integer> facilitiesCount = new HashMap<>();

        // Loop through all hotels to count their facilities
        for (Hotel hotel : hotels) {
            int count = hotel.getFacilities().size();
            facilitiesCount.put(hotel.getHotelName(), count);
        }

        // Find the max count of facilities among all hotels
        int maxCount = Collections.max(facilitiesCount.values());

        // Loop through the map to find the hotel name(s) with the max count of facilities
        List<String> maxFacilitiesHotels = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : facilitiesCount.entrySet()) {
            if (entry.getValue() == maxCount) {
                maxFacilitiesHotels.add(entry.getKey());
            }
        }

        // Sort the list of hotel names with max facilities in lexicographical order
        Collections.sort(maxFacilitiesHotels);

        // Return the first hotel name in the sorted list
        return maxFacilitiesHotels.get(0);
    }

    public int bookARoom(Booking booking){
        return repository.bookARoom(booking);
    }

    public int getBooking(Integer adharCard){

        int count = repository.getBooking(adharCard);
        return count;
    }

    public Hotel updateFacility(List<Facility> newFacilities, String hotelName){

        return repository.updateFacility(newFacilities, hotelName);
    }

}
