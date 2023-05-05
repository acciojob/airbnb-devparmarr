package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;

import java.util.*;

public class HotelManagementRepository {

    private HashMap<String, Hotel> hotelDb = new HashMap<>();
    private HashMap<Integer, User> userDb = new HashMap<>();

    private HashMap<String,Booking> bookingDb = new HashMap<>();

    private HashMap<Integer,Integer> countOfBookings = new HashMap<>();

    public String addHotel(Hotel hotel){
        if(hotel == null || hotel.getHotelName() == null){
            return "FAILURE";
        }
        if(hotelDb.containsKey(hotel.getHotelName())){
            return "FAILURE";
        }

        hotelDb.put(hotel.getHotelName(), hotel);

        return "SUCCESS";
    }


    public int addUser(User user) {
        userDb.put(user.getaadharCardNo(), user);

        return user.getaadharCardNo();
    }
    public Optional<Hotel> getHotelByName(String name) {
        if (name == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(hotelDb.get(name));
    }

    public List<Hotel> getAllHotels() {
        return new ArrayList<>(hotelDb.values());
    }

    public int bookARoom(Booking booking){
        String key = UUID.randomUUID().toString();

        booking.setBookingId(key);

        String hotelName = booking.getHotelName();

        Hotel hotel = hotelDb.get(hotelName);

        int availableRooms = hotel.getAvailableRooms();

        if(availableRooms<booking.getNoOfRooms()){
            return -1;
        }

        int amountToBePaid = hotel.getPricePerNight()*booking.getNoOfRooms();
        booking.setAmountToBePaid(amountToBePaid);

        hotel.setAvailableRooms(hotel.getAvailableRooms()-booking.getNoOfRooms());

        bookingDb.put(key,booking);

        hotelDb.put(hotelName,hotel);

        int aadharCard = booking.getBookingAadharCard();
        Integer currentBookings = countOfBookings.get(aadharCard);
        countOfBookings.put(aadharCard, Objects.nonNull(currentBookings)?1+currentBookings:1);
        return amountToBePaid;
    }

    public int getBooking(Integer adharCard){
        return countOfBookings.get(adharCard);
    }

    public Hotel updateFacility(List<Facility> newFacilities, String hotelName){
        List<Facility> oldFacilities = hotelDb.get(hotelName).getFacilities();

        for(Facility facility: newFacilities){

            if(oldFacilities.contains(facility)){
                continue;
            }else{
                oldFacilities.add(facility);
            }
        }

        Hotel hotel = hotelDb.get(hotelName);
        hotel.setFacilities(oldFacilities);

        hotelDb.put(hotelName,hotel);

        return hotel;
    }



}
