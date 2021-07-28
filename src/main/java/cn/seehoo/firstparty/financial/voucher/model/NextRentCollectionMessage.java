package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Notice:预收下期租金
 *
 * @author xuxu
 * @version 1.0
 * @date 2021/6/23
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class NextRentCollectionMessage extends CommonMessage{
    private static final long serialVersionUID = -951431458156064082L;
    /**
     * 租赁类型
     */
    private String leaseType;
    /**
     * 合同号码
     */
    private String contractNo;
    /**
     * 对应收款金额
     */
    private BigDecimal correspondPaymentAmount;
    /**
     *对应收款利息
     */
    private BigDecimal correspondInterest;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 客户姓名
     */
    private String custName;
    /*
     * 当前期数
     */
    private Integer currentTerm;
    /*
     * 贷款期数(月)
     */
    private Integer loanTerm;
    /**
     * 税率
     */
    private BigDecimal taxRate;
    /**
     * 账户业务模式
     */
    private String bizType;
    /**
     * 账户用途
     */
    private String useType;
    /**
     *付款方开户行名称
     */
    private String payerBankName;
    /**
     *付款方账号
     */
    private String payerAcctNo;
}
