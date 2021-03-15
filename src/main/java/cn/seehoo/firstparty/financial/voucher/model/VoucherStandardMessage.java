package cn.seehoo.firstparty.financial.voucher.model;

import com.alipay.sofa.sofamq.com.shade.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Notice: 标准财务凭证消息定义：会计平台所需标准消息
 *
 * @author Staunch
 * @version 1.0
 * @date 2021/3/15
 * @since 1.0
 */
@Data
public class VoucherStandardMessage implements Serializable {
    private static final long serialVersionUID = 4131214301751366368L;
    /**
     * 记录类型：01混合，02主记录，03子记录
     */
    @JSONField(name = "MsgType")
    private String MsgType;
    /**
     * 报文发送时间
     */
    @JSONField(name = "SendMsgTime")
    private Date SendMsgTime;
    /**
     * 报文编号
     */
    @JSONField(name = "MsgId")
    private String MsgId;
    /**
     * 是否冲销，0正常 1 红冲 2蓝冲
     */
    @JSONField(name = "IsChargeAgainst")
    private String IsChargeAgainst;
    /**
     * 制证交易流水
     */
    @JSONField(name = "AcctDocGenTrans")
    private AcctDocGenTrans AcctDocGenTrans;
    /**
     * 制证子交易流水集合
     */
    @JSONField(name = "AcctDocGenSubTransList")
    private List<AcctDocGenTransDoc> AcctDocGenSubTransList;
}
