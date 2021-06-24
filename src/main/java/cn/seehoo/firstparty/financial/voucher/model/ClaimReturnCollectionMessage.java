package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Notice: 被认领-选择退回场景
 *
 * @author xuxu
 * @version 1.0
 * @date 2021/6/23
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class ClaimReturnCollectionMessage extends CommonMessage {
    private static final long serialVersionUID = 1242045055236347953L;
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
     * 税率
     */
    private BigDecimal taxRate;
}
