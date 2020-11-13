package com.picochain.service;

import com.picochain.pojo.BlockchainTransaction;
import com.picochain.pojo.ContractParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * @author mafeng
 */
@Service
public class BlockChainService {

    private final Web3j web3j;
    private static final Logger LOGGER = LoggerFactory.getLogger(BlockChainService.class);

    @Autowired
    public BlockChainService(Web3j web3j) {
        this.web3j = web3j;
    }

    /**
     * 发送交易
     *
     * @param tx
     * @return
     */
    public BlockchainTransaction process(BlockchainTransaction tx) throws IOException {

        EthAccounts accounts = web3j.ethAccounts().send();
        EthGetTransactionCount transactionCount = web3j.ethGetTransactionCount(accounts.getAccounts().get(tx.getFromId()), DefaultBlockParameterName.LATEST).send();

        Transaction transaction = Transaction.createEtherTransaction(
                accounts.getAccounts().get(tx.getFromId()), transactionCount.getTransactionCount(), BigInteger.valueOf(tx.getValue()),
                BigInteger.valueOf(21_000), accounts.getAccounts().get(tx.getToId()), BigInteger.valueOf(tx.getValue()));

        EthSendTransaction response = web3j.ethSendTransaction(transaction).send();

        if (response.getError() != null) {
            tx.setAccepted(false);
            LOGGER.info("Tx rejected: {}", response.getError().getMessage());
            return tx;
        }

        tx.setAccepted(true);
        String txHash = response.getTransactionHash();
        LOGGER.info("Tx hash: {}", txHash);

        tx.setId(txHash);
        EthGetTransactionReceipt receipt = web3j.ethGetTransactionReceipt(txHash).send();

        receipt.getTransactionReceipt().ifPresent(transactionReceipt -> LOGGER.info("Tx receipt:  {}", transactionReceipt.getCumulativeGasUsed().intValue()));

        return tx;

    }

    /**
     * 获取合约数据
     *
     * @param path
     * @return
     */
    public ContractParams getContractParams(String path) {
        ContractParams contractParams = new ContractParams();

        try {
            //
            File file = new File(path);
            if (file.isFile() && file.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(isr);
                String lineTxt = null;
                int indexLine = 0;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    indexLine++;
                    if (indexLine == 1) {
                        contractParams.setParamA1(lineTxt);
                    }else if (indexLine == 2) {
                        contractParams.setParamA2(lineTxt);
                    }else if (indexLine == 3) {
                        String[] s = lineTxt.trim().split(" ");
                        contractParams.setParamB2(s[0]);
                        contractParams.setParamB1(s[1]);
                    }else if (indexLine == 4) {
                        String[] s = lineTxt.trim().split(" ");
                        contractParams.setParamB4(s[0]);
                        contractParams.setParamB3(s[1]);
                    }else if (indexLine == 5) {
                        contractParams.setParamC1(lineTxt);
                    }else if (indexLine == 6) {
                        contractParams.setParamC2(lineTxt);
                    }
                }
                if (indexLine != 6) {
                    LOGGER.error("读取文件内容出错: {}", path);
                    return new ContractParams();
                }
                isr.close();
            }else {
                LOGGER.error("找不到指定文件: {}", path);
                return new ContractParams();
            }
            return contractParams;
        }catch (Exception e) {
            LOGGER.error("读取文件内容出错: {}", path);
            return new ContractParams();
        }
    }
}
