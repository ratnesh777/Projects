package com.mars.mapper;

import java.util.HashSet;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mars.models.BackRoom;
import com.mars.repository.entity.BackRoomEntity;

@Service
public class BackRoomMapperImpl extends AbstractBaseMapper<BackRoomEntity, BackRoom>
        implements BackRoomMapper
{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public BackRoom toModel(BackRoomEntity entity)
    {
        if (entity == null)
        {
            return null;
        }
        BackRoom model = new BackRoom();
        model.setId(entity.getId() != null ? entity.getId().toString() : null);
        model.setName(entity.getName());
        model.setHomeZoneIP(entity.getHomeZoneIP());
        model.setVoipProxyIP(entity.getVoipProxyIP());
        model.setManagementProxyIP(entity.getManagementProxyIP());
        model.setCompany(companyMapper.toModel(entity.getCompany()));
        model.setUsers(userMapper.toModels(entity.getUsers()));
        return model;
    }

    @Override
    public BackRoomEntity toEntity(BackRoom model)
    {
        if (model == null)
        {
            return null;
        }
        BackRoomEntity entity = new BackRoomEntity();
        entity.setId(
                StringUtils.isNotBlank(model.getId()) ? Integer.parseInt(model.getId()) : null);
        entity.setName(model.getName());
        entity.setHomeZoneIP(model.getHomeZoneIP());
        entity.setVoipProxyIP(model.getVoipProxyIP());
        entity.setManagementProxyIP(model.getManagementProxyIP());
        entity.setCompany(companyMapper.toEntity(model.getCompany()));
        entity.setUsers(new HashSet<>(userMapper.toEntities(model.getUsers())));
        return entity;
    }
}
