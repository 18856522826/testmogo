package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Notice: 逐期结转场景所需财务信息
 *
 * @author xuxu
 * @version 1.0
 * @date 2021/6/22
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class CarryForwardCollectionMessage extends CommonMessage{
    private static final long serialVersionUID = 6461032442288834155L;
    /**
     * 租赁类型
     */
    private String leaseType;
    /**
     * 合同号码
     */
    private String contractNo;
    /**
     * 租金金额
     */
    private BigDecimal rentAmount;
    /**
     *利息
     */
    private BigDecimal interest;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 客户姓名
     */
    private String custName;
    /*
     * 当前期数
     */
    private Integer currentTerm;
    /*
     * 贷款期数(月)
     */
    private Integer loanTerm;
    /**
     * 税率
     */
    private BigDecimal taxRate;
}
