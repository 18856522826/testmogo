package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Notice: 提前结清-实收当期租金
 *
 * @author xuxinhui
 * @version 1.0
 * @date 2021/11/5
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EarlyActuallyCurrentCollectionMessage extends CommonMessage{
    /**
     * 租赁类型
     */
    private String leaseType;
    /**
     * 合同号码
     */
    private String contractNo;
    /**
     * 当前收款金额
     */
    private BigDecimal currentPaymentAmount;
    /**
     *当前收款利息
     */
    private BigDecimal currentInterest;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 客户姓名
     */
    private String custName;
    /*
     * 当前期数
     */
    private Integer currentTerm;
    /*
     * 贷款期数(月)
     */
    private Integer loanTerm;
    /**
     * 税率
     */
    private BigDecimal taxRate;
    /**
     *付款方开户行名称
     */
    private String payerBankName;
    /**
     *付款方账号
     */
    private String payerAcctNo;
    /**
     * 账户key
     */
    private String bizUseType;
    /**
     * 租金（含税）
     */
    private BigDecimal includeTaxRent;
    /**
     * 不含税租金
     */
    private BigDecimal noTaxRent;
    /**
     * 租金税额
     */
    private BigDecimal taxRent;
    /**
     * 本金（含税）
     */
    private BigDecimal includeCapital;
    /**
     * 不含税利息
     */
    private BigDecimal noTaxInterest;
    /**
     * 利息税额
     */
    private BigDecimal taxInterest;
}
