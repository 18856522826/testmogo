package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Notice:首付款计入融资租赁资产
 *
 * @author lix
 * @version 1.0
 * @date 2021/5/12
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DownPaymentInAssetsCollectionMessage extends CommonMessage{
    private static final long serialVersionUID = -2736372259038504709L;
    /**
     * 租赁属性
     */
    private String leaseType;
    /**
     * 合同号码
     */
    private String contractNo;
    /**
     * 首付款金额
     */
    private BigDecimal downPayment;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 客户姓名
     */
    private String custName;
    /**
     * 税率
     */
    private BigDecimal taxRate;
    /*
     * 当前期数
     */
    private Integer currentTerm;
    /*
     * 贷款期数(月)
     */
    private Integer loanTerm;
}
