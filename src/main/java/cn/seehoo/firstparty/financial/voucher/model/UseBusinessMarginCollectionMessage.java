package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Notice: 动用业务保证金-代偿
 *
 * @author xuxu
 * @version 1.0
 * @date 2021/6/24
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class UseBusinessMarginCollectionMessage extends CommonMessage {
    private static final long serialVersionUID = 6350552861866735620L;
    /**
     * 租赁类型
     */
    private String leaseType;
    /**
     * 合同号码
     */
    private String contractNo;
    /**
     * 对应业务保证金金额
     */
    private BigDecimal businessMarginAmount;
    /**
     *对应利息
     */
    private BigDecimal correspondInterest;
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
    /**
     * 是否返还
     */
    private String exchanged;
}
