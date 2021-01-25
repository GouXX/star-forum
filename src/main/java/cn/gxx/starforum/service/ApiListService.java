package cn.gxx.starforum.service;

import java.util.List;
import java.util.Map;

public interface ApiListService {
    List<Map<String, String>> getListByGroup(String group);
}
