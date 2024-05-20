package com.ssafy.enjoytrips.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotPlace {
    private int hotplaceId;
    private String hotplaceName;
    private double latitude;
    private double longitude;
    private String addr1;
    private String addr2;
    private String comment;
    private int category;
    private String image;
    private String writer;
}
