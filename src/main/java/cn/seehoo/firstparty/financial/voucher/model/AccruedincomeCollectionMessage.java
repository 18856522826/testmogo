package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Notice:
 *
 * @author xuxu
 * @version 1.0
 * @date 2021/9/27
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AccruedincomeCollectionMessage extends CommonMessage {

    /**
     * 是否冲销，0正常 1 红冲 2蓝冲
     */
    private String isChargeAgainst;

    /**
     * 租赁属性
     */
    private String leaseType;

    /**
     * 业务类型
     */
    private String bussinessType;

    /**
     * 申请编号
     */
    private String applyNo;

    /**
     * 提前结清
     */
    private String settleEarly;

    /**
     * 合同号码
     */
    private String contractNo;

    /**
     *补计提收入
     */
    private String accrualTask;

    /**
     * 0190101-直租补计提收入
     * 0190201-回租补计提收入
     */
    private String transType;

    /**
     * 应计提
     */
    private BigDecimal custName;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 合作商名称
     */
    private String partneName;

    /**
     * 主产品表方案
     */
    private String productTableScheme;

    /**
     * 直租：13 回租6
     */
    private BigDecimal taxRate;

    /**
     * 主表融资期限
     */
    private BigDecimal financingPeriod;

}
