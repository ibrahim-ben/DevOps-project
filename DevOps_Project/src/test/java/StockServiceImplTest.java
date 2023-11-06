import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.StockRepository;
import tn.esprit.devops_project.services.StockServiceImpl;

@ExtendWith(MockitoExtension.class)
public class StockServiceImplTest {

    @Mock
    StockRepository stockRepository;

    @InjectMocks
    StockServiceImpl stockService;

    @Test
    public void retrieveAllStocksTest() {
        when(stockRepository.findAll()).thenReturn(
                Arrays.asList(
                        new Stock(1L, "delice", null),
                        new Stock(2L, "coca", null),
                        new Stock(3L, "vitaliat", null)
                )
        );

        assertEquals(3, stockService.retrieveAllStock().size());
    }

    @Test
    public void addStockTest() {
        Stock stock = new Stock(1L, "delice", null);
        when(stockRepository.save(stock)).thenReturn(stock);
        assertEquals(stock, stockService.addStock(stock));
    }

    @Test
    public void retrieveStockFoundTest() {
        Stock stock = new Stock(2L, "coca", null);
        when(stockRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(stock));
        Stock stock1 = stockService.retrieveStock(2L);
        Assertions.assertNotNull(stock1);
    }

    @Test
    public void retrieveStockNotFoundTest() {
        when(stockRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(NullPointerException.class, () -> {
            stockService.retrieveStock(2L);
        });
    }

}
