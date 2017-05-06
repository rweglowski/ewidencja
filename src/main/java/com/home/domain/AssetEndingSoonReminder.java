package com.home.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AssetEndingSoonReminder {
    private Long employeeId;
    private String firstName;
    private String surname;
    private String email;
    private String assetName;
    private String assetInventoryCode;
}
