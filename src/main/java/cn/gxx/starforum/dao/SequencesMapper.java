package cn.gxx.starforum.dao;

import cn.gxx.starforum.entity.Sequences;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * <p>
 * 序列号表 Mapper 接口
 * </p>
 *
 * @author Gxx
 * @since 2020-04-02
 */
@Repository
public interface SequencesMapper {

    Sequences getOneBySeqKey(String key);
    int updateForIncrement(Map<String, Object> map);
}
