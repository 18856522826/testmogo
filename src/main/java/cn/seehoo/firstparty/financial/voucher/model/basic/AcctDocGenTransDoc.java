package cn.seehoo.firstparty.financial.voucher.model.basic;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Notice: 制证子交易流水
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
    private String subTransName;
    /**
     * 交易类型
     */
    @JSONField(name = "TransType")
    private String transType;
    /**
     * 生成日期
     */
    @JSONField(name = "GenerateDate")
    private Date generateDate;
    /**
     * 生成时间
     */
    @JSONField(name = "GenerateTime")
    private Date generateTime;
    /**
     * 币种
     */
    @JSONField(name = "CCY")
    private String ccy;
    /**
     * 金额
     */
    @JSONField(name = "Amount")
    private BigDecimal amount;

    /**
     * 剩余本金
     */
    @JSONField(name = "RemanentCapital")
    private BigDecimal remanentCapital;
    /**
     * 利息
     */
    @JSONField(name = "Interest")
    private BigDecimal interest;
    /**
     * 总利息
     */
    @JSONField(name = "SumInterest")
    private BigDecimal sumInterest;
    /**
     * 费用
     */
    @JSONField(name = "Fee")
    private BigDecimal fee;
    /**
     * 支付ID
     */
    @JSONField(name = "PaymentId")
    private String paymentId;
    /**
     * 摘要
     */
    @JSONField(name = "Summary")
    private String summary;
    /**
     * 备注
     */
    @JSONField(name = "Remark")
    private String remark;
    /**
     * 付款方开户行名称
     */
    @JSONField(name = "PayerBankName")
    private String payerBankName;
    /**
     * 收款方开户行名称
     */
    @JSONField(name = "PayeeBankName")
    private String payeeBankName;
    /**
     * 付款方账号
     */
    @JSONField(name = "PayerAcctNo")
    private String payerAcctNo;
    /**
     * 收款方账号
     */
    @JSONField(name = "PayeeAcctNo")
    private String PayeeAcctNo;
    /**
     * 产品名称
     */
    @JSONField(name = "ProductNm")
    private String productNm;
    /**
     * 供应商名称
     */
    @JSONField(name = "SuppierNm")
    private String suppierNm;
    /**
     * 客户
     */
    @JSONField(name = "CustNm")
    private String custNm;
    /**
     * 平台合作方
     */
    @JSONField(name = "PlatformPartner")
    private String platformPartner;
    /**
     * 现金流量
     */
    @JSONField(name = "CashFlow")
    private String cashFlow;
    /**
     * 金融产品
     */
    @JSONField(name = "FinancialProduct")
    private String financialProduct;
    /**
     * 税率
     */
    @JSONField(name = "TaxRate")
    private String taxRate;
    /**
     * 进项税分类
     */
    @JSONField(name = "InputTax")
    private String inputTax;
    /**
     * 是否动产
     */
    @JSONField(name = "IsMovableProperty")
    private String isMovableProperty;
    /**
     * 往来核算
     */
    @JSONField(name = "CurrentAccounting")
    private String currentAccounting;

    /**
     * 期数
     */
    @JSONField(name = "Term")
    private int term;
    /**
     * 是否冲销
     */
    @JSONField(name = "ChargeAgainstFlag")
    private int chargeAgainstFlag;
    /**
     * 总期数
     */
    @JSONField(name = "SumTerm")
    private String sumTerm;
    /**
     * 租户编号
     */
    @JSONField(name = "TntInstId")
    private String tntInstId;
    /**
     * 租户名称
     */
    @JSONField(name = "TenantName")
    private String tenantName;
    /**
     * 系统来源
     */
    @JSONField(name = "SystemSource")
    private String systemSource;
    /**
     * 来源类型
     * 01系统，02手工
     */
    @JSONField(name = "SourceType")
    private String sourceType;
    /**
     * 制单人
     */
    @JSONField(name = "Creator")
    private String creator;
    /**
     * 审核人
     */
    @JSONField(name = "Auditor")
    private String auditor;
    /**
     * 出纳
     */
    @JSONField(name = "Cashier")
    private String cashier;
    /**
     * 过账
     */
    @JSONField(name = "Posting")
    private String posting;
    /**
     * 固定保证金
     */
    @JSONField(name = "FixedMargin")
    private BigDecimal fixedMargin;
    /**
     * 浮动保证金
     */
    @JSONField(name = "FloatingMargin")
    private BigDecimal floatingMargin;
    /**
     * 客户保证金
     */
    @JSONField(name = "CustomerMargin")
    private BigDecimal customerMargin;
    /**
     * 滞纳金(违约金）
     */
    @JSONField(name = "PenalSum")
    private BigDecimal penalSum;
    /**
     * 买断价
     */
    @JSONField(name = "BuyoutPrice")
    private BigDecimal buyoutPrice;
    /**
     * 剩余未收本金
     */
    @JSONField(name = "ResidueUncollectedCapital")
    private BigDecimal residueUncollectedCapital;
    /**
     * 剩余未收利息
     */
    @JSONField(name = "ResidueUncollectedInterest")
    private BigDecimal residueUncollectedInterest;
    /**
     * 当期未收本金
     */
    @JSONField(name = "PresentUncollectedCapital")
    private BigDecimal presentUncollectedCapital;
    /**
     * 当期未收利息
     */
    @JSONField(name = "PresentUncollectedInterest")
    private BigDecimal presentUncollectedInterest;
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
    private BigDecimal earnings;
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
    private String depositType;
    /**
     * 不含税金额
     */
    @JSONField(name = "NoTaxAmount")
    private BigDecimal noTaxAmount;
    /**
     * 合同物品价格
     */
    @JSONField(name = "ContractPrice")
    private BigDecimal contractPrice;
    /**
     * 不含税合同物品价格
     */
    @JSONField(name = "NoTaxContractPrice")
    private BigDecimal noTaxContractPrice;
    /**
     * 物品架税额
     */
    @JSONField(name = "GoodsTax")
    private BigDecimal goodsTax;
    /**
     * 租金（含税）
     */
    @JSONField(name = "IncludeTaxRent")
    private BigDecimal includeTaxRent;
    /**
     * 不含税租金
     */
    @JSONField(name = "NoTaxRent")
    private BigDecimal noTaxRent;
    /**
     *租金税额
     */
    @JSONField(name = "TaxRent")
    private BigDecimal taxRent;
    /**
     *总本金
     */
    @JSONField(name = "TotalCapital")
    private BigDecimal totalCapital;
    /**
     *不含税总本金
     */
    @JSONField(name = "NoTaxTotalCapital")
    private BigDecimal noTaxTotalCapital;
    /**
     *总本金税额
     */
    @JSONField(name = "TaxTotalCapital")
    private BigDecimal taxTotalCapital;
    /**
     *本金（含税）
     */
    @JSONField(name = "IncludeCapital")
    private BigDecimal includeCapital;
    /**
     *不含税本金
     */
    @JSONField(name = "NoTaxCapital")
    private BigDecimal noTaxCapital;
    /**
     *本金税额
     */
    @JSONField(name = "TaxCapital")
    private BigDecimal taxCapital;
    /**
     *不含税剩余本金
     */
    @JSONField(name = "NoTaxOddCorpus")
    private BigDecimal noTaxOddCorpus;
    /**
     *剩余本金税额
     */
    @JSONField(name = "TaxOddCorpus")
    private BigDecimal taxOddCorpus;
    /**
     *不含税利息
     */
    @JSONField(name = "NoTaxInterest")
    private BigDecimal noTaxInterest;
    /**
     *利息税额
     */
    @JSONField(name = "TaxInterest")
    private BigDecimal taxInterest;
    /**
     *总利息税额
     */
    @JSONField(name = "GrossInterest")
    private BigDecimal grossInterest;
    /**
     *不含税费用
     */
    @JSONField(name = "NoTaxFee")
    private BigDecimal noTaxFee;
    /**
     *费用税额
     */
    @JSONField(name = "TaxFee")
    private BigDecimal taxFee;
    /**
     *不含税固定保证金
     */
    @JSONField(name = "NOTaxFixedDeposit")
    private BigDecimal noTaxFixedDeposit;
    /**
     *固定保证金税额
     */
    @JSONField(name = "TaxFixedDeposit")
    private BigDecimal taxFixedDeposit;
    /**
     *不含税浮动保证金
     */
    @JSONField(name = "NoTaxFloatingDeposit")
    private BigDecimal noTaxFloatingDeposit;
    /**
     *浮动保证金税额
     */
    @JSONField(name = "TaxFloatingDeposit")
    private BigDecimal taxFloatingDeposit;
    /**
     *不含税客户保证金
     */
    @JSONField(name = "NoTaxCustomerDeposit")
    private BigDecimal noTaxCustomerDeposit;
    /**
     *客户保证金税额
     */
    @JSONField(name = "TaxCustomerDeposit")
    private BigDecimal taxCustomerDeposit;
    /**
     *不含税滞纳金
     */
    @JSONField(name = "NoTaxLateFee")
    private BigDecimal noTaxLateFee;
    /**
     *滞纳金税额
     */
    @JSONField(name = "TaxLateFee")
    private BigDecimal taxLateFee;
    /**
     *不含税买断价
     */
    @JSONField(name = "NoTaxBuyoutPrice")
    private BigDecimal noTaxBuyoutPrice;
    /**
     *买断价税额
     */
    @JSONField(name = "TaxBuyoutPrice")
    private BigDecimal taxBuyoutPrice;
    /**
     *不含税剩余未收本金
     */
    @JSONField(name = "NoTaxResidueCapital")
    private BigDecimal noTaxResidueCapital;
    /**
     *剩余未收本金税额
     */
    @JSONField(name = "TaxResidueCapital")
    private BigDecimal taxResidueCapital;
    /**
     *不含税剩余未收利息
     */
    @JSONField(name = "NoTaxResidueInterest")
    private BigDecimal noTaxResidueInterest;
    /**
     *剩余未收利息税额
     */
    @JSONField(name = "TaxResidueInterest")
    private BigDecimal taxResidueInterest;
    /**
     *不含税当期未收本金
     */
    @JSONField(name = "NoTaxPresentCapital")
    private BigDecimal noTaxPresentCapital;
    /**
     *当期未收本金税额
     */
    @JSONField(name = "TaxPresentCapital")
    private BigDecimal taxPresentCapital;
    /**
     * 不含税当期未收利息
     */
    @JSONField(name = "NoTaxPresentInterest")
    private BigDecimal noTaxPresentInterest;
    /**
     * 当期未收利息税额
     */
    @JSONField(name = "TaxPresentInterest")
    private BigDecimal taxPresentInterest;
    /**
     * 现金流量金额
     */
    @JSONField(name = "FlowsMoney")
    private BigDecimal flowsMoney;
    /**
     * 现金流量金额不含税
     */
    @JSONField(name = "NoTaxFlowsMoney")
    private BigDecimal noTaxFlowsMoney;
    /**
     * 现金流量金额税额
     */
    @JSONField(name = "TaxFlowsMoney")
    private BigDecimal taxFlowsMoney;
}
