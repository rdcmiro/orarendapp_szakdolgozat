package com.miroslav.orarend.repository;

import com.miroslav.orarend.pojo.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Room findByName(@Param("name") String roomName);

    boolean existsByName(@Param("name") String roomName);
}
