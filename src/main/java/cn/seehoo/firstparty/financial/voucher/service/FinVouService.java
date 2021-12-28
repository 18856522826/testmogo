package cn.seehoo.firstparty.financial.voucher.service;

import cn.seehoo.firstparty.financial.voucher.model.*;

/**
 * Notice:
 *
 * @author xuxu
 * @version 1.0
 * @date 2021/12/23
 * @since 1.0
 */
public interface FinVouService {
    /**
     *
     * @param message
     * @throws Exception
     */
    VoucherStandardMessage marginCollection(MarginCollectionMessage message) throws Exception;

    VoucherStandardMessage assetCollection(AssetCollectionMessage message) throws Exception;
    VoucherStandardMessage makePaymentCollection(MakePaymentCollectionMessage message) throws Exception;
    /**
     * 购入融资租赁资产-首付款场景所需财务信息
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
     VoucherStandardMessage downPaymentCollection(DownPaymentCollectionMessage message) throws Exception;
    /**
     * 起租 场景所需财务信息  直租回租逻辑不同，目前逻辑属于回租
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
     VoucherStandardMessage startRentCollection(StartRentCollectionMessage message) throws Exception;
    /**
     * 计提利息收入 场景所需财务信息
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
     VoucherStandardMessage interestIncomeCollection(InterestIncomeCollectionMessage message) throws Exception;
    /**
     * 计算销项税-场景所需财务信息
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
     VoucherStandardMessage computeOutputTaxCollection(ComputeOutputTaxCollectionMessage message) throws Exception;
    /**
     * 首付款计入融资租赁资产 目前逻辑只符合回租
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
     VoucherStandardMessage downPaymentInAssetsCollection(DownPaymentInAssetsCollectionMessage message) throws Exception;
    /**
     * 承租人支付首付款 目前逻辑只符合回租
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
     VoucherStandardMessage payDownPaymentCollection(PayDownPaymentCollectionMessage message) throws Exception;
    /**
     * 逐期结转场景所需财务信息
     * @param message 当前场景业务信息
     */
     VoucherStandardMessage carryForward(CarryForwardCollectionMessage message) throws Exception;
    /**
     * 实收当期租金场景所需财务信息
     * @param message 当前场景业务信息
     */
     VoucherStandardMessage actuallyCurrentRent(ActuallyCurrentRentCollectionMessage message) throws Exception;
    /**
     * 预收下期租金场景所需财务信息
     * @param message 当前场景业务信息
     */
     VoucherStandardMessage preCollectNextRent (NextRentCollectionMessage message) throws Exception;
    /**
     * 预收下期租金退回场景所需财务信息
     * @param message 当前场景业务信息
     */
     VoucherStandardMessage returnNextRent (ReturnNextRentCollectionMessage message) throws Exception;
    /**
     * 暂挂账场景所需财务信息
     * @param message 当前场景业务信息
     */
     VoucherStandardMessage pendingAccount (PendingAccountCollectionMessage message) throws Exception;
    /**
     * 租金被认领场景所需财务信息
     * @param message 当前场景业务信息
     */
     VoucherStandardMessage rentClaim (RentClaimCollectionMessage message) throws Exception;
    /**
     * 保证金被认领场景所需财务信息
     * @param message 当前场景业务信息
     */
     VoucherStandardMessage marginClaim (MarginClaimCollectionMessage message) throws Exception;
    /**
     * 罚息被认领场景所需财务信息
     * @param message 当前场景业务信息
     */
     VoucherStandardMessage penaltyInterestClaim (PenaltyInterestClaimCollectionMessage message) throws Exception;
    /**
     * 被认领-选择退回场景所需财务信息
     * @param message 当前场景业务信息
     */
     VoucherStandardMessage claimReturn(ClaimReturnCollectionMessage message) throws Exception;
    /**
     * 未被认领-确实无法偿付的其他应付款项场景所需财务信息
     * @param message 当前场景业务信息
     */
     VoucherStandardMessage unClaimNotRepaid(UnClaimNotRepaidCollectionMessage message) throws Exception;
    /**
     * 动用业务保证金-代偿
     * @param message 当前场景业务信息
     */
     VoucherStandardMessage useBusinessMargin(UseBusinessMarginCollectionMessage message) throws Exception;
    /**
     * 内部资金调拨——提现场景所需财务信息
     * @param message 当前场景业务信息
     */
     VoucherStandardMessage fundTransferWithdrawCollection(FundTransferWithdrawCollectionMessage message) throws Exception;
    /**
     * 内部资金调拨场景所需财务信息
     * @param message 当前场景业务信息
     */
     VoucherStandardMessage fundTransferCollection(FundTransferCollectionMessage message) throws Exception;
    /**
     * 实收滞纳金、违约金场景所需财务信息
     * @param message 当前场景业务信息
     */
     VoucherStandardMessage actualReceiptsPenalty (ActualReceiptsPenaltyCollectionMessage message) throws Exception;
    /**
     * 转待凭证场景所需财务信息
     * @param message 当前场景业务信息
     */
     VoucherStandardMessage transferVoucher (TransferAndInvoiceCollectionMessage message) throws Exception;
    /**
     * 发票认证场景所需财务信息
     * @param message 当前场景业务信息
     */
     VoucherStandardMessage invoiceAuth (TransferAndInvoiceCollectionMessage message) throws Exception;

    /**
     * 提前结清-补计提收入
     * @param message 当前场景业务信息
     */
     VoucherStandardMessage accruedIncome (AccruedIncomeCollectionMessage message) throws Exception;
    /**
     * 提前结清-补计提销项税
     * @param message 当前场景业务信息
     */
     VoucherStandardMessage supplementaryOutputTax (SupplementaryOutputTaxCollectionMessage message) throws Exception;
    /**
     * 提前结清-收到结清款
     * @param message 当前场景业务信息
     */
     VoucherStandardMessage receiptSettlement (ReceiptSettlementCollectionMessage message) throws Exception;
    /**
     * 提前结清-滞纳金收入
     * @param message 入参
     */
     VoucherStandardMessage earlyConfirmLateFee(EarlyConfirmLateFeeCollectionMessage message) throws Exception;
    /**
     * 提前结清-结平
     * @param message 入参
     */
     VoucherStandardMessage earlyRepayment(EarlyRepaymentCollectionMessage message) throws Exception;
    /**
     * 提前结清-实收租金
     * @param message 入参
     */
     VoucherStandardMessage earlyActuallyCurrentRent(ActuallyCurrentRentCollectionMessage message) throws Exception;
    /**
     * 行驶购买权--收取名义购买价
     * @param message 入参
     */
     VoucherStandardMessage useRetentionPrice(UseRetentionPriceCollectionMessage message) throws Exception;
    /**
     * 行驶购买权--正常结清结转
     * @param message 入参
     */
     VoucherStandardMessage normalSettlementCarryForward(SettleCarryForwardCollectionMessage message) throws Exception;

    /**
     * 提前结清--保证金代偿
     * @param message 入参
     */
    VoucherStandardMessage marginCompensation(MarginCompensationCollectionMessage message) throws Exception;
    }
