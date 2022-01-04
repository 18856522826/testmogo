package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Notice: 预收下期租金退回
 *
 * @author xuxu
 * @version 1.0
 * @date 2021/6/23
 * @since 1.0
 */
@Getter
@Setter
@ToString(callSuper = true)
public class ReturnNextRentCollectionMessage extends CommonMessage {
    private static final long serialVersionUID = 6259765811103367802L;
    /**
     * 租赁类型
     */
    private String leaseType;
    /**
     * 合同号码
     */
    private String contractNo;
    /**
     * 对应收款金额
     */
    private BigDecimal correspondRefundAmount;
    /**
     *对应收款利息
     */
    private BigDecimal correspondRefundInterest;
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
     * 账户key
     */
    private String bizUseType;
    /**
     *付款方开户行名称
     */
    private String payerBankName;
    /**
     *付款方账号
     */
    private String payerAcctNo;
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
