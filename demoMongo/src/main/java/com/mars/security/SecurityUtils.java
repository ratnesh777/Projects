package com.mars.security;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SecurityUtils
{
    /**
     * Get the login of the current user.
     */
    public static String getCurrentLogin()
    {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        UserDetails springSecurityUser = null;
        String userName = null;
        if (authentication != null)
        {
            if (authentication.getPrincipal() instanceof UserDetails)
            {
                springSecurityUser = (UserDetails) authentication.getPrincipal();
                userName = springSecurityUser.getUsername();
            }
            else if (authentication.getPrincipal() instanceof String)
            {
                userName = (String) authentication.getPrincipal();
            }
        }
        return userName;
    }

    public static String getSessionId()
    {
        return RequestContextHolder.currentRequestAttributes().getSessionId();
    }

    public static String getRemoteAddress()
    {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getRemoteAddr();
    }

    public static Locale getLocale()
    {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getLocale();
    }

    public static Map<String, String> getAttributesMap()
    {
        Map<String, String> attributesMap = new HashMap<>();
        attributesMap.put("email", getCurrentLogin());
        attributesMap.put("sessionId", getSessionId());
        attributesMap.put("ip_address", getRemoteAddress());
        attributesMap.put("locale", getLocale().getDisplayName());
        attributesMap.put("timezone", TimeZone.getDefault().getDisplayName(getLocale()));
        attributesMap.put("timestamp_utc", getTimeStamp());
        return attributesMap;
    }

    private static String getTimeStamp()
    {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss zzz",
                getLocale());
        simpleDateFormat.setTimeZone(timeZone);
        return simpleDateFormat.format(calendar.getTime());
    }
}
