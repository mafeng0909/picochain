package com.picochain.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mafeng
 */
@Data
@NoArgsConstructor
public class BlockchainTransaction {

    private String id;
    private Integer fromId;
    private Integer toId;
    private Long value;
    private Boolean accepted;

    public BlockchainTransaction(int fromId, int toId, long value) {
        this.fromId = fromId;
        this.toId = toId;
        this.value = value;
    }
}
