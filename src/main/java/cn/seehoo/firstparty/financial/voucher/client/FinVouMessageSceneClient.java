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
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_005);
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
        transDoc.setCustNm(message.getMerchantName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getAdjustAmount()));
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setChargeAgainstFlag(Integer.valueOf(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setDepositType(ClientConstants.DEPOSIT_TYPE);
        transDoc.setProductNm(ClientConstants.PRODUCT_NM);
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
        transDoc.setPayeeBankName(message.getPayeeBankName());
        transDoc.setPayeeAcctNo(message.getPayeeAcctNo());
        //国银付款账号信息
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getLoanAmount()));
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setChargeAgainstFlag(Integer.valueOf(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
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
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
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
     * 起租 场景所需财务信息  直租回租逻辑不同，目前逻辑属于回租
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
        transDoc.setProductNm(ClientConstants.PRODUCT_NM);
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
        transDoc.setContractPrice(message.getLoanAmount().add(message.getDownPayment()));
        transDoc.setIncludeTaxRent(message.getRent());
        transDoc.setNoTaxRent(message.getRentExcludeTax());
        transDoc.setTaxRent(message.getRentTax());
        transDoc.setTotalCapital(message.getLoanAmount());
        transDoc.setNoTaxTotalCapital(message.getPrincipalExcludeTax());
        transDoc.setTaxTotalCapital(message.getPrincipalTax());
        transDoc.setNoTaxBuyoutPrice(message.getRetentionExcludeTax());
        transDoc.setTaxBuyoutPrice(message.getRetentionTax());
        transDoc.setNoTaxResidueCapital(message.getPrincipalExcludeTax());
        transDoc.setNoTaxContractPrice(message.getLoanAmount().add(message.getDownPayment()));
        transDoc.setGoodsTax(message.getSurplusInterest().add(message.getRetentionPrice()));
        transDoc.setNoTaxInterest(message.getInterestExcludeTax().add(message.getRetentionExcludeTax()));
        transDoc.setTaxInterest(message.getInterestTax().add(message.getRetentionTax()));
        transDoc.setIncludeCapital(message.getPrincipalExcludeTax().add(message.getPrincipalTax()));
        transDoc.setNoTaxPresentCapital(message.getPrincipalExcludeTax());
        transDoc.setTaxCapital(message.getPrincipalTax());
        transDoc.setNoTaxOddCorpus(message.getPrincipalExcludeTax());
        transDoc.setTaxOddCorpus(message.getPrincipalTax());
        transDoc.setGrossInterest(message.getInterestTax());

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
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_013);
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
     * 首付款计入融资租赁资产 目前逻辑只符合回租
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
    public void downPaymentInAssetsCollection(DownPaymentInAssetsCollectionMessage message) throws Exception {
        log.info("场景八 首付款计入融资租赁资产,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_052);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_DOWN_PAYMENT_IN_ASSETS);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_DOWN_PAYMENT_IN_ASSETS);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_DOWN_PAYMENT_IN_ASSETS);
        transDoc.setAmount(message.getDownPayment());
        transDoc.setProductNm(message.getProductName());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getDownPayment()));
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
     * 承租人支付首付款 目前逻辑只符合回租
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
    public void payDownPaymentCollection(PayDownPaymentCollectionMessage message) throws Exception {
        log.info("场景九 承租人支付首付款,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_052);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_PAY_DOWN_PAYMENT);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_PAY_DOWN_PAYMENT);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_PAY_DOWN_PAYMENT);
        transDoc.setAmount(message.getDownPayment());
        transDoc.setProductNm(message.getProductName());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getDownPayment()));
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
     * 逐期结转场景所需财务信息
     * @param message 当前场景业务信息
     */
    public void carryForward(CarryForwardCollectionMessage message) throws Exception {
        log.info("场景十 逐期结转场景所需财务信息,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_003);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_CARRY_FORWARD);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_CARRY_FORWARD);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_CARRY_FORWARD);
        transDoc.setAmount(message.getRentAmount());
        transDoc.setInterest(message.getInterest());
        transDoc.setProductNm(message.getProductName());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getRentAmount()));
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setTerm(message.getCurrentTerm());
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
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
     * 实收当期租金场景所需财务信息
     * @param message 当前场景业务信息
     */
    public void actuallyCurrentRent(ActuallyCurrentRentCollectionMessage message) throws Exception {
        log.info("场景十一&十二 实收当期租金场景所需财务信息,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_006);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_CURRENT_RENT_BANK);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_CURRENT_RENT_BANK);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_CURRENT_RENT_BANK);
        transDoc.setAmount(message.getCurrentPaymentAmount());
        transDoc.setInterest(message.getCurrentInterest());
        transDoc.setProductNm(message.getProductName());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getCurrentPaymentAmount()));
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setTerm(message.getCurrentTerm());
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setPayeeBankName(config.getAccountConfigs().get(message.getBizUseType()).getPayeeBankName());
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayeeAcctNo(config.getAccountConfigs().get(message.getBizUseType()).getPayeeAcctNo());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setIncludeTaxRent(message.getIncludeTaxRent());
        transDoc.setNoTaxRent(message.getNoTaxRent());
        transDoc.setTaxRent(message.getTaxRent());
        transDoc.setIncludeCapital(message.getIncludeCapital());
        transDoc.setNoTaxInterest(message.getNoTaxInterest());
        transDoc.setTaxInterest(message.getTaxInterest());
        //租户赋值
        setTenantValue(trans,transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);
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
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_006);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_CURRENT_RENT_BANK);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_NEXT_RENT_BANK);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_NEXT_RENT_BANK);
        transDoc.setAmount(message.getCorrespondPaymentAmount());
        transDoc.setInterest(message.getCorrespondInterest());
        transDoc.setProductNm(message.getProductName());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getCorrespondPaymentAmount()));
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setTerm(message.getCurrentTerm());
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setPayeeBankName(config.getAccountConfigs().get(message.getBizUseType()).getPayeeBankName());
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayeeAcctNo(config.getAccountConfigs().get(message.getBizUseType()).getPayeeAcctNo());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setIncludeTaxRent(message.getIncludeTaxRent());
        transDoc.setNoTaxRent(message.getNoTaxRent());
        transDoc.setTaxRent(message.getTaxRent());
        transDoc.setIncludeCapital(message.getIncludeCapital());
        transDoc.setNoTaxInterest(message.getNoTaxInterest());
        transDoc.setTaxInterest(message.getTaxInterest());
        //租户赋值
        setTenantValue(trans,transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);
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
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_006);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_CURRENT_RENT_BANK);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_NEXT_RENT_RETURN);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_NEXT_RENT_RETURN);
        transDoc.setAmount(message.getCorrespondRefundAmount());
        transDoc.setInterest(message.getCorrespondRefundInterest());
        transDoc.setProductNm(message.getProductName());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getCorrespondRefundAmount()));
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setTerm(message.getCurrentTerm());
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setPayeeBankName(config.getAccountConfigs().get(message.getBizUseType()).getPayeeBankName());
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayeeAcctNo(config.getAccountConfigs().get(message.getBizUseType()).getPayeeAcctNo());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setIncludeTaxRent(message.getIncludeTaxRent());
        transDoc.setNoTaxRent(message.getNoTaxRent());
        transDoc.setTaxRent(message.getTaxRent());
        transDoc.setIncludeCapital(message.getIncludeCapital());
        transDoc.setNoTaxInterest(message.getNoTaxInterest());
        transDoc.setTaxInterest(message.getTaxInterest());
        //租户赋值
        setTenantValue(trans,transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);
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
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_007);
        trans.setInputId(message.getCollectionDetailsId());
        trans.setTransName(ClientConstants.TRANS_NAME_UNKNOWN_PAYMENT);
        trans.setContractId(message.getCollectionDetailsId());
        trans.setIputFlowId(message.getCollectionDetailsId());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_PENDING_ACCOUNT);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_PENDING_ACCOUNT);
        transDoc.setAmount(message.getCorrespondAccountAmount());
        transDoc.setInterest(BigDecimal.ZERO);
        transDoc.setCashFlow(String.valueOf(message.getCorrespondAccountAmount()));
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setPayeeBankName(message.getPayeeBankName());
        transDoc.setPayeeAcctNo(message.getPayeeAcctNo());
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setCustNm(message.getCustName());
        transDoc.setCurrentAccounting("乘用车待核查客户");
        //租户赋值
        setTenantValue(trans,transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);
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
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_007);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_UNKNOWN_PAYMENT);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_RENT_CLAIM);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_RENT_CLAIM);
        transDoc.setAmount(message.getCorrespondPaymentAmount());
        transDoc.setInterest(message.getCorrespondInterest());
        transDoc.setProductNm(message.getProductName());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getCorrespondPaymentAmount()));
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setTerm(message.getCurrentTerm());
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setPayeeBankName(message.getPayeeBankName());
        transDoc.setPayeeAcctNo(message.getPayeeAcctNo());
        transDoc.setIncludeTaxRent(message.getIncludeTaxRent());
        transDoc.setNoTaxRent(message.getNoTaxRent());
        transDoc.setTaxRent(message.getTaxRent());
        transDoc.setIncludeCapital(message.getIncludeCapital());
        transDoc.setNoTaxInterest(message.getNoTaxInterest());
        transDoc.setTaxInterest(message.getTaxInterest());
        //租户赋值
        setTenantValue(trans,transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);
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
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_007);
        trans.setInputId(message.getDepositId());
        trans.setTransName(ClientConstants.TRANS_NAME_CURRENT_RENT_BANK);
        trans.setContractId(message.getDepositId());
        trans.setIputFlowId(message.getDepositId());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_MARGIN_CLAIM);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_MARGIN_CLAIM);
        transDoc.setAmount(message.getMarginAmount());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getMarginAmount()));
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        //租户赋值
        setTenantValue(trans,transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);
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
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_007);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_UNKNOWN_PAYMENT);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_PENALTY_INTEREST_CLAIM);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_PENALTY_INTEREST_CLAIM);
        transDoc.setAmount(message.getCorrespondPaymentAmount());
        transDoc.setProductNm(message.getProductName());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getCorrespondPaymentAmount()));
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setPayeeBankName(message.getPayeeBankName());
        transDoc.setPayeeAcctNo(message.getPayeeAcctNo());
        //租户赋值
        setTenantValue(trans,transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);
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
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_007);
        trans.setInputId(message.getCollectionDetailsId());
        trans.setTransName(ClientConstants.TRANS_NAME_UNKNOWN_PAYMENT);
        trans.setContractId(message.getCollectionDetailsId());
        trans.setIputFlowId(message.getCollectionDetailsId());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_CLAIM_RETURN);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_CLAIM_RETURN);
        transDoc.setAmount(message.getCorrespondAmount());
        transDoc.setCashFlow(String.valueOf(message.getCorrespondAmount()));
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setPayeeBankName(message.getPayeeBankName());
        transDoc.setPayeeAcctNo(message.getPayeeAcctNo());
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setCustNm(message.getCustName());
        transDoc.setCurrentAccounting("乘用车待核查客户");
        //租户赋值
        setTenantValue(trans,transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);
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
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_007);
        trans.setInputId(message.getCollectionDetailsId());
        trans.setTransName(ClientConstants.TRANS_NAME_UNKNOWN_PAYMENT);
        trans.setContractId(message.getCollectionDetailsId());
        trans.setIputFlowId(message.getCollectionDetailsId());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_UN_CLAIM_NOT_REPAID);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_UN_CLAIM_NOT_REPAID);
        transDoc.setAmount(message.getCorrespondAmount());
        transDoc.setCashFlow(String.valueOf(message.getCorrespondAmount()));
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setPayeeBankName(message.getPayeeBankName());
        transDoc.setPayeeAcctNo(message.getPayeeAcctNo());
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setCustNm(message.getCustName());
        transDoc.setCurrentAccounting("乘用车待核查客户");
        //租户赋值
        setTenantValue(trans,transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);
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
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_009);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_USE_BUSINESS_MARGIN);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_USE_BUSINESS_MARGIN);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_USE_BUSINESS_MARGIN);
        transDoc.setAmount(message.getBusinessMarginAmount());
        transDoc.setInterest(message.getCorrespondInterest());
        transDoc.setProductNm(message.getProductName());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getBusinessMarginAmount()));
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setTerm(message.getCurrentTerm());
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
     * 内部资金调拨——提现场景所需财务信息
     * @param message 当前场景业务信息
     */
    public void fundTransferWithdrawCollection(FundTransferWithdrawCollectionMessage message) throws Exception {
        log.info("场景二十四 内部资金调拨——提现场景所需财务信息,入参:{}",message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_039);
        trans.setInputId(message.getCollectionDetailsId());
        trans.setTransName(ClientConstants.TRANS_NAME_FUND_TRANSFER);
        trans.setContractId(message.getCollectionDetailsId());
        trans.setIputFlowId(message.getCollectionDetailsId());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_FUND_TRANSFER_WITHDRAW);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_FUND_TRANSFER_WITHDRAW);
        transDoc.setAmount(message.getCorrespondAmount());
        transDoc.setInterest(message.getCorrespondInterest());
        transDoc.setPaymentId(ClientConstants.PAYMENT_ID_ZERO);
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getCorrespondAmount()));
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setPayeeBankName(message.getPayeeBankName());
        transDoc.setPayeeAcctNo(message.getPayeeAcctNo());
        transDoc.setIncludeTaxRent(message.getIncludeTaxRent());
        transDoc.setIncludeCapital(message.getIncludeCapital());
        transDoc.setPlatformPartner(message.getPaymentAgency());
        transDoc.setCurrentAccounting(message.getPaymentAgency());
        //租户赋值
        setTenantValue(trans,transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);
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
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_039);
        trans.setInputId(message.getCollectionDetailsId());
        trans.setTransName(ClientConstants.TRANS_NAME_FUND_TRANSFER);
        trans.setContractId(message.getCollectionDetailsId());
        trans.setIputFlowId(message.getCollectionDetailsId());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_FUND_TRANSFER);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_FUND_TRANSFER);
        transDoc.setAmount(message.getCorrespondAmount());
        transDoc.setInterest(message.getCorrespondInterest());
        transDoc.setPaymentId(ClientConstants.PAYMENT_ID_ZERO);
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getCorrespondAmount()));
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setPayeeBankName(message.getPayeeBankName());
        transDoc.setPayeeAcctNo(message.getPayeeAcctNo());
        transDoc.setIncludeTaxRent(message.getIncludeTaxRent());
        transDoc.setIncludeCapital(message.getIncludeCapital());
        transDoc.setPlatformPartner(message.getPaymentAgency());
        transDoc.setCurrentAccounting(message.getPaymentAgency());
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
