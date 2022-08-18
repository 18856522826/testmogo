package cn.seehoo.firstparty.financial.voucher.model;

import cn.seehoo.firstparty.financial.voucher.model.basic.CommonFinBasicAttr;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Notice:计提补偿利息场景所需财务信息
 *
 * @author lix
 * @version 1.0
 * @date 2021/3/15
 * @since 1.0
 */
@Getter
@Setter
@ToString(callSuper = true)
public class AccrualDiscountCollectionMessage extends CommonFinBasicAttr {
    private static final long serialVersionUID = -2073796043447418208L;
    /**
     * 补偿利息税额
     */
    private BigDecimal discountTaxAmount;
}
