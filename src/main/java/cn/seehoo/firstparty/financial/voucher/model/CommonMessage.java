package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Notice: 通用的财务凭证信息
 *
 * @author Staunch
 * @version 1.0
 * @date 2021/3/15
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class CommonMessage implements Serializable {
    private static final long serialVersionUID = -3648736660576333255L;

    /**
     * 申请编号
     */
    private String orderNo;
    /**
     * 合同号码
     */
    private String contractNo;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 租赁属性
     */
    private String leaseType;
    /**
     * 放款金额
     */
    private BigDecimal loanAmount;
    /**
     * 当前剩余未还本金
     */
    private BigDecimal surplusPrincipal;
    /**
     * 当前剩余未还利息
     */
    private BigDecimal surplusInterest;
    /**
     * 合同总利息
     */
    private BigDecimal sumInterest;
    /**
     * 支付ID
     */
    private BigDecimal paymentId;
    /**
     * 付款方开户行名称
     */
    private String payerBankName;
    /**
     * 付款方账号
     */
    private String payerAcctNo;
    /**
     * 店面名称
     */
    private String storeName;
    /**
     * 渠道名称
     */
    private String merchantName;
    /**
     * 客户姓名
     */
    private String custName;
    /**
     * 税率
     */
    private BigDecimal taxRate;
    /*
     * 贷款期数(月)
     */
    private String loanTerm;

}
