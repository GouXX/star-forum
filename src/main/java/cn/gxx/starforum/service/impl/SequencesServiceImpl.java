package cn.gxx.starforum.service.impl;

import cn.gxx.starforum.dao.SequencesMapper;
import cn.gxx.starforum.entity.Sequences;
import cn.gxx.starforum.service.ISequencesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 序列号表 服务实现类
 * </p>
 *
 * @author Gxx
 * @since 2020-04-02
 */
@Service
public class SequencesServiceImpl implements ISequencesService {
    @Resource
    private SequencesMapper sequencesMapper;

    @Override
    @Transactional
    public Integer getSeqAndIncBykey(String key) {
        Sequences sequences = sequencesMapper.getOneBySeqKey(key);
        Integer newSeqValue = sequences.getSeqValue()+sequences.getIncrease();
        Map<String, Object> pmap = new HashMap<>();
        pmap.put("skey", key);
        pmap.put("svalue", newSeqValue);
        sequencesMapper.updateForIncrement(pmap);
        return sequences.getSeqValue();
    }
}
