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
     * 租赁类型
     */
    private String leaseType;
    /**
     * 预付保证金Id
     */
    private String depositId;
    /**
     * 对应核销金额
     */
    private BigDecimal marginAmount;
}
