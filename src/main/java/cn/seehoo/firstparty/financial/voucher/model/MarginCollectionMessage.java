package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Notice: 保证金收取场景所需财务信息
 *
 * @author Staunch
 * @version 1.0
 * @date 2021/3/15
 * @since 1.0
 */
@Getter
@Setter
@ToString(callSuper = true)
public class MarginCollectionMessage extends CommonMessage{
    private static final long serialVersionUID = -9206139129964822188L;

    /**
     * 交易类型 直租：0010101 回租：0010201
     */
    private String  transType;
}
