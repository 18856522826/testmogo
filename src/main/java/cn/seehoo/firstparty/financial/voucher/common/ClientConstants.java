package cn.seehoo.firstparty.financial.voucher.common;

/**
 * Notice: 财务凭证字典
 *
 * @author xuxu
 * @version 1.0
 * @date 2020/10/12
 * @since 1.0
 */
public interface ClientConstants {
    /**
     * 币种
     */
    String CCY_CNY = "CNY";
    /**
     * 记录类型 01混合，02主记录，03子记录
     */
    String MSG_TYPE = "01";
    /**
     * 业务板块 01小微租赁
     */
    String  BUSINESS_RESTRICTION = "01";
    /**
     * 是否动产
     */
    String IS_MOVABLE_PROPERTY = "是";
    /**
     * 系统来源
     */
    String SYSTEM_SOURCE = "01";
    /**
     * 系统来源_03
     */
    String SYSTEM_SOURCE_3="03";
    /**
     * 来源类型
     */
    String SOURCE_TYPE = "01";
    /**
     * 是否冲销-正常
     */
    String IS_CHARGE_AGAINST_NORMAL = "0";

    /**
     * 是否冲销-红冲
     */
    String IS_CHARGE_AGAINST_RED = "1";

    /**
     * 租赁类型：直租
     */
    String LEASE_TYPE_DIRECT_RENT = "01";
    /**
     * 租赁类型：回租
     */
    String LEASE_TYPE_LEASE_BACK = "02";
    /**
     * 租赁类型：所有
     */
    String LEASE_TYPE_ALL = "03";

    /**
     * 业务类型-收取保证金
     */
    String BUSINESS_TYPE_005 = "005";
    /**
     * 业务类型-收取保证金、取得资产、支付价款
     */
    String BUSINESS_TYPE_001 = "001";
    /**
     * 业务类型-首付款
     */
    String BUSINESS_TYPE_051 = "051";
    /**
     * 业务类型-起租
     */
    String BUSINESS_TYPE_002 = "002";
    /**
     * 业务类型-计提利息收入
     */
    String BUSINESS_TYPE_012 = "012";
    /**
     * 业务类型-计算销项税
     */
    String BUSINESS_TYPE_013 = "013";
    /**
     * 业务类型-首付款计入融资租赁资产
     */
    String BUSINESS_TYPE_052 = "052";
    /**
     *业务类型-逐期结转
     */
    String BUSINESS_TYPE_003="003";
    /**
     *业务类型-逐期结转
     */
    String BUSINESS_TYPE_006="006";
    /**
     *业务类型-暂挂账
     */
    String BUSINESS_TYPE_007="007";
    /**
     *业务类型-暂挂账
     */
    String BUSINESS_TYPE_009="009";
    /**
     *业务类型-内部资金调拨
     */
    String BUSINESS_TYPE_039="039";
    /**
     *业务类型-内部资金调拨
     */
    String BUSINESS_TYPE_014="014";
    /**
     * 制证交易名称 收取保证金场景
     */
    String TRANS_NAME_MARGIN = "收取保证金";
    /**
     * 制证交易名称-取得资产场景
     */
    String TRANS_NAME_ASSET = "购入融资资产";
    /**
     * 制证交易名称-支付价款场景
     */
    String TRANS_NAME_MAKE_PAYMENT = "支付价款";
    /**
     * 制证交易名称-首付款 场景
     */
    String TRANS_NAME_DOWN_PAYMENT = "首付款";
    /**
     * 制证交易名称-起租 场景
     */
    String TRANS_NAME_START_RENT = "起租";
    /**
     * 制证交易名称-计提利息收入 场景
     */
    String TRANS_NAME_INTEREST_INCOME= "计提利息收入";
    /**
     * 制证交易名称-计提销项税额
     */
    String TRANS_NAME_OUTPUT_TAX ="计提销项税额";
    /**
     * 制证交易名称-首付款计入融资租赁资产
     */
    String TRANS_NAME_DOWN_PAYMENT_IN_ASSETS ="首付款计入融资租赁资产";
    /**
     * 制证交易名称-承租人支付首付款
     */
    String TRANS_NAME_PAY_DOWN_PAYMENT ="承租人支付首付款";
    /**
     * 制证交易名称-逐期结转
     */
    String TRANS_NAME_CARRY_FORWARD ="逐期结转";
    /**
     * 制证交易名称-实收当期租金
     */
    String TRANS_NAME_CURRENT_RENT_BANK ="收取租金";
    /**
     * 制证交易名称-不明来款
     */
    String TRANS_NAME_UNKNOWN_PAYMENT="不明来款";
    /**
     * 制证交易名称-动用业务保证金-代偿
     */
    String TRANS_NAME_USE_BUSINESS_MARGIN ="业务保证金代偿";
    /**
     * 制证交易名称-内部资金调拨
     */
    String TRANS_NAME_FUND_TRANSFER="内部资金调拨";
    /**
     * 制证交易名称-计提/实收滞纳金、违约金
     */
    String TRANS_NAME_FUND_LATE_FEES="计提/实收滞纳金、违约金";
    /**
     * 子交易名称-收取保证金场景
     */
    String SUB_TRANS_NAME_MARGIN = "平台合作方缴纳业务保证金";
    /**
     * 子交易名称-取得资产场景
     */
    String SUB_TRANS_NAME_ASSET_DIRECT_RENT = "直租取得资产";
    /**
     * 子交易名称-取得资产场景
     */
    String SUB_TRANS_NAME_ASSET_LEASE_BACK = "回租取得资产";
    /**
     * 子交易名称-支付价款场景
     */
    String SUB_TRANS_NAME_PAYMENT_DIRECT_RENT = "直租支付价款";
    /**
     * 子交易名称-支付价款场景
     */
    String SUB_TRANS_NAME_PAYMENT_LEASE_BACK = "回租支付价款";
    /**
     * 子交易名称-首付款场景
     */
    String SUB_TRANS_NAME_DOWN_PAYMENT = "购入租赁资产 ";
    /**
     * 子交易名称-起租 场景
     */
    String SUB_TRANS_NAME_RENT_DIRECT_RENT = "直租起租";
    /**
     * 子交易名称-起租 场景
     */
    String SUB_TRANS_NAME_RENT_LEASE_BACK = "回租起租";
    /**
     * 子交易名称-计提利息收入 场景
     */
    String SUB_TRANS_NAME_INTEREST_DIRECT_RENT = "直租业务计提收入";
    /**
     * 子交易名称-计提利息收入 场景
     */
    String SUB_TRANS_NAME_INTEREST_LEASE_BACK = "回租业务计提收";
    /**
     * 子交易名称-计算销项税场景
     */
    String SUB_TRANS_NAME_OUTPUT_TAX_DIRECT_RENT="直租逐期开票计提销项税额";
    /**
     * 子交易名称-计算销项税场景
     */
    String SUB_TRANS_NAME_OUTPUT_TAX_BACK_RENT="回租逐期开票计提销项税额";
    /**
     * 子交易名称-首付款计入融资租赁资产
     */
    String SUB_TRANS_NAME_DOWN_PAYMENT_IN_ASSETS="首付款计入融资租赁资产";
    /**
     * 子交易名称-承租人支付首付款
     */
    String SUB_TRANS_NAME_PAY_DOWN_PAYMENT="承租人支付首付款";
    /**
     * 子交易名称-逐期结转
     */
    String SUB_TRANS_NAME_CARRY_FORWARD="逐期结转";
    /**
     * 子交易名称-实收当期租金
     */
    String SUB_TRANS_NAME_CURRENT_RENT_BANK="实收当期租金";
    /**
     * 子交易名称-预收下期租金
     */
    String SUB_TRANS_NAME_NEXT_RENT_BANK="预收下期租金";
    /**
     * 子交易名称-预收下期租金退回
     */
    String SUB_TRANS_NAME_NEXT_RENT_RETURN="预收下期租金退回";
    /**
     * 子交易名称-暂挂账
     */
    String SUB_TRANS_NAME_PENDING_ACCOUNT="暂挂账";
    /**
     * 子交易名称-租金被认领
     */
    String SUB_TRANS_NAME_RENT_CLAIM="租金被认领";
    /**
     * 子交易名称-保证金被认领
     */
    String SUB_TRANS_NAME_MARGIN_CLAIM="保证金被认领";
    /**
     * 子交易名称-罚息被认领
     */
    String SUB_TRANS_NAME_PENALTY_INTEREST_CLAIM="罚息被认领";
    /**
     * 子交易名称-被认领-选择退回
     */
    String SUB_TRANS_NAME_CLAIM_RETURN="被认领-选择退回";
    /**
     * 子交易名称-未被认领-确实无法偿付的其他应付款项
     */
    String SUB_TRANS_NAME_UN_CLAIM_NOT_REPAID="未被认领-确实无法偿付的其他应付款项";
    /**
     * 子交易名称-未被认领-确实无法偿付的其他应付款项
     */
    String SUB_TRANS_NAME_USE_BUSINESS_MARGIN="业务保证金代偿";
    /**
     * 子交易名称-资金调拨_提现
     */
    String SUB_TRANS_NAME_FUND_TRANSFER_WITHDRAW="提现";
    /**
     * 子交易名称-资金调拨
     */
    String SUB_TRANS_NAME_FUND_TRANSFER="资金调拨";
    /**
     * 子交易名称-实收滞纳金、违约金（网商银行）
     */
    String SUB_TRANS_NAME_FUND_LATE_FEES="实收滞纳金、违约金（网商银行）";
    /**
     * 子交易名称-转待凭证
     */
    String SUB_TRANS_NAME_TRANSFER_VOUCHER="转待凭证";
    /**
     * 子交易名称-发票认证
     */
    String SUB_TRANS_NAME_INVOICE_AUTH="发票认证";
    /**
     * 交易类型-收取保证金场景
     */
    String TRANS_TYPE_MARGIN = "0050301";
    /**
     * 交易类型-取得资产 场景
     */
    String TRANS_TYPE_ASSET_DIRECT_RENT = "0010101";
    /**
     * 交易类型-取得资产 场景
     */
    String TRANS_TYPE_ASSET_LEASE_BACK = "0010201";
    /**
     * 交易类型-取得资产 场景
     */
    String TRANS_TYPE_PAYMENT_DIRECT_RENT = "0010102";
    /**
     * 交易类型-取得资产 场景
     */
    String TRANS_TYPE_PAYMENT_LEASE_BACK = "0010202";
    /**
     * 交易类型-首付款 场景
     */
    String TRANS_TYPE_DOWN_PAYMENT = "0510201";
    /**
     * 交易类型-起租 场景
     */
    String TRANS_TYPE_RENT_DIRECT_RENT = "0020101";
    /**
     * 交易类型-起租 场景
     */
    String TRANS_TYPE_RENT_LEASE_BACK = "0020201";
    /**
     * 交易类型-计提利息收入 场景
     */
    String TRANS_TYPE_INTEREST_DIRECT_RENT = "0120101";
    /**
     * 交易类型-计提利息收入 场景
     */
    String TRANS_TYPE_INTEREST_LEASE_BACK = "0120201";
    /**
     * 交易类型-计算销项税（直租）
     */
    String TRANS_TYPE_OUTPUT_TAX_DIRECT_RENT="0130101";
    /**
     * 交易类型-计算销项税（回租）
     */
    String TRANS_TYPE_OUTPUT_TAX_BACK_RENT="0130201";
    /**
     * 交易类型-首付款计入融资租赁资产
     */
    String TRANS_TYPE_DOWN_PAYMENT_IN_ASSETS="0520201";
    /**
     * 交易类型-承租人支付首付款
     */
    String TRANS_TYPE_PAY_DOWN_PAYMENT="0520202";
    /**
     * 交易类型-逐期结转
     */
    String TRANS_TYPE_CARRY_FORWARD="0030301";
    /**
     * 交易类型-实收当期租金（网商银行）
     */
    String TRANS_TYPE_CURRENT_RENT_BANK="0060301";
    /**
     * 交易类型-预收下期租金（网商银行）
     */
    String TRANS_TYPE_NEXT_RENT_BANK="0060302";
    /**
     * 交易类型-预收下期租金（网商银行）
     */
    String TRANS_TYPE_NEXT_RENT_RETURN="0060303";
    /**
     * 交易类型-暂挂账
     */
    String TRANS_TYPE_PENDING_ACCOUNT="0070301";
    /**
     * 交易类型-租金被认领
     */
    String TRANS_TYPE_RENT_CLAIM="0070302";
    /**
     * 交易类型-保证金被认领
     */
    String TRANS_TYPE_MARGIN_CLAIM="0070303";
    /**
     * 交易类型-罚息被认领
     */
    String TRANS_TYPE_PENALTY_INTEREST_CLAIM="0070306";
    /**
     * 交易类型-被认领-选择退回
     */
    String TRANS_TYPE_CLAIM_RETURN="0070304";
    /**
     * 交易类型-未被认领-确实无法偿付的其他应付款项
     */
    String TRANS_TYPE_UN_CLAIM_NOT_REPAID="0070305";
    /**
     * 交易类型-未被认领-确实无法偿付的其他应付款项
     */
    String TRANS_TYPE_USE_BUSINESS_MARGIN="0090301";
    /**
     * 交易类型-资金调拨-提现
     */
    String TRANS_TYPE_FUND_TRANSFER_WITHDRAW="0390301";
    /**
     * 交易类型-资金调拨
     */
    String TRANS_TYPE_FUND_TRANSFER="0390302";
    /**
     * 交易类型-实收滞纳金
     */
    String TRANS_TYPE_FUND_LATE_FEES="0140302";
    /**
     * 交易类型-转待凭证
     */
    String TRANS_TYPE_TRANSFER_VOUCHER="0010103";
    /**
     * 交易类型-发票认证
     */
    String TRANS_TYPE_INVOICE_AUTH="0010104";

    /**
     * 支付Id--收取保证金场景
     */
    String PAYMENT_ID_ZERO = "0";
    /**
     * 保证金类型
     */
    String DEPOSIT_TYPE = "01";
    /**
     * 合同名称
     */
    String CONTRACT_NAME = "融资租赁合同";
    /**
     * 辅助核算，产品
     */
    String PRODUCT_NM_5 = "05";
    /**
     * 取得资产 产品 3.0-05，4.0-06
     */
    String PRODUCT_NM_6="06";
    /**
     * 产品模式
     */
    String PRODUCT_TYPE="1";
    /**
     * 资产买断模式
     */
    String ASSET_TYPE="2";
}
