package com.picochain.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author mafeng
 */
@Data
@Table(name = "share_work")
public class ShareData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String name;

    private String author;

    private String work_desc;

    private String ipfs;

    private String address;


}
