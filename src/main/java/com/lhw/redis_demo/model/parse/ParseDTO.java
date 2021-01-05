package com.lhw.redis_demo.model.parse;

import lombok.Data;

import java.util.List;

@Data
public class ParseDTO {

    private String mapName;
    private String productName;
    private List<BasicData> basicData;
    private List<DetailData> detailData;
    private List<SumData> sumData;
    private List<LandLevels> landLevels;

}
