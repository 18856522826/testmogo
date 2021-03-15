package cn.seehoo.firstparty.financial.voucher.model;

import com.alipay.sofa.sofamq.com.shade.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Notice:
 *
 * @author xuxu
 * @version 1.0
 * @date 2021/3/8
 * @since 1.0
 */
@Data
public class AcctDocGenTransDoc {
    /**
     * 子交易名称
     */
    @JSONField(name = "SubTransName")
    private String SubTransName;
    /**
     * 交易类型
     */
    @JSONField(name = "TransType")
    private String TransType;
    /**
     * 生成日期
     */
    @JSONField(name = "GenerateDate")
    private Date GenerateDate;
    /**
     * 生成时间
     */
    @JSONField(name = "GenerateTime")
    private Date GenerateTime;
    /**
     * 币种
     */
    @JSONField(name = "CCY")
    private String CCY;
    /**
     * 金额
     */
    @JSONField(name = "Amount")
    private BigDecimal Amount;

    /**
     * 剩余本金
     */
    @JSONField(name = "RemanentCapital")
    private BigDecimal RemanentCapital;
    /**
     * 利息
     */
    @JSONField(name = "Interest")
    private BigDecimal Interest;
    /**
     * 总利息
     */
    @JSONField(name = "SumInterest")
    private BigDecimal SumInterest;
    /**
     * 费用
     */
    @JSONField(name = "Fee")
    private BigDecimal Fee;
    /**
     * 支付ID
     */
    @JSONField(name = "PaymentId")
    private String PaymentId;
    /**
     * 摘要
     */
    @JSONField(name = "Summary")
    private String Summary;
    /**
     * 备注
     */
    @JSONField(name = "Remark")
    private String Remark;
    /**
     * 付款方开户行名称
     */
    @JSONField(name = "PayerBankName")
    private String PayerBankName;
    /**
     * 收款方开户行名称
     */
    @JSONField(name = "PayeeBankName")
    private String PayeeBankName;
    /**
     * 付款方账号
     */
    @JSONField(name = "PayerAcctNo")
    private String PayerAcctNo;
    /**
     * 收款方账号
     */
    @JSONField(name = "PayeeAcctNo")
    private String PayeeAcctNo;
    /**
     * 产品名称
     */
    @JSONField(name = "ProductNm")
    private String ProductNm;
    /**
     * 供应商名称
     */
    @JSONField(name = "SuppierNm")
    private String SuppierNm;
    /**
     * 客户
     */
    @JSONField(name = "CustNm")
    private String CustNm;
    /**
     * 平台合作方
     */
    @JSONField(name = "PlatformPartner")
    private String PlatformPartner;
    /**
     * 现金流量
     */
    @JSONField(name = "CashFlow")
    private String CashFlow;
    /**
     * 金融产品
     */
    @JSONField(name = "FinancialProduct")
    private String FinancialProduct;
    /**
     * 税率
     */
    @JSONField(name = "TaxRate")
    private String TaxRate;
    /**
     * 进项税分类
     */
    @JSONField(name = "InputTax")
    private String InputTax;
    /**
     * 是否动产
     */
    @JSONField(name = "IsMovableProperty")
    private String IsMovableProperty;
    /**
     * 往来核算
     */
    @JSONField(name = "CurrentAccounting")
    private String CurrentAccounting;

    /**
     * 期数
     */
    @JSONField(name = "Term")
    private int Term;
    /**
     * 是否冲销
     */
    @JSONField(name = "ChargeAgainstFlag")
    private int ChargeAgainstFlag;
    /**
     * 总期数
     */
    @JSONField(name = "SumTerm")
    private String SumTerm;
    /**
     * 租户编号
     */
    @JSONField(name = "TntInstId")
    private String TntInstId;
    /**
     * 租户名称
     */
    @JSONField(name = "TenantName")
    private String TenantName;
    /**
     * 系统来源
     */
    @JSONField(name = "SystemSource")
    private String SystemSource;
    /**
     * 来源类型
     * 01系统，02手工
     */
    @JSONField(name = "SourceType")
    private String SourceType;
    /**
     * 制单人
     */
    @JSONField(name = "Creator")
    private String Creator;
    /**
     * 审核人
     */
    @JSONField(name = "Auditor")
    private String Auditor;
    /**
     * 出纳
     */
    @JSONField(name = "Cashier")
    private String Cashier;
    /**
     * 过账
     */
    @JSONField(name = "Posting")
    private String Posting;
    /**
     * 固定保证金
     */
    @JSONField(name = "FixedMargin")
    private BigDecimal FixedMargin;
    /**
     * 浮动保证金
     */
    @JSONField(name = "FloatingMargin")
    private BigDecimal FloatingMargin;
    /**
     * 客户保证金
     */
    @JSONField(name = "CustomerMargin")
    private BigDecimal CustomerMargin;
    /**
     * 滞纳金(违约金）
     */
    @JSONField(name = "PenalSum")
    private BigDecimal PenalSum;
    /**
     * 买断价
     */
    @JSONField(name = "BuyoutPrice")
    private BigDecimal BuyoutPrice;
    /**
     * 剩余未收本金
     */
    @JSONField(name = "ResidueUncollectedCapital")
    private BigDecimal ResidueUncollectedCapital;
    /**
     * 剩余未收利息
     */
    @JSONField(name = "ResidueUncollectedInterest")
    private BigDecimal ResidueUncollectedInterest;
    /**
     * 当期未收本金
     */
    @JSONField(name = "PresentUncollectedCapital")
    private BigDecimal PresentUncollectedCapital;
    /**
     * 当期未收利息
     */
    @JSONField(name = "PresentUncollectedInterest")
    private BigDecimal PresentUncollectedInterest;
    /**
     * 未计提利息
     */
    @JSONField(name = "provisionInterest")
    private BigDecimal provisionInterest;
    /**
     * 未计提税金
     */
    @JSONField(name = "provisionTaxes")
    private BigDecimal provisionTaxes;
    /**
     * 收益
     */
    @JSONField(name = "Earnings")
    private BigDecimal Earnings;
    /**
     * 税额
     */
    @JSONField(name = "amountOfTax")
    private BigDecimal amountOfTax;
    /**
     * 销项税额
     */
    @JSONField(name = "outputAmountOfTax")
    private BigDecimal outputAmountOfTax;
    /**
     * 进项税额
     */
    @JSONField(name = "receiptsAmountOfTax")
    private BigDecimal receiptsAmountOfTax;
    /**
     * 保证金类型
     * 01业务保证金，02风险保证金，03承租人保证金
     */
    @JSONField(name = "DepositType")
    private String DepositType;
    /**
     * 不含税金额
     */
    @JSONField(name = "NoTaxAmount")
    private BigDecimal NoTaxAmount;
    /**
     * 合同物品价格
     */
    @JSONField(name = "ContractPrice")
    private BigDecimal ContractPrice;
    /**
     * 不含税合同物品价格
     */
    @JSONField(name = "NoTaxContractPrice")
    private BigDecimal NoTaxContractPrice;
    /**
     * 物品架税额
     */
    @JSONField(name = "GoodsTax")
    private BigDecimal GoodsTax;
    /**
     * 租金（含税）
     */
    @JSONField(name = "IncludeTaxRent")
    private BigDecimal IncludeTaxRent;
    /**
     * 不含税租金
     */
    @JSONField(name = "NoTaxRent")
    private BigDecimal NoTaxRent;
    /**
     *租金税额
     */
    @JSONField(name = "TaxRent")
    private BigDecimal TaxRent;
    /**
     *总本金
     */
    @JSONField(name = "TotalCapital")
    private BigDecimal TotalCapital;
    /**
     *不含税总本金
     */
    @JSONField(name = "NoTaxTotalCapital")
    private BigDecimal NoTaxTotalCapital;
    /**
     *总本金税额
     */
    @JSONField(name = "TaxTotalCapital")
    private BigDecimal TaxTotalCapital;
    /**
     *本金（含税）
     */
    @JSONField(name = "IncludeCapital")
    private BigDecimal IncludeCapital;
    /**
     *不含税本金
     */
    @JSONField(name = "NoTaxCapital")
    private BigDecimal NoTaxCapital;
    /**
     *本金税额
     */
    @JSONField(name = "TaxCapital")
    private BigDecimal TaxCapital;
    /**
     *不含税剩余本金
     */
    @JSONField(name = "NoTaxOddCorpus")
    private BigDecimal NoTaxOddCorpus;
    /**
     *剩余本金税额
     */
    @JSONField(name = "TaxOddCorpus")
    private BigDecimal TaxOddCorpus;
    /**
     *不含税利息
     */
    @JSONField(name = "NoTaxInterest")
    private BigDecimal NoTaxInterest;
    /**
     *利息税额
     */
    @JSONField(name = "TaxInterest")
    private BigDecimal TaxInterest;
    /**
     *总利息税额
     */
    @JSONField(name = "GrossInterest")
    private BigDecimal GrossInterest;
    /**
     *不含税费用
     */
    @JSONField(name = "NoTaxFee")
    private BigDecimal NoTaxFee;
    /**
     *费用税额
     */
    @JSONField(name = "TaxFee")
    private BigDecimal TaxFee;
    /**
     *不含税固定保证金
     */
    @JSONField(name = "NOTaxFixedDeposit")
    private BigDecimal NOTaxFixedDeposit;
    /**
     *固定保证金税额
     */
    @JSONField(name = "TaxFixedDeposit")
    private BigDecimal TaxFixedDeposit;
    /**
     *不含税浮动保证金
     */
    @JSONField(name = "NoTaxFloatingDeposit")
    private BigDecimal NoTaxFloatingDeposit;
    /**
     *浮动保证金税额
     */
    @JSONField(name = "TaxFloatingDeposit")
    private BigDecimal TaxFloatingDeposit;
    /**
     *不含税客户保证金
     */
    @JSONField(name = "NoTaxCustomerDeposit")
    private BigDecimal NoTaxCustomerDeposit;
    /**
     *客户保证金税额
     */
    @JSONField(name = "TaxCustomerDeposit")
    private BigDecimal TaxCustomerDeposit;
    /**
     *不含税滞纳金
     */
    @JSONField(name = "NoTaxLateFee")
    private BigDecimal NoTaxLateFee;
    /**
     *滞纳金税额
     */
    @JSONField(name = "TaxLateFee")
    private BigDecimal TaxLateFee;
    /**
     *不含税买断价
     */
    @JSONField(name = "NoTaxBuyoutPrice")
    private BigDecimal NoTaxBuyoutPrice;
    /**
     *买断价税额
     */
    @JSONField(name = "TaxBuyoutPrice")
    private BigDecimal TaxBuyoutPrice;
    /**
     *不含税剩余未收本金
     */
    @JSONField(name = "NoTaxResidueCapital")
    private BigDecimal NoTaxResidueCapital;
    /**
     *剩余未收本金税额
     */
    @JSONField(name = "TaxResidueCapital")
    private BigDecimal TaxResidueCapital;
    /**
     *不含税剩余未收利息
     */
    @JSONField(name = "NoTaxResidueInterest")
    private BigDecimal NoTaxResidueInterest;
    /**
     *剩余未收利息税额
     */
    @JSONField(name = "TaxResidueInterest")
    private BigDecimal TaxResidueInterest;
    /**
     *不含税当期未收本金
     */
    @JSONField(name = "NoTaxPresentCapital")
    private BigDecimal NoTaxPresentCapital;
    /**
     *当期未收本金税额
     */
    @JSONField(name = "TaxPresentCapital")
    private BigDecimal TaxPresentCapital;
    /**
     * 不含税当期未收利息
     */
    @JSONField(name = "NoTaxPresentInterest")
    private BigDecimal NoTaxPresentInterest;
    /**
     * 当期未收利息税额
     */
    @JSONField(name = "TaxPresentInterest")
    private BigDecimal TaxPresentInterest;
    /**
     * 现金流量金额
     */
    @JSONField(name = "FlowsMoney")
    private BigDecimal FlowsMoney;
    /**
     * 现金流量金额不含税
     */
    @JSONField(name = "NoTaxFlowsMoney")
    private BigDecimal NoTaxFlowsMoney;
    /**
     * 现金流量金额税额
     */
    @JSONField(name = "TaxFlowsMoney")
    private BigDecimal TaxFlowsMoney;
}
