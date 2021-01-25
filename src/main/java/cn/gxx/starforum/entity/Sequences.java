package cn.gxx.starforum.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 序列号表
 * </p>
 *
 * @author Gxx
 * @since 2020-04-02
 */
public class Sequences implements Serializable {

    /**
     * 序列号键值
     */
    @TableField("seq_key")
    private String seqKey;
    /**
     * 序列号值
     */
    @TableField("seq_value")
    private Integer seqValue;
    /**
     * 递增值
     */
    private Integer increase;


    public String getSeqKey() {
        return seqKey;
    }

    public void setSeqKey(String seqKey) {
        this.seqKey = seqKey;
    }

    public Integer getSeqValue() {
        return seqValue;
    }

    public void setSeqValue(Integer seqValue) {
        this.seqValue = seqValue;
    }

    public Integer getIncrease() {
        return increase;
    }

    public void setIncrease(Integer increase) {
        this.increase = increase;
    }

    @Override
    public String toString() {
        return "Sequences{" +
        ", seqKey=" + seqKey +
        ", seqValue=" + seqValue +
        ", increase=" + increase +
        "}";
    }
}
