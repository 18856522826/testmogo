package cn.seehoo.firstparty.financial.voucher.client;

import cn.seehoo.firstparty.financial.voucher.common.ClientConstants;
import cn.seehoo.firstparty.financial.voucher.config.FinVouMessageConfig;
import cn.seehoo.firstparty.financial.voucher.model.*;
import cn.seehoo.firstparty.financial.voucher.model.basic.AcctDocGenTrans;
import cn.seehoo.firstparty.financial.voucher.model.basic.AcctDocGenTransDoc;
import cn.seehoo.firstparty.financial.voucher.service.FinVouAService;
import cn.seehoo.firstparty.financial.voucher.service.FinVouBService;
import cn.seehoo.firstparty.financial.voucher.service.FinVouService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Notice: 财务凭证信息场景客户端：提供各场景消息转换、组装及发送功能
 *
 * @author Staunch
 * @version 1.0
 * @date 2021/3/15
 * @since 1.0
 */
@Slf4j
@AllArgsConstructor
public class FinVouMessageSceneClient {
    /**
     * 财务凭证消息发送者
     */
    private final FinVouMessageSender sender;
    /**
     * 财务凭证配置信息
     */
    private final FinVouMessageConfig config;

    /**
     * 保证金收取场景
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
    public void marginCollection(MarginCollectionMessage message) throws Exception {
        log.info("场景一 保证金收取,入参:{}",message.toString());
        VoucherStandardMessage standardMessage=lookUp(config).marginCollection(message);
        //发送
        sender.send(standardMessage);
    }

    /**
     * 取得资产场景
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
    public void assetCollection(AssetCollectionMessage message) throws Exception {
        log.info("场景二 取得资产,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).assetCollection(message);
        //发送
        sender.send(standardMessage);
    }

    /**
     * 支付价款
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
    public void makePaymentCollection(MakePaymentCollectionMessage message) throws Exception {
        log.info("场景三 支付价款,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).makePaymentCollection(message);
        //发送
        sender.send(standardMessage);
    }

    /**
     * 购入融资租赁资产-首付款场景所需财务信息
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
    public void downPaymentCollection(DownPaymentCollectionMessage message) throws Exception {
        log.info("场景四 购入融资租赁资产,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).downPaymentCollection(message);

        //发送
        sender.send(standardMessage);
    }

    /**
     * 起租 场景所需财务信息  直租回租逻辑不同，目前逻辑属于回租
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
    public void startRentCollection(StartRentCollectionMessage message) throws Exception {
        log.info("场景五 起租,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).startRentCollection(message);
        //发送
        sender.send(standardMessage);
    }

    /**
     * 计提利息收入 场景所需财务信息
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
    public void interestIncomeCollection(InterestIncomeCollectionMessage message) throws Exception {
        log.info("场景六 计提利息收入,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).interestIncomeCollection(message);
        //发送
        sender.send(standardMessage);
    }
    /**
     * 计算销项税-   场景所需财务信息
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
    public void computeOutputTaxCollection(ComputeOutputTaxCollectionMessage message) throws Exception {
        log.info("场景七 计算销项税,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).computeOutputTaxCollection(message);
        //发送
        sender.send(standardMessage);
    }

    /**
     * 首付款计入融资租赁资产 目前逻辑只符合回租
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
    public void downPaymentInAssetsCollection(DownPaymentInAssetsCollectionMessage message) throws Exception {
        log.info("场景八 首付款计入融资租赁资产,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).downPaymentInAssetsCollection(message);
        //发送
        sender.send(standardMessage);
    }
    /**
     * 承租人支付首付款 目前逻辑只符合回租
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
    public void payDownPaymentCollection(PayDownPaymentCollectionMessage message) throws Exception {
        log.info("场景九 承租人支付首付款,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).payDownPaymentCollection(message);
        //发送
        sender.send(standardMessage);
    }

    /**
     * 逐期结转场景所需财务信息
     * @param message 当前场景业务信息
     */
    public void carryForward(CarryForwardCollectionMessage message) throws Exception {
        log.info("场景十 逐期结转场景所需财务信息,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).carryForward(message);
        //发送
        sender.send(standardMessage);
    }
    /**
     * 实收当期租金场景所需财务信息
     * @param message 当前场景业务信息
     */
    public void actuallyCurrentRent(ActuallyCurrentRentCollectionMessage message) throws Exception {
        log.info("场景十一&十二 实收当期租金场景所需财务信息,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).actuallyCurrentRent(message);

        //发送
        sender.send(standardMessage);
    }
    /**
     * 预收下期租金场景所需财务信息
     * @param message 当前场景业务信息
     */
    public void preCollectNextRent (NextRentCollectionMessage message) throws Exception {
        log.info("场景十三&十四 预收下期租金场景所需财务信息,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).preCollectNextRent(message);
        //发送
        sender.send(standardMessage);
    }
    /**
     * 预收下期租金退回场景所需财务信息
     * @param message 当前场景业务信息
     */
    public void returnNextRent (ReturnNextRentCollectionMessage message) throws Exception {
        log.info("场景十五&十六 预收下期租金退回场景所需财务信息,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).returnNextRent(message);
        //发送
        sender.send(standardMessage);
    }
    /**
     * 暂挂账场景所需财务信息
     * @param message 当前场景业务信息
     */
    public void pendingAccount (PendingAccountCollectionMessage message) throws Exception {
        log.info("场景十七 暂挂账场景所需财务信息,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).pendingAccount(message);
        //发送
        sender.send(standardMessage);
    }
    /**
     * 租金被认领场景所需财务信息
     * @param message 当前场景业务信息
     */
    public void rentClaim (RentClaimCollectionMessage message) throws Exception {
        log.info("场景十八 租金被认领场景所需财务信息,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).rentClaim(message);
        //发送
        sender.send(standardMessage);
    }
    /**
     * 保证金被认领场景所需财务信息
     * @param message 当前场景业务信息
     */
    public void marginClaim (MarginClaimCollectionMessage message) throws Exception {
        log.info("场景十九 保证金被认领场景所需财务信息,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).marginClaim(message);
        //发送
        sender.send(standardMessage);
    }
    /**
     * 罚息被认领场景所需财务信息
     * @param message 当前场景业务信息
     */
    public void penaltyInterestClaim (PenaltyInterestClaimCollectionMessage message) throws Exception {
        log.info("场景二十 罚息被认领场景所需财务信息,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).penaltyInterestClaim(message);
        //发送
        sender.send(standardMessage);
    }
    /**
     * 被认领-选择退回场景所需财务信息
     * @param message 当前场景业务信息
     */
    public void claimReturn(ClaimReturnCollectionMessage message) throws Exception {
        log.info("场景二十一 被认领-选择退回场景所需财务信息,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).claimReturn(message);
        //发送
        sender.send(standardMessage);
    }
    /**
     * 未被认领-确实无法偿付的其他应付款项场景所需财务信息
     * @param message 当前场景业务信息
     */
    public void unClaimNotRepaid(UnClaimNotRepaidCollectionMessage message) throws Exception {
        log.info("场景二十二 未被认领-确实无法偿付的其他应付款项场景所需财务信息,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).unClaimNotRepaid(message);
        //发送
        sender.send(standardMessage);
    }
    /**
     * 动用业务保证金-代偿
     * @param message 当前场景业务信息
     */
    public void useBusinessMargin(UseBusinessMarginCollectionMessage message) throws Exception {
        log.info("场景二十三 动用业务保证金-代偿场景所需财务信息,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).useBusinessMargin(message);
        //发送
        sender.send(standardMessage);
    }
    /**
     * 内部资金调拨——提现场景所需财务信息
     * @param message 当前场景业务信息
     */
    public void fundTransferWithdrawCollection(FundTransferWithdrawCollectionMessage message) throws Exception {
        log.info("场景二十四 内部资金调拨——提现场景所需财务信息,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).fundTransferWithdrawCollection(message);
        //发送
        sender.send(standardMessage);
    }
    /**
     * 内部资金调拨场景所需财务信息
     * @param message 当前场景业务信息
     */
    public void fundTransferCollection(FundTransferCollectionMessage message) throws Exception {
        log.info("场景二十五 内部资金调拨场景所需财务信息,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).fundTransferCollection(message);
        //发送
        sender.send(standardMessage);
    }
    /**
     * 实收滞纳金、违约金场景所需财务信息
     * @param message 当前场景业务信息
     */
    public void actualReceiptsPenalty (ActualReceiptsPenaltyCollectionMessage message) throws Exception {
        log.info("场景二十六 实收滞纳金、违约金场景所需财务信息,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).actualReceiptsPenalty(message);
        //发送
        sender.send(standardMessage);
    }
    /**
     * 转待凭证场景所需财务信息
     * @param message 当前场景业务信息
     */
    public void transferVoucher (TransferAndInvoiceCollectionMessage message) throws Exception {
        log.info("场景二十六 转待凭证场景所需财务信息,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).transferVoucher(message);
        //发送
        sender.send(standardMessage);
    }
    /**
     * 发票认证场景所需财务信息
     * @param message 当前场景业务信息
     */
    public void invoiceAuth (TransferAndInvoiceCollectionMessage message) throws Exception {
        log.info("场景二十六 发票认证场景所需财务信息,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).invoiceAuth(message);
        //发送
        sender.send(standardMessage);
    }

    /**
     * 提前结清-补计提收入
     * @param message 当前场景业务信息
     */
    public void accruedIncome (AccruedIncomeCollectionMessage message) throws Exception {
        log.info("场景二十七 提前结清-补计提收入,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).accruedIncome(message);
        //发送
        sender.send(standardMessage);
    }

    /**
     * 提前结清-补计提销项税
     * @param message 当前场景业务信息
     */
    public void supplementaryOutputTax (SupplementaryOutputTaxCollectionMessage message) throws Exception {
        log.info("场景二十八 提前结清-补计提销项税,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).supplementaryOutputTax(message);
        //发送
        sender.send(standardMessage);
    }

    /**
     * 提前结清-收到结清款
     * @param message 当前场景业务信息
     */
    public void receiptSettlement (ReceiptSettlementCollectionMessage message) throws Exception {
        log.info("场景二十九 提前结清-收到结清款项,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).receiptSettlement(message);
        //发送
        sender.send(standardMessage);
    }

    /**
     * 提前结清-滞纳金收入
     * @param message 入参
     */
    public void earlyConfirmLateFee(EarlyConfirmLateFeeCollectionMessage message) throws Exception {
        log.info("场景三十 提前结清-滞纳金收入,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).earlyConfirmLateFee(message);
        //发送
        sender.send(standardMessage);
    }
    /**
     * 提前结清-结平
     * @param message 入参
     */
    public void earlyRepayment(EarlyRepaymentCollectionMessage message) throws Exception {
        log.info("场景三十一 提前结清-结平,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).earlyRepayment(message);
        //发送
        sender.send(standardMessage);
    }

    /**
     * 提前结清-实收租金
     * @param message 入参
     */
    public void earlyActuallyCurrentRent(ActuallyCurrentRentCollectionMessage message) throws Exception {
        log.info("场景三十二 提前结清-实收租金,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).earlyActuallyCurrentRent(message);
        //发送
        sender.send(standardMessage);
    }
    /**
     * 行驶购买权--收取名义购买价
     * @param message 入参
     */
    public void useRetentionPrice(UseRetentionPriceCollectionMessage message) throws Exception {
        log.info("场景三十三 行驶购买权-收取名义购买价,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).useRetentionPrice(message);

        //发送
        sender.send(standardMessage);
    }

    /**
     * 行驶购买权--正常结清结转
     * @param message 入参
     */
    public void normalSettlementCarryForward(SettleCarryForwardCollectionMessage message) throws Exception {
        log.info("场景三十四 行驶购买权-正常结清结转,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).normalSettlementCarryForward(message);
        //发送
        sender.send(standardMessage);
    }
    /**
     * 调整买断价场景
     * @param message 入参
     */
    public void adjustBuyout(AdjustBuyoutMessage message) throws Exception {
        log.info("场景三十六 调整买断价场景,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).adjustBuyout(message);
        //发送
        sender.send(standardMessage);
    }
    /**
     * 场景三十四 确认期初利息余额
     * @param message 入参
     */
    public void beginInterest(BeginInterestMessage message) throws Exception {
        log.info("场景三十四 确认期初利息余额,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).beginInterest(message);
        //发送
        sender.send(standardMessage);
    }
    /**
     * 场景三十八 确认直租项目本金增值税场景
     * @param message 入参
     */
    public void vatPrincipal(VatPrincipalMessage message) throws Exception {
        log.info("场景三十八 确认直租项目本金增值税场景,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).vatPrincipal(message);
        //发送
        sender.send(standardMessage);
    }
    /**
     * 场景三十九 结转未实现融资收益场景
     * @param message 入参
     */
    public void carryForwardNoGains(CarryForwardNoGainsMessage message) throws Exception {
        log.info("场景三十九 结转未实现融资收益场景场景所需财务信息,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage=lookUp(config).carryForwardNoGains(message);
        //发送
        sender.send(standardMessage);
    }

    /**
     * 查询AB口径service
     * @param config 配置信息
     * @return 响应
     */
    public FinVouService lookUp(FinVouMessageConfig config){
        final String A="A";
        if (A.equals(config.getFlag())){
            return new FinVouAService(config);
        }else {
            return new FinVouBService(config);
        }
    }
}
