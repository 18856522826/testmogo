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
     * 业务类型-计提利息收入、计算销项税
     */
    String BUSINESS_TYPE_012 = "012";
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
    String TRANS_TYPE_OUTPUT_TAX_DIRECT_RENT="0150101";
    /**
     * 交易类型-计算销项税（回租）
     */
    String TRANS_TYPE_OUTPUT_TAX_BACK_RENT="0150201";
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
}
