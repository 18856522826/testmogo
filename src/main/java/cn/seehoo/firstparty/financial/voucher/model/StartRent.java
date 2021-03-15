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
public class StartRent {
    /**
     * 销售还款计划表租金和
     */
    private String amount;
    /**
     * 销售还款计划表除第0期外剩余本金总和
     */
    private String remanentCapital;
    /**
     * 销售还款计划表除第0期外剩余利息总和
     */
    private String interest;
    /**
     * 销售还款计划表除第0期外剩余利息总和
     */
    private String sumInterest;
    /**
     * 留购价款金额
     */
    private String fee;
    /**
     * 主表产品方案
     */
    private String financialProduct;
    /**
     * 税率
     */
    private String taxRate;
    /**
     * 主表融资期限
     */
    private String term;
    /**
     * 留购价款
     */
    private String buyoutPrice;
    /**
     * 销售还款计划表除0期外本金总额
     */
    private BigDecimal residueUncollectedCapital;
    /**
     * 销售还款计划表除0期外利息总额
     */
    private BigDecimal residueUncollectedInterest;
    /**
     * 销售还款计划表除0期外利息总额
     */
    private BigDecimal provisionInterest;
    /**
     * 销售还款计划表除0期外税金总和
     */
    private BigDecimal provisionTaxes;
    /**
     * 销售还款计划表除0期外税后利息总额
     */
    private BigDecimal earnings;
    /**
     * 销售还款计划表除0期外利息税额总额
     */
    private BigDecimal amountOfTax;
    /**
     * 销售还款计划表税后租金总和
     */
    private BigDecimal noTaxAmount;
    /**
     * 车辆销售价
     */
    private BigDecimal contractPrice;
    /**
     * 放款金额
     */
    private BigDecimal totalCapital;
    /**
     * 不含税买断价
     */
    private BigDecimal NoTaxBuyoutPrice;
    /**
     * 买断价税额
     */
    private BigDecimal TaxBuyoutPrice;
    /**
     * 不含税剩余未收本金
     */
    private BigDecimal NoTaxResidueCapital;


}
