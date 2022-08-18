package cn.seehoo.firstparty.financial.voucher.model;

import cn.seehoo.firstparty.financial.voucher.model.basic.CommonFinBasicAccountAttr;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Notice:支付价款(补偿利息)场景所需财务信息
 *
 * @author lix
 * @version 1.0
 * @date 2021/3/15
 * @since 1.0
 */
@Getter
@Setter
@ToString(callSuper = true)
public class MakeDiscountPaymentCollectionMessage extends CommonFinBasicAccountAttr {
    private static final long serialVersionUID = -2073796043447418208L;
    /**
     * 放款金额
     */
    private BigDecimal loanAmount;
    /**
     * 补偿利息金额
     */
    private BigDecimal discountAmount;
}
