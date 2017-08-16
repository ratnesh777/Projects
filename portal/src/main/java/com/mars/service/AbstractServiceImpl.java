package com.mars.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public abstract class AbstractServiceImpl
{
    protected PageRequest getPageRequest(Integer page, Integer size, String sortParam,
            String sortDirection)
    {
        if (page != null && page >= 0 && size != null && size > 0)
        {
            if (StringUtils.isBlank(sortParam))
            {
                return new PageRequest(page, size);
            }
            else
            {
                return new PageRequest(page, size, Sort.Direction.fromStringOrNull(sortDirection), sortParam);
            }
        }
        return null;
    }
}
