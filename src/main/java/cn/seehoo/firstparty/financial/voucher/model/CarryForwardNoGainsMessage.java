package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Notice:
 *
 * @author xuxu
 * @version 1.0
 * @date 2022/1/12
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CarryForwardNoGainsMessage extends CommonMessage {
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
     * 当期利息
     */
    private BigDecimal amount;
    /**
     * 当期利息税额
     */
    private BigDecimal amountTax;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 主表融资期限
     */
    private int sumTerm;
    /**
     * 当前期数
     */
    private int currentTerm;

    /**
     * 直租：13 回租6
     */
    private BigDecimal taxRate;

    /**
     *付款方开户行名称
     */
    private String payerBankName;
    /**
     *付款方账号
     */
    private String payerAcctNo;
    /**
     * 账户key
     */
    private String bizUseType;
    /**
     * 收款方开户行名称（即收款银行）
     */
    private String payeeBankName;

    /**
     * 收款方账号（即收款账号）
     */
    private String payeeAcctNo;
}
