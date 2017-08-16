package com.mars.service;

import java.util.Collections;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mars.exception.PortalException;
import com.mars.exception.PortalServiceParameterException;
import com.mars.exception.ResourceNotFoundException;
import com.mars.mapper.BackRoomMapper;
import com.mars.models.BackRoom;
import com.mars.repository.BackRoomRepository;
import com.mars.repository.entity.BackRoomEntity;
import com.mars.util.ErrorMessagesConstant;

@Service
public class BackRoomServiceImpl extends AbstractServiceImpl implements BackRoomService
{

    @Autowired
    private BackRoomRepository backRoomRepository;

    @Autowired
    private BackRoomMapper backRoomMapper;

    @Autowired
    private CompanyService companyService;

    @Override
    @Transactional
    public BackRoom create(BackRoom backRoom)
    {
        backRoom.setId(null);
        backRoom.setCompany(companyService.findCompanyById(
                backRoom.getCompany() != null ? backRoom.getCompany().getId() : null));
        backRoom.setUsers(Collections.emptyList());
        try
        {
            return backRoomMapper
                    .toModel(backRoomRepository.save(backRoomMapper.toEntity(backRoom)));
        }
        catch (DataIntegrityViolationException e)
        {
            if (e.getCause() != null && e.getCause().getCause() != null
                    && StringUtils.containsIgnoreCase(e.getCause().getCause().getMessage(),
                            "name_company_UniqueConstraint"))
            {
                throw new PortalServiceParameterException(
                        ErrorMessagesConstant.DUPLICATE_BACK_ROOM_NAME_AND_COMPANY)
                                .addContextValue("back room name", backRoom.getName())
                                .addContextValue("company", backRoom.getCompany());
            }
            throw new PortalException(ErrorMessagesConstant.ERROR_CREATE_BACK_ROOM, e);
        }
        catch (Throwable throwable)
        {
            throw new PortalException(ErrorMessagesConstant.ERROR_CREATE_BACK_ROOM, throwable);
        }
    }

    @Override
    public BackRoom findById(String id)
    {
        BackRoomEntity backRoomEntity = null;
        try
        {
            backRoomEntity = backRoomRepository.findOne(Integer.parseInt(id));
        }
        catch (Exception ignored)
        {

        }

        if (backRoomEntity == null)
        {
            throw new ResourceNotFoundException(ErrorMessagesConstant.BACK_ROOM_ID_NOT_FOUND)
                    .addContextValue(ErrorMessagesConstant.ID, id);
        }
        return backRoomMapper.toModel(backRoomEntity);
    }

    @Override
    public Page<BackRoom> findAll(Integer page, Integer size, String sortParam,
            String sortDirection)
    {
        return backRoomRepository.findAll(getPageRequest(page, size, sortParam, sortDirection))
                .map(userEntity -> backRoomMapper.toModel(userEntity));
    }
}
