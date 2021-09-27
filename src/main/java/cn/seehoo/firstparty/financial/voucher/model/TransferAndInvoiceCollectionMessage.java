package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Notice:
 *
 * @author xuxu
 * @version 1.0
 * @date 2021/9/27
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TransferAndInvoiceCollectionMessage extends CommonMessage {
    /**
     * 合同号码
     */
    private String contractNo;
    /**
     *归属国银的本金税额总和
     */
    private BigDecimal amount;
    /**
     * 日期
     */
    private Date date;
    /**
     * 客户姓名
     */
    private String custName;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 税率
     */
    private BigDecimal taxRate;
    /**
     * 贷款期数(月)
     */
    private Integer loanTerm;
}
