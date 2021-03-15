package cn.seehoo.firstparty.financial.voucher.model;

import com.alipay.sofa.sofamq.com.shade.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * Notice: 财务子交易
 *
 * @author xuxu
 * @version 1.0
 * @date 2021/2/26
 * @since 1.0
 */
@Data
public class AcctDocGenTrans {
    /**
     * 租赁类型：01直租，02回租，03所有
     */
    @JSONField(name = "LeaseType")
    private String LeaseType;
    /**
     * 业务类型
     */
    @JSONField(name = "BussinessType")
    private String BussinessType;
    /**
     * 进件ID
     */
    @JSONField(name = "InputId")
    private String InputId;
    /**
     * 制证交易名称
     */
    @JSONField(name = "TransName")
    private String TransName;
    /**
     * 合同ID
     */
    @JSONField(name = "ContractId")
    private String ContractId;
    /**
     * 进件流水ID
     */
    @JSONField(name = "IputFlowId")
    private String IputFlowId;
    /**
     * 生成日期
     */
    @JSONField(name = "GenerateDate")
    private Date GenerateDate;
    /**
     * 生成时间
     */
    @JSONField(name = "GenerateTime")
    private Date GenerateTime;
    /**
     * 租户编号
     */
    @JSONField(name = "TntInstId")
    private String TntInstId;
    /**
     * 租户名称
     */
    @JSONField(name = "TenantName")
    private String TenantName;
    /**
     * 合同名称
     */
    @JSONField(name = "ContractName")
    private String ContractName;
    /**
     * 创建人
     */
    @JSONField(name = "CreateUser")
    private String CreateUser;
    /**
     * 修改人
     */
    @JSONField(name = "UpdateUser")
    private String UpdateUser;
    /**
     * 业务板块
     * 01小微租赁
     */
    @JSONField(name = "BusinessRestriction")
    private String BusinessRestriction;
}
