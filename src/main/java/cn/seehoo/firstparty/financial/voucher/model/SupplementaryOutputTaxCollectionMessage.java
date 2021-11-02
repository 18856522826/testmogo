package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Notice:
 *
 * @author xuxinhui
 * @version 1.0
 * @date 2021/11/01
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SupplementaryOutputTaxCollectionMessage extends CommonMessage {

    /**
     * 是否冲销，0正常 1 红冲 2蓝冲
     */
    private String isChargeAgainst;

    /**
     * 租赁属性
     */
    private String leaseType;

    /**
     * 合同号码
     */
    private String contractNo;

    /**
     * 客户姓名
     */
    private String custName;
    /**
     * 计提收入金额
     */
    private BigDecimal amount;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 3.0业务取05、4.0业务取06
     */
    private String businessType;

    /**
     * 直租：13 回租6
     */
    private BigDecimal taxRate;

    /**
     * 融资期限
     */
    private Integer loanTerm;

}
