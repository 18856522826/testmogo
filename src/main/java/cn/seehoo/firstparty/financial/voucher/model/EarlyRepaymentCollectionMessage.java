package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Notice: 提前结清-结平
 *
 * @author xuxu
 * @version 1.0
 * @date 2021/11/1
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EarlyRepaymentCollectionMessage extends CommonMessage{
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
     * 剩余未收利息
     */
    private BigDecimal notChargeInterest;
    /**
     * 剩余未收本金
     */
    private BigDecimal notChargePrincipal;
    /**
     * 当期未收本金
     */
    private BigDecimal currentNotChargePrincipal;
    /**
     * 当期未收利息
     */
    private BigDecimal currentNotChargeInterest;
    /**
     * 收益
     */
    private BigDecimal earnings;
    /**
     * 租金
     */
    private BigDecimal includeTaxRent;
    /**
     * 利息税额
     */
    private BigDecimal taxInterest;
}
