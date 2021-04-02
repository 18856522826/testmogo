package cn.seehoo.firstparty.financial.voucher.client;

import cn.seehoo.firstparty.financial.voucher.common.ClientConstants;
import cn.seehoo.firstparty.financial.voucher.config.FinVouMessageConfig;
import cn.seehoo.firstparty.financial.voucher.model.*;
import cn.seehoo.firstparty.financial.voucher.model.basic.AcctDocGenTrans;
import cn.seehoo.firstparty.financial.voucher.model.basic.AcctDocGenTransDoc;
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
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(message.getAdjustType());
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_001);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_MARGIN);
        trans.setContractId(message.getBusinessNo());
        trans.setIputFlowId(message.getBusinessNo());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_MARGIN);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_MARGIN);
        transDoc.setAmount(message.getAdjustAmount());
        transDoc.setPaymentId(ClientConstants.PAYMENT_ID_ZERO);
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getAdjustAmount()));
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setChargeAgainstFlag(Integer.valueOf(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setDepositType(ClientConstants.DEPOSIT_TYPE);
        //国银收款账号信息
        transDoc.setPayeeAcctNo(config.getBankAccountNo());
        transDoc.setPayeeBankName(config.getBankAccountName());
        //租户赋值
        setTenantValue(trans,transDoc);
        docList.add(transDoc);

        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);
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
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_001);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_ASSET);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setContractName(ClientConstants.CONTRACT_NAME);
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        if (ClientConstants.LEASE_TYPE_DIRECT_RENT.equals(message.getLeaseType())){
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_ASSET_DIRECT_RENT);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_ASSET_DIRECT_RENT);
        }else{
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_ASSET_LEASE_BACK);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_ASSET_LEASE_BACK);
        }
        transDoc.setAmount(message.getLoanAmount());
        transDoc.setRemanentCapital(message.getSurplusPrincipal());
        transDoc.setInterest(message.getSurplusInterest());
        transDoc.setSumInterest(message.getSumInterest());
        transDoc.setPaymentId(ClientConstants.PAYMENT_ID_ZERO);
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setProductNm(message.getProductName());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getLoanAmount()));
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setTerm(message.getLoanTerm());
        transDoc.setChargeAgainstFlag(Integer.valueOf(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setContractPrice(message.getLoanAmount());
        transDoc.setNoTaxContractPrice(message.getPrincipalExcludeTax());
        transDoc.setGoodsTax(message.getPrincipalTax());
        //国银收款账号信息
        transDoc.setPayeeAcctNo(config.getBankAccountNo());
        transDoc.setPayeeBankName(config.getBankAccountName());
        //租户赋值
        setTenantValue(trans,transDoc);
        docList.add(transDoc);

        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);
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
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_001);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_MAKE_PAYMENT);
        trans.setContractId(message.getBusinessNo());
        trans.setIputFlowId(message.getBusinessNo());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        if (ClientConstants.LEASE_TYPE_DIRECT_RENT.equals(message.getLeaseType())){
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_PAYMENT_DIRECT_RENT);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_PAYMENT_DIRECT_RENT);
        }else{
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_PAYMENT_LEASE_BACK);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_PAYMENT_LEASE_BACK);
        }
        transDoc.setAmount(message.getLoanAmount());
        transDoc.setRemanentCapital(message.getSurplusPrincipal());
        transDoc.setInterest(message.getSurplusInterest());
        transDoc.setSumInterest(message.getSumInterest());
        transDoc.setPaymentId(message.getBusinessNo());
        transDoc.setPayeeBankName(message.getPayerBankName());
        transDoc.setPayeeAcctNo(message.getPayerAcctNo());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getLoanAmount()));
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setChargeAgainstFlag(Integer.valueOf(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        //国银付款账号信息
        transDoc.setPayerAcctNo(config.getBankAccountNo());
        transDoc.setPayerBankName(config.getBankAccountName());
        //租户赋值
        setTenantValue(trans,transDoc);
        docList.add(transDoc);

        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);
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
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_051);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_DOWN_PAYMENT);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setContractName(ClientConstants.CONTRACT_NAME);
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_DOWN_PAYMENT);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_DOWN_PAYMENT);
        transDoc.setAmount(message.getDownPayment());
        transDoc.setRemanentCapital(message.getSurplusPrincipal());
        transDoc.setInterest(BigDecimal.ZERO);
        transDoc.setSumInterest(message.getSumInterest());
        transDoc.setPaymentId(ClientConstants.PAYMENT_ID_ZERO);
        transDoc.setProductNm(message.getProductName());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getDownPayment()));
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setTerm(message.getLoanTerm());
        transDoc.setChargeAgainstFlag(Integer.valueOf(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setBuyoutPrice(message.getRetentionPrice());
        transDoc.setResidueUncollectedCapital(message.getSurplusPrincipal());
        transDoc.setResidueUncollectedInterest(message.getSurplusInterest());
        transDoc.setProvisionInterest(message.getSurplusInterest());
        transDoc.setProvisionTaxes(message.getInterestTax());
        transDoc.setDepositType(ClientConstants.DEPOSIT_TYPE);
        transDoc.setNoTaxAmount(message.getDownPayment());
        transDoc.setIncludeTaxRent(message.getDownPayment());
        transDoc.setNoTaxRent(message.getDownPayment());
        transDoc.setTotalCapital(message.getDownPayment());
        transDoc.setNoTaxTotalCapital(message.getDownPayment());
        transDoc.setNoTaxCapital(message.getDownPayment());
        transDoc.setNoTaxOddCorpus(message.getPrincipalExcludeTax());
        transDoc.setTaxOddCorpus(message.getPrincipalTax());
        transDoc.setGrossInterest(message.getSumInterest());
        //租户赋值
        setTenantValue(trans,transDoc);
        docList.add(transDoc);

        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);
        //发送
        sender.send(standardMessage);
    }

    /**
     * 起租 场景所需财务信息
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
    public void startRentCollection(StartRentCollectionMessage message) throws Exception {
        log.info("场景五 起租,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_002);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_START_RENT);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setContractName(ClientConstants.CONTRACT_NAME);
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        if (ClientConstants.LEASE_TYPE_DIRECT_RENT.equals(message.getLeaseType())){
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_RENT_DIRECT_RENT);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_RENT_DIRECT_RENT);
        }else{
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_RENT_LEASE_BACK);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_RENT_LEASE_BACK);
        }
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setAmount(message.getRent());
        transDoc.setRemanentCapital(message.getSurplusPrincipal());
        transDoc.setInterest(message.getSurplusInterest());
        transDoc.setSumInterest(message.getSurplusInterest());
        transDoc.setFee(message.getRetentionPrice());
        transDoc.setPaymentId(ClientConstants.PAYMENT_ID_ZERO);
        transDoc.setProductNm(message.getProductName());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getRent()));
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setTerm(message.getLoanTerm());
        transDoc.setChargeAgainstFlag(Integer.valueOf(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setBuyoutPrice(message.getRetentionPrice());
        transDoc.setResidueUncollectedCapital(message.getSurplusPrincipal());
        transDoc.setResidueUncollectedInterest(message.getSurplusInterest());
        transDoc.setProvisionInterest(message.getSurplusInterest());
        transDoc.setProvisionTaxes(message.getRentTax());
        transDoc.setEarnings(message.getInterestExcludeTax());
        transDoc.setAmountOfTax(message.getInterestTax());
        transDoc.setNoTaxAmount(message.getRentExcludeTax());
        transDoc.setContractPrice(message.getVehicleSalesPrice());
        transDoc.setIncludeTaxRent(message.getRent());
        transDoc.setNoTaxRent(message.getRentExcludeTax());
        transDoc.setTaxRent(message.getRentTax());
        transDoc.setTotalCapital(message.getLoanAmount());
        transDoc.setNoTaxTotalCapital(message.getPrincipalExcludeTax());
        transDoc.setTaxTotalCapital(message.getPrincipalTax());
        transDoc.setNoTaxBuyoutPrice(message.getRetentionExcludeTax());
        transDoc.setTaxBuyoutPrice(message.getRetentionTax());
        transDoc.setNoTaxResidueCapital(message.getPrincipalExcludeTax());
        transDoc.setNoTaxContractPrice(message.getLoanAmount());
        //租户赋值
        setTenantValue(trans,transDoc);
        docList.add(transDoc);

        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);
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
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_012);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_INTEREST_INCOME);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setContractName(ClientConstants.CONTRACT_NAME);
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        if (ClientConstants.LEASE_TYPE_DIRECT_RENT.equals(message.getLeaseType())){
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_INTEREST_DIRECT_RENT);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_INTEREST_DIRECT_RENT);
        }else{
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_INTEREST_LEASE_BACK);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_INTEREST_LEASE_BACK);
        }
        transDoc.setAmount(message.getInterest());
        transDoc.setPaymentId(ClientConstants.PAYMENT_ID_ZERO);
        transDoc.setProductNm(message.getProductName());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getRent()));
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setTerm(message.getCurrentTerm());
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setBuyoutPrice(message.getRetentionPrice());
        transDoc.setProvisionInterest(message.getSurplusInterest());
        transDoc.setProvisionTaxes(message.getInterestTax());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setEarnings(message.getRent());
        transDoc.setAmountOfTax(message.getRentTax());
        //租户赋值
        setTenantValue(trans,transDoc);
        docList.add(transDoc);

        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);
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
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_012);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_OUTPUT_TAX);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        if (ClientConstants.LEASE_TYPE_DIRECT_RENT.equals(message.getLeaseType())){
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_OUTPUT_TAX_DIRECT_RENT);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_OUTPUT_TAX_DIRECT_RENT);
        }else{
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_OUTPUT_TAX_BACK_RENT);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_OUTPUT_TAX_BACK_RENT);
        }
        transDoc.setAmount(message.getInterestTax());
        transDoc.setInterest(message.getInterest());
        transDoc.setPaymentId(ClientConstants.PAYMENT_ID_ZERO);
        transDoc.setProductNm(message.getProductName());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getInterestTax()));
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setTerm(message.getCurrentTerm());
        transDoc.setChargeAgainstFlag(Integer.valueOf(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setSumTerm(message.getLoanTerm());
        //租户赋值
        setTenantValue(trans,transDoc);

        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);
        //发送
        sender.send(standardMessage);
    }

    /**
     * 租户赋值
     * @param trans 财务子交易
     * @param transDoc 制证子交易流水
     */
    private void setTenantValue(AcctDocGenTrans trans,AcctDocGenTransDoc transDoc){
        trans.setTntInstId(config.getTntInstId());
        trans.setTenantName(config.getTenantName());

        transDoc.setTntInstId(config.getTntInstId());
        transDoc.setTenantName(config.getTenantName());
    }

}
