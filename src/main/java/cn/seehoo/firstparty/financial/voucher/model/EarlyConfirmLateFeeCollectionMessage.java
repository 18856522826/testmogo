package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Notice:
 *
 * @author xuxu
 * @version 1.0
 * @date 2021/11/1
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EarlyConfirmLateFeeCollectionMessage extends CommonMessage{

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
     * 产品名称
     */
    private String productName;

    /**
     * 直租：13 回租6
     */
    private BigDecimal taxRate;

    /**
     * 融资期限
     */
    private Integer loanTerm;
    /**
     * 提前结清滞纳金金额
     */
    private BigDecimal penalSum;
    /**
     * 提前结清滞纳金金额(不含税)
     */
    private BigDecimal NoTaxLateFee;
    /**
     * 提前结清滞纳金金额(税额)
     */
    private BigDecimal TaxLateFee;
}