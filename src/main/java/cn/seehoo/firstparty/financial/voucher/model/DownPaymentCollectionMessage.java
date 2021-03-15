package cn.seehoo.firstparty.financial.voucher.model;

import java.math.BigDecimal;

/**
 * Notice:购入融资租赁资产-首付款场景所需财务信息
 *
 * @author lix
 * @version 1.0
 * @date 2021/3/15
 * @since 1.0
 */
public class DownPaymentCollectionMessage {

    /**
     * 申请编号
     */
    private String orderNo;
    /**
     * 合同号码
     */
    private String contractNo;
    /**
     * 首付款金额
     */
    private BigDecimal downPayment;
    /**
     * 当前剩余未还本金-除首付金额
     */
    private BigDecimal surplusPrincipal;
    /**
     * 当前剩余未还利息
     */
    private BigDecimal surplusInterest;
    /**
     * 合同总利息
     */
    private BigDecimal sumInterest;
    /**
     * 合作方名称
     */
    private String merchantName;
    /**
     * 客户姓名
     */
    private String custName;
    /**
     * 税率
     */
    private BigDecimal taxRate;
    /*
     * 贷款期数(月)
     */
    private String loanTerm;
    /**
     * 留购价款
     */
    private BigDecimal retentionPrice;
    /**
     * 利息税额
     */
    private BigDecimal interestTax;
    /**
     * 剩余不含税本金
     */
    private BigDecimal principalExcludTax;
    /**
     * 剩余未还税额
     */
    private BigDecimal rentTax;

}


