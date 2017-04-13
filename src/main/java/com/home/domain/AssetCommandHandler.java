package com.home.domain;

import com.home.domain.enumeration.AssetStatus;
import com.home.web.rest.AssetResource;

import org.springframework.stereotype.Component;

@Component
public class AssetCommandHandler {

    public void handle(Asset asset){

        asset.setInventoryCode("INV/"+asset.getId());
        asset.setStatus(AssetStatus.ACQUIRED);
    }

}

