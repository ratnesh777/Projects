package com.mars.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mars.repository.entity.BackRoomEntity;

/**
 * Copyright (c) 2017 IPC Systems, Inc.
 * Created by Viktor Bondarenko on 1/3/2017.
 */
@Repository
public interface BackRoomRepository extends JpaRepository<BackRoomEntity, Integer> {
}
