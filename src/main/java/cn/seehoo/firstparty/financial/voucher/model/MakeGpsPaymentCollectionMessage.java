package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Notice:支付价款（GPS）场景所需财务信息
 *
 * @author lix
 * @version 1.0
 * @date 2021/3/15
 * @since 1.0
 */
@Getter
@Setter
@ToString(callSuper = true)
public class MakeGpsPaymentCollectionMessage extends CommonMessage{
    private static final long serialVersionUID = -2073796043447418208L;
    /**
     * 租赁属性
     */
    private String leaseType;
    /**
     * 合同号码
     */
    private String contractNo;
    /**
     * 放款金额
     */
    private BigDecimal loanAmount;
    /**
     * 付款方开户行名称
     */
    private String payerBankName;
    /**
     * 付款方账号
     */
    private String payerAcctNo;
    /**
     * 收款方开户行名称
     */
    private String payeeBankName;
    /**
     * 收款方账号
     */
    private String payeeAcctNo;
}
