package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Notice: 资金调拨_提现
 *
 * @author xuxu
 * @version 1.0
 * @date 2021/7/27
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class FundTransferWithdrawCollectionMessage extends CommonMessage{
    private static final long serialVersionUID = 8459163915380127318L;
    /**
     * 租赁类型
     */
    private String leaseType;
    /**
     * 收款明细ID
     */
    private String collectionDetailsId;
    /**
     * 对应金额
     */
    private BigDecimal correspondAmount;
    /**
     * 对应利息
     */
    private BigDecimal correspondInterest;
    /**
     * 税率
     */
    private BigDecimal taxRate;

}