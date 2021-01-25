package cn.gxx.starforum.dao;

import java.util.List;
import java.util.Map;

public interface ApiListMapper {
    List<Map<String, String>> selectByGroup(String group);
}
