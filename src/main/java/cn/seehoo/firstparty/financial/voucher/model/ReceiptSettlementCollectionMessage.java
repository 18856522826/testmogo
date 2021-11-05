package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Notice:
 *
 * @author xuxinhui
 * @version 1.0
 * @date 2021/11/01
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ReceiptSettlementCollectionMessage extends CommonMessage {

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
     * 实际还款金额
     */
    private BigDecimal actualRepaymentAmt;

    /**
     * 违约金
     */
    private BigDecimal penalty;

    /**
     * 剩余本金
     */
    private BigDecimal residualPrincipal;

    /**
     * 当期未收利息
     */
    private BigDecimal uncollectedInterest;

    /**
     * 留购价
     */
    private BigDecimal purchasePrice;

    /**
     * 费用
     */
    private BigDecimal fee;

    /**
     * 利息
     */
    private BigDecimal interest;

    /**
     * 现金流量金额
     */
    private BigDecimal cashFlowAmount;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 直租：13 回租6
     */
    private BigDecimal taxRate;

    /**
     * 融资期限
     */
    private Integer loanTerm;

    /**
     * 付款方开户行名称
     */
    private String payerBankName;

    /**
     * 付款方账号
     */
    private String payerAcctNo;

    /**
     * 账户key
     */
    private String bizUseType;

}
