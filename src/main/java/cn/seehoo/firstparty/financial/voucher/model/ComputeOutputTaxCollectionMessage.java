package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

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
public class ComputeOutputTaxCollectionMessage extends CommonMessage{
    /**
     * 租赁类型
     */
    private String leaseType;
    /**
     * 合同号码
     */
    private String contractNo;
    /**
     * 还款计划表对应期数的利息税额
     */
    private BigDecimal interestTax;
    /**
     * 还款计划表对应期数的利息
     */
    private BigDecimal interest;
    /**
     * 主表产品名称
     */
    private String productName;
    /**
     * 客户姓名
     */
    private String custName;
    /**
     * 还款计划表对应期数
     */
    private Integer currentTerm;
    /**
     *主表融租期限
     */
    private Integer loanTerm;
    /**
     * 税率
     */
    private BigDecimal taxRate;
}
