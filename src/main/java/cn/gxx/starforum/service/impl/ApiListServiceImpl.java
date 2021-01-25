package cn.gxx.starforum.service.impl;

import cn.gxx.starforum.dao.ApiListMapper;
import cn.gxx.starforum.service.ApiListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Gxx
 * @time: 2021-01-24 00:14
 */
@Service
public class ApiListServiceImpl implements ApiListService {
    @Resource
    private ApiListMapper apiListMapper;

    @Override
    public List<Map<String, String>> getListByGroup(String group) {
        return apiListMapper.selectByGroup(group);
    }
}
