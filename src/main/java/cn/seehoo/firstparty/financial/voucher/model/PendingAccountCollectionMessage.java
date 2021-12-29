package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Notice: 暂挂账
 *
 * @author xuxu
 * @version 1.0
 * @date 2021/6/23
 * @since 1.0
 */
@Getter
@Setter
@ToString(callSuper = true)
public class PendingAccountCollectionMessage extends CommonMessage{
    private static final long serialVersionUID = 2156294386640410646L;
    /**
     * 租赁类型
     */
    private String leaseType;
    /**
     * 收款明细ID
     */
    private String collectionDetailsId;
    /**
     * 对应挂账金额
     */
    private BigDecimal correspondAccountAmount;
    /**
     *付款方开户行名称
     */
    private String payerBankName;
    /**
     *付款方账号
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
    /**
     * 客户姓名
     */
    private String custName;
}
