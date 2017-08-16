package com.mars.service;

import org.springframework.data.domain.Page;

import com.mars.models.BackRoom;

public interface BackRoomService {

    BackRoom create(BackRoom backRoom);

    BackRoom findById(String id);

    Page<BackRoom> findAll(Integer page, Integer size, String sortParam, String sortDirection);
}
