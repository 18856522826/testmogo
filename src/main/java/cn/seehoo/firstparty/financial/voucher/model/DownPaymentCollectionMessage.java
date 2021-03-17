package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Notice:购入融资租赁资产-首付款场景所需财务信息
 *
 * @author lix
 * @version 1.0
 * @date 2021/3/15
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class DownPaymentCollectionMessage extends CommonMessage{
    private static final long serialVersionUID = 358480098723850413L;
    /**
     * 租赁属性
     */
    private String leaseType;

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
     * 销售当前剩余利息
     */
    private BigDecimal surplusInterest;
    /**
     * 合同总利息
     */
    private BigDecimal sumInterest;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 客户姓名
     */
    private String custName;
    /*
     * 贷款期数(月)
     */
    private Integer loanTerm;
    /**
     * 留购价款
     */
    private BigDecimal retentionPrice;
    /**
     * 销售当前剩余利息税额
     */
    private BigDecimal interestTax;
    /**
     * 剩余不含税本金
     */
    private BigDecimal principalExcludeTax;
    /**
     * 销售还款剩余未还税额
     */
    private BigDecimal rentTax;
    /**
     * 税率
     */
    private BigDecimal taxRate;


}


