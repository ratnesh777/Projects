package com.mars.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mars.repository.entity.BackRoomEntity;

@Repository
public interface BackRoomRepository extends JpaRepository<BackRoomEntity, Integer> {
}
