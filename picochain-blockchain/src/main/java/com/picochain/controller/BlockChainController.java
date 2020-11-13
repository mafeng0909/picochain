package com.picochain.controller;

import com.picochain.config.ProofProperties;
import com.picochain.pojo.BlockchainTransaction;
import com.picochain.pojo.ContractParams;
import com.picochain.service.BlockChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author mafeng
 */
@RestController
@EnableConfigurationProperties(ProofProperties.class)
public class BlockChainController {

    private final BlockChainService blockChainService;
    private final ProofProperties props;

    @Autowired
    public BlockChainController(BlockChainService blockChainService, ProofProperties props) {
        this.blockChainService = blockChainService;
        this.props = props;
    }

    /**
     * 发送交易
     *
     * @param transaction
     * @return
     * @throws IOException
     */
    @PostMapping("transaction")
    public ResponseEntity<BlockchainTransaction> execute(@RequestBody BlockchainTransaction transaction) throws IOException {
        return ResponseEntity.ok(blockChainService.process(transaction));
    }

    /**
     * 获取合约数据
     *
     * @return
     */
    @GetMapping("contractParams")
    public ResponseEntity<ContractParams> getContractParams() {
        return ResponseEntity.ok(blockChainService.getContractParams(props.getPath()));
    }
}
