package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Notice:
 *
 * @author xuxu
 * @version 1.0
 * @date 2021/11/1
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MarginCompensationCollectionMessage extends CommonMessage {
    /**
     * 租赁属性
     */
    private String leaseType;

    /**
     * 合同号码
     */
    private String contractNo;

    /**
     * 客户姓名
     */
    private String custName;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 还款计划表租金（含税）
     */
    private BigDecimal includeTaxRent;

    /**
     * 还款计划表利息
     */
    private BigDecimal interest;

    /**
     * 还款计划表本金（含税）
     */
    private BigDecimal includeCapital;

    /**
     * 直租：13 回租6
     */
    private BigDecimal taxRate;

    /**
     * 当前期数
     */
    private Integer currentPeriods;

    /**
     * 融资期限
     */
    private Integer loanTerm;
}
