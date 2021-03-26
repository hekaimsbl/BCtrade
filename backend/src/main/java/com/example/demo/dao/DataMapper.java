package com.example.demo.dao;


import com.example.demo.model.DataAsset;

import javax.xml.crypto.Data;
import java.util.List;

public interface DataMapper {
    public void insertData(DataAsset dataAsset);
    public List<String> getDataIdById(String ownerId);
    public List<DataAsset> getDataById(String ownerId);
}

