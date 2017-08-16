package com.mars.service;

import org.springframework.data.domain.Page;

import com.mars.models.BackRoom;

/**
 * Copyright (c) 2017 IPC Systems, Inc.
 * Created by Viktor Bondarenko on 1/3/2017.
 */
public interface BackRoomService {

    BackRoom create(BackRoom backRoom);

    BackRoom findById(String id);

    Page<BackRoom> findAll(Integer page, Integer size, String sortParam, String sortDirection);
}
