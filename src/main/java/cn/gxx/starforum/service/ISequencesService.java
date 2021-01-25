package cn.gxx.starforum.service;

/**
 * <p>
 * 序列号表 服务类
 * </p>
 *
 * @author Gxx
 * @since 2020-04-02
 */
public interface ISequencesService {
    /**
     * @description: 获取序列号并自增
     * @param key
     * @return: Integer
     * @author: Gxx
     * @time: 2020/4/2 8:37 下午
     */    
    Integer getSeqAndIncBykey(String key);
}
