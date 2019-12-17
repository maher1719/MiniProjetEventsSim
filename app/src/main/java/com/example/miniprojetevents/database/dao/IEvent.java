package com.example.miniprojetevents.database.dao;

import com.example.miniprojetevents.entities.Event;
import com.example.miniprojetevents.entities.MessageNetwork;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IEvent {
    @GET("/mesWebServices/selectAllEvent.php")
    Call<List<Event>> getEvent();

    @POST("/mesWebServices/addEvent.php")
    Call<MessageNetwork> addEvent(@Body Event event);

    @POST("/mesWebServices/addEvent.php")
    Call<MessageNetwork> addEvent1();


}
