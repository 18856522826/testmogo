package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Notice: 计提利息收入
 *
 * @author xuxu
 * @version 1.0
 * @date 2021/3/15
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class InterestIncomeCollectionMessage extends CommonMessage {
    private static final long serialVersionUID = 7157758499900398962L;
    /**
     * 租赁属性
     */
    private String leaseType;
    /**
     * 合同号码
     */
    private String contractNo;
    /**
     * 财务还款计划表当月租金总和
     */
    private BigDecimal rent;
    /**
     *
     * 财务还款计划当月利息
     */
    private BigDecimal interest;
    /**
     * 财务还款计划表当月租金税额总和
     */
    private BigDecimal rentTax;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 客户姓名
     */
    private String custName;
    /**
     * 当前期数
     */
    private Integer currentTerm;
    /**
     * 融资期限
     */
    private Integer loanTerm;
    /**
     * 留购价款
     */
    private BigDecimal retentionPrice;
    /**
     * 财务还款计划表本月后剩余利息总和
     */
    private BigDecimal surplusInterest;
    /**
     * 财务还款计划表本月后剩余利息税金总和
     */
    private BigDecimal interestTax;
    /**
     * 税率
     */
    private BigDecimal taxRate;
    /**
     * 财务计划表当月利息税额
     */
    private BigDecimal interestTaxB;
}
