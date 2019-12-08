package com.example.miniprojetevents.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.miniprojetevents.entities.Event;

import java.util.List;

@Dao
public interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Event event);

    @Query("DELETE FROM Event")
    void deleteAll();

    @Query("DELETE FROM Event where id=:id")
    void deleteEventFromFavorit(int id);

    @Query("Select * FROM Event where id=:id")
    Event selectEvent(int id);

    @Query("Select id FROM Event where id=:id")
    Integer selectEventId(int id);

    @Query("SELECT * from Event ORDER BY id ASC")
    LiveData<List<Event>> getEvents();

    @Query("SELECT * from Event ORDER BY id ASC")
    List<Event> getAllEvents();




}
