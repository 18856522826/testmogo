package cn.seehoo.firstparty.financial.voucher.model.basic;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Notice:
 *
 * @author xuxu
 * @version 1.0
 * @date 2022/8/18
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommonFinBasicAccountAttr extends CommonFinBasicAttr {
    private static final long serialVersionUID = 1071653285790596117L;
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
