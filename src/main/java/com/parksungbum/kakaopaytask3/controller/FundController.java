package com.parksungbum.kakaopaytask3.controller;

import com.parksungbum.kakaopaytask3.service.FundService;
import com.parksungbum.kakaopaytask3.service.dto.AnnualFundStatisticsResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FundController {

    private final FundService fundService;

    public FundController(FundService fundService) {
        this.fundService = fundService;
    }

    @GetMapping("/funds/statistics")
    public ResponseEntity<List<AnnualFundStatisticsResponseDto>> showStatistics() {
        List<AnnualFundStatisticsResponseDto> annualFundStatistics =
                fundService.findAnnualFundStatistics();

        return ResponseEntity.ok(annualFundStatistics);
    }

    @GetMapping("/funds/temp")
    public ResponseEntity<Void> temp() {
        fundService.temp();

        return ResponseEntity.ok().build();
    }
}
