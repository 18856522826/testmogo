package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Notice: 实收当期租金
 *
 * @author xuxu
 * @version 1.0
 * @date 2021/6/23
 * @since 1.0
 */
@Getter
@Setter
@ToString(callSuper = true)
public class ActuallyCurrentRentCollectionMessage extends CommonMessage{
    private static final long serialVersionUID = 2566018635857321387L;
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
