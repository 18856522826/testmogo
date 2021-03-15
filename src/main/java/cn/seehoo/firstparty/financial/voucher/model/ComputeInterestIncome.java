package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
public class ComputeInterestIncome {
    /**
     * 财务还款计划表当月租金总和
     */
    private String amount;
    /**
     * 主表产品名称
     */
    private String productNm;
    /**
     * 合作方名称
     */
    private String suppierNm;
    /**
     * 客户姓名
     */
    private String custName;
    /**
     * 合作方名称
     */
    private String platformPartner;
    /**
     * 直租：13 回租：6 税率
     */
    private String taxRate;
    /**
     * 是否动产
     */
    private String isMovableProperty;
    /**
     * 平台合作方(往来核算)
     */
    private String currentAccounting;
    /**
     * 还款计划表对应期数
     */
    private int term;
    /**
     * 主表融资期限
     */
    private String sumTerm;
    /**
     * 留购价款
     */
    private String buyoutPrice;
    /**
     * 财务还款计划表本月后剩余利息总和
     */
    private String provisionInterest;
    /**
     * 财务还款计划表本月后剩余利息税金总和
     */
    private String provisionTaxes;
    /**
     * 收益
     */
    private String earnings;
    /**
     * 财务还款计划表当月租金税额总和
     */
    private String amountOfTax;
}
