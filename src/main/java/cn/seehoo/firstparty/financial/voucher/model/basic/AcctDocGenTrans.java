package cn.seehoo.firstparty.financial.voucher.model.basic;

import cn.seehoo.firstparty.financial.voucher.common.ClientConstants;
import com.alibaba.fastjson.annotation.JSONField;
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
    private String leaseType;
    /**
     * 业务类型
     */
    @JSONField(name = "BussinessType")
    private String bussinessType;
    /**
     * 进件ID
     */
    @JSONField(name = "InputId")
    private String inputId;
    /**
     * 制证交易名称
     */
    @JSONField(name = "TransName")
    private String transName;
    /**
     * 合同ID
     */
    @JSONField(name = "ContractId")
    private String contractId;
    /**
     * 进件流水ID
     */
    @JSONField(name = "IputFlowId")
    private String iputFlowId;
    /**
     * 生成日期
     */
    @JSONField(name = "GenerateDate")
    private Date generateDate = new Date();;
    /**
     * 生成时间
     */
    @JSONField(name = "GenerateTime")
    private Date generateTime = new Date();
    /**
     * 租户编号
     */
    @JSONField(name = "TntInstId")
    private String tntInstId;
    /**
     * 租户名称
     */
    @JSONField(name = "TenantName")
    private String tenantName;
    /**
     * 合同名称
     */
    @JSONField(name = "ContractName")
    private String contractName;
    /**
     * 创建人
     */
    @JSONField(name = "CreateUser")
    private String createUser;
    /**
     * 修改人
     */
    @JSONField(name = "UpdateUser")
    private String updateUser;
    /**
     * 业务板块
     * 01小微租赁
     */
    @JSONField(name = "BusinessRestriction")
    private String businessRestriction = ClientConstants.BUSINESS_RESTRICTION;
}
