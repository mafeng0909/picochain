package com.picochain.mapper;

import com.picochain.pojo.ShareData;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author mafeng
 */
@Component
public interface ShareMapper extends Mapper<ShareData> {
    
}
