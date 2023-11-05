package tn.esprit.devops_project.controllers;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.services.Iservices.IStockService;
import java.util.List;
import io.prometheus.client.Counter;
import io.prometheus.client.spring.web.PrometheusTimeMethod;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class StockController {

    IStockService stockService;
    private final Counter requestsTotal = Counter.build()
        .name("myapp_requests_total")
        .help("Total HTTP requests.")
        .labelNames("method")
        .register();

    @PrometheusTimeMethod(name = "myapp_request_duration_seconds", help = "Request duration in seconds.")
    @GetMapping("/myendpoint")
    public String myEndpoint() {
        requestsTotal.labels("GET").inc();
        return "Response";
    }

    @PostMapping("/stock")
    public Stock addStock(@RequestBody Stock stock){
        return stockService.addStock(stock);
    }

    @GetMapping("/stock/{id}")
    public Stock retrieveStock(@PathVariable Long id){
        return stockService.retrieveStock(id);
    }

    @GetMapping("/stock")
    public List<Stock> retrieveAllStock(){
        return stockService.retrieveAllStock();
    }

    


}
