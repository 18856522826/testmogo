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
     * 是否动产
     */
    String IS_MOVABLE_PROPERTY = "是";
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
     * 业务类型-购入融资租赁资产-首付款
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
     * 业务板块 01小微租赁
     */
    String BUSINESS_RESTRICTION = "01";
    /**
     * 子交易名称-收取保证金场景
     */
    String SUB_TRANS_NAME_MARGIN = "平台合作方缴纳业务保证金";
    /**
     * 交易类型-收取保证金场景
     */
    String TRANS_TYPE_MARGIN = "0050301";
    /**
     * 支付Id--收取保证金场景
     */
    String PAYMENT_ID_MARGIN = "0";
    /**
     * 往来核算 --收取保证金场景
     */
    String CURRENT_ACCOUNTING_MARGIN="平台合作方";
    /**
     * 保证金类型
     */
    String DEPOSIT_TYPE = "01";
}
