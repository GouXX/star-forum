package cn.gxx.starforum.security.dynamicauth;

import cn.gxx.starforum.service.ApiListService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: 动态权限元数据加载器
 * @author: Gxx
 * @time: 2021-01-22 14:53
 */
@Component
public class DynamicFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Resource
    private ApiListService apiListService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) o;
        String url = fi.getRequestUrl();
        if (url.indexOf("/", 1) == -1){
            return null;
        }
        List<Map<String, String>> apis = apiListService.getListByGroup(url.substring(1, url.indexOf("/", 1)));
        List<String> roles = apis.stream()
                .filter(api -> new AntPathRequestMatcher(api.get("uri_antpath"), api.get("httpmethod")).matches(fi.getRequest()) && api.get("name") != null)
                .map(api -> api.get("name")).collect(Collectors.toList());
        if (roles != null){
            return SecurityConfig.createList(roles.toArray(new String[]{}));
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
