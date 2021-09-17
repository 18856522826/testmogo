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
public class MarginCollectionMessage extends CommonMessage implements Serializable {
    private static final long serialVersionUID = -9206139129964822188L;
    /**
     * 保证金调整类型 金额增加为 0-正常，金额减少为 1-红冲
     */
    private String adjustType;
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
     *收款方开户行名称
     */
    private String payeeBankName;
    /**
     *收款方账号
     */
    private String payeeAcctNo;
}
