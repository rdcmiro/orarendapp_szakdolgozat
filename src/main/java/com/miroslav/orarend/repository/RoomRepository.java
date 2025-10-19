package com.miroslav.orarend.repository;

import com.miroslav.orarend.pojo.Room;
import com.miroslav.orarend.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Room findByName(@Param("name") String roomName);

    boolean existsByName(@Param("name") String roomName);

    List<Room> findAllByCreatedBy(User user);

}
