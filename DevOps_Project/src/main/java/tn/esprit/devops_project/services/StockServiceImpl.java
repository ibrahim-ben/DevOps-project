package tn.esprit.devops_project.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.devops_project.services.Iservices.IStockService;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.List;
import io.micrometer.core.annotation.Counted;
import org.springframework.stereotype.Service;
@Service
@AllArgsConstructor
public class StockServiceImpl implements IStockService {

   private final StockRepository stockRepository;

    @Override
    public Stock addStock(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public Stock retrieveStock(Long id) {
        return stockRepository.findById(id).orElseThrow(() -> new NullPointerException("Stock not found"));
    }

    @Override
    @Counted(value = "myapp.requests.processed", description = "Number of requests processed")

    public List<Stock> retrieveAllStock() {
        return stockRepository.findAll();
    }



}
