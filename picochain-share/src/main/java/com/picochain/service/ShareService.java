package com.picochain.service;

import com.picochain.mapper.ShareMapper;
import com.picochain.pojo.ShareData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mafeng
 */
@Service
public class ShareService {

    private final ShareMapper shareMapper;

    @Autowired
    public ShareService(ShareMapper shareMapper) {
        this.shareMapper = shareMapper;
    }

    /**
     *
     * @return
     */
    public List<ShareData> getData() {
//        List<ShareData> res = new ArrayList<>();
//        ShareData data = new ShareData();
//        res.add(data);

        return shareMapper.selectAll();
    }
}
