package hamro.stockmarket.stockmarket.controller;

import hamro.stockmarket.stockmarket.excpetion.NotFoundException;
import hamro.stockmarket.stockmarket.service.DetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DetailsController {
    private final DetailsService detailsService;

    public DetailsController(DetailsService detailsService) {
        this.detailsService = detailsService;
    }

    @GetMapping("/data")
    public void getData(@RequestParam(value = "symbol") String symbol) {
        if (checkNullAndEmpty(symbol)){
            throw new NotFoundException("symbol was not found");
        }
        detailsService.getStockQuote(symbol);
    }

    Boolean checkNullAndEmpty(String value) {
        return value == null || value.isEmpty();
    }
}
