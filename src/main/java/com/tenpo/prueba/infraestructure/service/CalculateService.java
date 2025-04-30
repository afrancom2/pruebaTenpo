package com.tenpo.prueba.infraestructure.service;

import com.tenpo.prueba.api.models.request.SumRequest;
import com.tenpo.prueba.api.models.response.SumResponse;
import com.tenpo.prueba.domain.entity.Percentage;
import com.tenpo.prueba.infraestructure.abstract_service.ICalculate;
import com.tenpo.prueba.infraestructure.abstract_service.IPercentageService;
import com.tenpo.prueba.util.Constants;
import com.tenpo.prueba.util.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculateService implements ICalculate {

    private final IPercentageService percentageService;
    private final HistoryService historyService;

    @Override
    public Double calculateSum(SumRequest sumRequest) {
        log.info("Calculate sum INIT");
        if (sumRequest == null || sumRequest.getFirst() == null || sumRequest.getSecond() == null)
            throw new ForbiddenException();
        Percentage percentage = Percentage.builder()
                .percentageValue(percentageService.gettingPercentage().getPercentage())
                .build();
        log.info("Calculate sum percentage service: {}", percentage);

        double total = (sumRequest.getFirst() + sumRequest.getSecond()) + ((sumRequest.getFirst() + sumRequest.getSecond()) * percentage.getPercentageValue() / 100);

        log.info("Calculate sum total: {}", total);

        SumResponse sumResponse = SumResponse.builder()
                .status(Constants.STATUS_OK)
                .result(total)
                .build();

        log.info("Calculate sum sumResponse: {}", sumResponse);

        log.info("Calculate sum save history");
        historyService.saveHistory(Constants.RESOURCE_SUM, sumRequest.toString(), sumResponse.toString(), false);
        log.info("Calculate sum FIN");
        return sumResponse.getResult();
    }
}
