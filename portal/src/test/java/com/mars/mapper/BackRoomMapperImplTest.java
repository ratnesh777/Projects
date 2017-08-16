package com.mars.mapper;

import com.mars.AbstractSpringTest;
import com.mars.mapper.BackRoomMapper;
import com.mars.models.BackRoom;

import org.junit.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class BackRoomMapperImplTest extends AbstractSpringTest
{
    @Autowired
    BackRoomMapper backRoomMapper;
    @Test
    public void convertNotNullUserModelToNotNullUserEntity() throws Exception {
        assertThat(backRoomMapper.toEntity(getBackRoomModel()), new ReflectionEquals(getBackRoomEntity()));
    }

    @Test
    public void convertNullUserModelToNullUserEntity() throws Exception {
        assertNull(backRoomMapper.toEntity(null));
    }

    @Test
    public void convertNotNullUserEntityToNotNullUserModel() throws Exception {
        BackRoom backRoomModel = getBackRoomModel();
        backRoomModel.getUsers().get(0).setPassword(null);
        assertThat(backRoomMapper.toModel(getBackRoomEntity()), new ReflectionEquals(backRoomModel));
    }

    @Test
    public void convertNullUserEntityToNullUserModel() throws Exception {
        assertNull(backRoomMapper.toModel(null));
    }
}
