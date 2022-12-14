package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Notice: 取得资产场景所需财务信息
 *
 * @author lix
 * @version 1.0
 * @date 2021/3/15
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class AssetCollectionMessage extends CommonMessage{
    private static final long serialVersionUID = 5978224537254233009L;
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
     * 剩余不含税本金
     */
    private BigDecimal principalExcludeTax;
    /**
     * 剩余本金税额
     */
    private BigDecimal principalTax;
    /**
     * 当前剩余未还利息
     */
    private BigDecimal surplusInterest;
    /**
     * 合同总利息
     */
    private BigDecimal sumInterest;
    /**
     * 付款方开户行名称
     */
    private String payerBankName;
    /**
     * 付款方账号
     */
    private String payerAcctNo;
    /**
     * 客户姓名
     */
    private String custName;
    /**
     * 贷款期数(月)
     */
    private Integer loanTerm;
    /**
     * 税率
     */
    private BigDecimal taxRate;

}
