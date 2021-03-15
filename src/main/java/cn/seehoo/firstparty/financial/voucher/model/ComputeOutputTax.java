package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Notice: 计算销项税
 *
 * @author xuxu
 * @version 1.0
 * @date 2021/3/15
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class ComputeOutputTax {
    /**
     * 还款计划表对应期数的利息税额
     */
    private String amount;
    /**
     * 还款计划表对应期数的利息
     */
    private String interest;
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
}
