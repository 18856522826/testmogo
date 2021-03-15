package cn.seehoo.firstparty.financial.voucher.model;

import java.math.BigDecimal;

/**
 * Notice:支付价款场景所需财务信息
 *
 * @author lix
 * @version 1.0
 * @date 2021/3/15
 * @since 1.0
 */
public class MakePaymentCollectionMessage {

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 放款金额
     */
    private BigDecimal loanAmount;
    /**
     * 当前剩余未还本金
     */
    private BigDecimal surplusPrincipal;
    /**
     * 当前剩余未还利息
     */
    private BigDecimal surplusInterest;
    /**
     * 合同总利息
     */
    private BigDecimal sumInterest;

    /**
     * 收款方开户行名称
     */
    private String payerBankName;
    /**
     * 收款方账号
     */
    private String payeAcctNo;
}
