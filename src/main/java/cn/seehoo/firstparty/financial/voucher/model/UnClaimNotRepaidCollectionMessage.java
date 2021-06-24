package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Notice: 未被认领-确实无法偿付的其他应付款项
 *
 * @author xuxu
 * @version 1.0
 * @date 2021/6/23
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class UnClaimNotRepaidCollectionMessage extends CommonMessage{
    private static final long serialVersionUID = 820503673857569325L;
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
}
