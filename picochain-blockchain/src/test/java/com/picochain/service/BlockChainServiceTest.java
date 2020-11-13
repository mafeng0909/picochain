package com.picochain.service;

import com.picochain.pojo.ContractParams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthAccounts;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BlockChainServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlockChainServiceTest.class);

    @Autowired
    private Web3j web3j;

    @Autowired
    private BlockChainService blockChainService;

    @Test
    public void test() throws CipherException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        File file = new File("/home/mafeng/wallet");
        LOGGER.info("Generating wallet file...");
        WalletUtils.generateNewWalletFile("mafeng0909", file, true);
        Credentials credentials = WalletUtils.loadCredentials("mafeng0909", file);
        LOGGER.info("Generating wallet file: {}", file);
        LOGGER.info("Generating account address: {}", credentials.getAddress());
        EthAccounts accounts = web3j.ethAccounts().send();

        LOGGER.info("Accounts size: {}", accounts.getAccounts().size());
        accounts.getAccounts().forEach(acc -> LOGGER.info("Account: {}", acc));
    }

    @Test
    public void test01() {
        String path = "/home/mafeng/ZKPDemo/sudoku-zk-snarks/build/src/proof_data";
        ContractParams contractParams = blockChainService.getContractParams(path);
        System.out.println(contractParams.getParamA1());
        System.out.println(contractParams.getParamA2());
        System.out.println(contractParams.getParamB1());
        System.out.println(contractParams.getParamB2());
        System.out.println(contractParams.getParamB3());
        System.out.println(contractParams.getParamB4());
        System.out.println(contractParams.getParamC1());
        System.out.println(contractParams.getParamC2());
    }

}