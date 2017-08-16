package com.mars.service;

import static com.mars.util.ErrorMessagesConstant.BACK_ROOM_ID_NOT_FOUND;
import static com.mars.util.ErrorMessagesConstant.DUPLICATE_BACK_ROOM_NAME_AND_COMPANY;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.mars.AbstractTest;
import com.mars.exception.PortalException;
import com.mars.exception.PortalServiceParameterException;
import com.mars.exception.ResourceNotFoundException;
import com.mars.mapper.BackRoomMapper;
import com.mars.models.BackRoom;
import com.mars.models.Company;
import com.mars.repository.BackRoomRepository;
import com.mars.repository.entity.BackRoomEntity;
import com.mars.service.BackRoomServiceImpl;
import com.mars.service.CompanyService;
import com.mars.util.ErrorMessagesConstant;

public class BackRoomServiceImplTest extends AbstractTest
{
    @Mock
    private CompanyService companyService;

    @Mock
    private BackRoomRepository backRoomRepository;

    @Mock
    private BackRoomMapper backRoomMapper;

    @InjectMocks
    @Spy
    private BackRoomServiceImpl backRoomService;

    @Test
    public void saveBAckRoomToDB() throws Exception
    {
        BackRoom backRoomModelToSave = getBackRoomModel();
        Company someCompany = getCompanyModel();
        someCompany.setName("some name");
        backRoomModelToSave.setCompany(someCompany);

        BackRoomEntity convertedEntityToSave = getBackRoomEntity();
        convertedEntityToSave.setUsers(Collections.emptySet());
        convertedEntityToSave.setId(null);

        BackRoomEntity savedBackRoomEntity = getBackRoomEntity();
        savedBackRoomEntity.setUsers(Collections.emptySet());

        BackRoom backRoomModelPreparedToSave = getBackRoomModel();
        backRoomModelPreparedToSave.setId(null);
        backRoomModelPreparedToSave.setUsers(Collections.emptyList());

        BackRoom savedBackRoomModel = getBackRoomModel();
        savedBackRoomModel.setUsers(Collections.emptyList());

        when(companyService.findCompanyById("1001")).thenReturn(getCompanyModel());
        when(backRoomMapper.toEntity(backRoomModelPreparedToSave))
                .thenReturn(convertedEntityToSave);
        when(backRoomRepository.save(convertedEntityToSave)).thenReturn(savedBackRoomEntity);
        when(backRoomMapper.toModel(savedBackRoomEntity)).thenReturn(savedBackRoomModel);

        assertSame(savedBackRoomModel, backRoomService.create(backRoomModelToSave));

        verify(companyService).findCompanyById("1001");
        verify(backRoomMapper).toEntity(backRoomModelPreparedToSave);
        verify(backRoomRepository).save(convertedEntityToSave);
        verify(backRoomMapper).toModel(savedBackRoomEntity);
        verifyNoMoreInteractions(companyService, backRoomMapper, backRoomRepository);
    }

    @Test
    public void throwPortalServiceParameterExceptionForDBUniqueConstraintViolation()
            throws Exception
    {
        thrown.expect(PortalServiceParameterException.class);
        thrown.expectMessage(containsString(DUPLICATE_BACK_ROOM_NAME_AND_COMPANY));
        thrown.expectMessage(containsString("[1:back room name=back room 1]"));
        thrown.expectMessage(containsString("[2:company=Company(id=1001, name=IPC-TEST)]"));

        when(companyService.findCompanyById("1001")).thenReturn(getCompanyModel());
        when(backRoomRepository.save(any(BackRoomEntity.class)))
                .thenThrow(new DataIntegrityViolationException("some",
                        new Exception(new Exception("name_company_UniqueConstraint"))));

        backRoomService.create(getBackRoomModel());
    }

    @Test
    public void throwPortalExceptionForDBException() throws Exception
    {
        thrown.expect(PortalException.class);
        thrown.expectMessage(containsString(ErrorMessagesConstant.ERROR_CREATE_BACK_ROOM));

        when(backRoomRepository.save(any(BackRoomEntity.class)))
                .thenThrow(new DataIntegrityViolationException("some"));

        backRoomService.create(getBackRoomModel());
    }

    @Test
    public void throwPortalExceptionForAnyExceptionExceptDBUniqueConstraintViolation()
            throws Exception
    {
        thrown.expect(PortalException.class);
        thrown.expectMessage(containsString(ErrorMessagesConstant.ERROR_CREATE_BACK_ROOM));

        when(backRoomRepository.save(any(BackRoomEntity.class))).thenThrow(new RuntimeException());

        backRoomService.create(getBackRoomModel());
    }

    @Test
    public void returnBAckRoomById() throws Exception
    {

        BackRoom backRoomModel = getBackRoomModel();

        when(backRoomRepository.findOne(1)).thenReturn(getBackRoomEntity());
        when(backRoomMapper.toModel(getBackRoomEntity())).thenReturn(backRoomModel);

        assertSame(backRoomModel, backRoomService.findById("1"));

        verify(backRoomRepository).findOne(1);
        verify(backRoomMapper).toModel(getBackRoomEntity());
        verifyNoMoreInteractions(backRoomRepository, backRoomMapper);
    }

    @Test
    public void throwNotFoundExceptionIfIdIsNull() throws Exception
    {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage(containsString(BACK_ROOM_ID_NOT_FOUND));
        thrown.expectMessage(containsString("[1:id=null]"));

        backRoomService.findById(null);
    }

    @Test
    public void throwNotFoundExceptionIfIdIsNotNumeric() throws Exception
    {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage(containsString(BACK_ROOM_ID_NOT_FOUND));
        thrown.expectMessage(containsString("[1:id=asd]"));

        backRoomService.findById("asd");
    }

    @Test
    public void throwNotFoundExceptionIfBackRoomDoesNotExists() throws Exception
    {
        when(backRoomRepository.findOne(1)).thenReturn(null);
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage(containsString(BACK_ROOM_ID_NOT_FOUND));
        thrown.expectMessage(containsString("[1:id=1]"));

        backRoomService.findById("1");
    }

    @Test
    public void findAllBackRoomsByPages() throws Exception
    {
        PageRequest pageRequest = new PageRequest(0, 1, Sort.Direction.ASC, "name");
        BackRoomEntity backRoomEntity = new BackRoomEntity();
        BackRoom backRoom = new BackRoom();

        when(backRoomService.getPageRequest(0, 1, "name", "asc")).thenReturn(pageRequest);
        when(backRoomRepository.findAll(pageRequest))
                .thenReturn(new PageImpl<>(asList(backRoomEntity)));
        when(backRoomMapper.toModel(backRoomEntity)).thenReturn(backRoom);

        Page<BackRoom> all = backRoomService.findAll(0, 1, "name", "asc");

        assertEquals(1, all.getTotalPages());
        assertEquals(1, all.getTotalElements());
        List<BackRoom> content = all.getContent();
        assertEquals(1, content.size());
        assertSame(backRoom, content.get(0));

        verify(backRoomService).getPageRequest(0, 1, "name", "asc");
        verify(backRoomRepository).findAll(pageRequest);
        verify(backRoomMapper).toModel(backRoomEntity);
    }
}
