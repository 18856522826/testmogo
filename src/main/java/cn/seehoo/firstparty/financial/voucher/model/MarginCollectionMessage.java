package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.beans.beancontext.BeanContext;
import java.io.Serializable;
import java.math.BigDecimal;

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
public class MarginCollectionMessage implements Serializable {
    private static final long serialVersionUID = -9206139129964822188L;

    /**
     * 保证金表编号
     */
    private String  marginId;

    /**
     * 保证金调整金额
     */
    private BigDecimal adjustAmount;

    /**
     * 付款方开户行名称
     */
    private String payerBankName;
    /**
     * 付款方账号
     */
    private String payerAcctNo;
    /**
     * 合作方名称
     */
    private String merchantName;
}
