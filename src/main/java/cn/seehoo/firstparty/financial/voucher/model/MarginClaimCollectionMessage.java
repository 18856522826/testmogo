package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Notice: 保证金认领
 *
 * @author xuxu
 * @version 1.0
 * @date 2021/6/23
 * @since 1.0
 */
@Getter
@Setter
@ToString(callSuper = true)
public class MarginClaimCollectionMessage extends CommonMessage {
    private static final long serialVersionUID = -1928335018395843762L;
    /**
     * 保证金调整类型 金额增加为 0-正常，金额减少为 1-红冲
     */
    private String adjustType;
    /**
     * 预付保证金Id
     */
    private String depositId;
    /**
     * 对应核销金额
     */
    private BigDecimal marginAmount;

    /**
     * 付款方开户行名称
     */
    private String payerBankName;
    /**
     * 付款方账号
     */
    private String payerAcctNo;
    /**
     *收款方开户行名称
     */
    private String payeeBankName;
    /**
     *收款方账号
     */
    private String payeeAcctNo;

}
