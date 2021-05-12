package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Notice: 起租
 *
 * @author xuxu
 * @version 1.0
 * @date 2021/3/15
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class StartRentCollectionMessage extends CommonMessage {
    private static final long serialVersionUID = -4277553387835552886L;
    /**
     * 租赁属性
     */
    private String leaseType;
    /**
     * 合同号码
     */
    private String contractNo;
    /**
     * 销售还款计划表租金和
     */
    private BigDecimal rent;
    /**
     * 销售还款计划表除第0期外剩余本金总和
     */
    private BigDecimal surplusPrincipal;
    /**
     * 销售还款计划表除第0期外剩余利息总和
     */
    private BigDecimal surplusInterest;
    /**
     * 留购价款金额
     */
    private BigDecimal retentionPrice;
    /**
     * 不含税留购价
     */
    private BigDecimal retentionExcludeTax;
    /**
     * 留购价款税额
     */
    private BigDecimal retentionTax;
    /**
     * 主表产品方案
     */
    private String productName;
    /**
     * 客户姓名
     */
    private String custName;
    /**
     * 融资期限
     */
    private Integer loanTerm;
    /**
     * 销售当前剩余利息税额
     */
    private BigDecimal interestTax;
    /**
     * 税后利息总额
     */
    private BigDecimal interestExcludeTax;
    /**
     * 税后租金总额
     */
    private BigDecimal rentExcludeTax;
    /**
     * 不含税剩余未收本金
     */
    private BigDecimal principalExcludeTax;

    /**
     * 租金税额
     */
    private BigDecimal rentTax;
    /**
     * 本金税额
     */
    private BigDecimal principalTax;
    /**
     * 车辆销售价
     */
    private BigDecimal vehicleSalesPrice;
    /**
     * 放款金额
     */
    private BigDecimal loanAmount;
    /**
     * 税率
     */
    private BigDecimal taxRate;
    /**
     * 首付款金额
     */
    private BigDecimal downPayment;
}
