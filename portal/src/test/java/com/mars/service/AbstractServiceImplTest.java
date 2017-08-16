package com.mars.service;

import com.mars.AbstractTest;
import com.mars.service.AbstractServiceImpl;

import org.junit.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.data.domain.PageRequest;

import static org.junit.Assert.*;
import static org.springframework.data.domain.Sort.Direction.ASC;

public class AbstractServiceImplTest extends AbstractTest{

    AbstractServiceImpl abstractUserService = new AbstractServiceImpl() {
    };

    @Test
    public void returnNullIfPageIsNull() throws Exception {
        assertNull(abstractUserService.getPageRequest(null, 2, "name", "asc"));
    }

    @Test
    public void returnNullIfPageIsLessThan0() throws Exception {
        assertNull(abstractUserService.getPageRequest(-1, 2, "name", "asc"));
    }

    @Test
    public void returnNullIfSizeIsLessThan1() throws Exception {
        assertNull(abstractUserService.getPageRequest(0, 0, "name", "asc"));
    }

    @Test
    public void returnNullIfSizeIsNull() throws Exception {
        assertNull(abstractUserService.getPageRequest(null, 2, "name", "asc"));
    }

    @Test
    public void returnPageRequestWithPageAndSize() throws Exception {
        assertThat(abstractUserService.getPageRequest(0, 2, " ", "asc"), new ReflectionEquals(new PageRequest(0, 2)));
    }

    @Test
    public void returnPageRequestWithPageSizeSortAndDirection() throws Exception {
        assertThat(abstractUserService.getPageRequest(0, 2, "filedName", "asc"), new ReflectionEquals(new PageRequest(0, 2, ASC, "filedName")));
    }

    @Test
    public void returnPageRequestWithPageSizeSortAndNullDirectionIfDirectionIsNull() throws Exception {
        assertThat(abstractUserService.getPageRequest(0, 2, "filedName", null), new ReflectionEquals(new PageRequest(0, 2, null, "filedName")));
    }
    @Test
    public void returnPageRequestWithPageSizeSortAndNullDirectionIfDirectionIsWrong() throws Exception {
        assertThat(abstractUserService.getPageRequest(0, 2, "filedName", "wrongDirection"), new ReflectionEquals(new PageRequest(0, 2, null, "filedName")));
    }
}