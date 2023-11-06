package controllersTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.devops_project.controllers.OperatorController;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.services.Iservices.IOperatorService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OperatorControllerTest {

    @Mock
    IOperatorService operatorService;

    @InjectMocks
    OperatorController operatorController;

    @Test
    public void getOperatorsTest() {
        when(operatorService.retrieveAllOperators()).thenReturn(Arrays.asList(
                new Operator(1L,"salah","ibrahim","123", null),
                new Operator(2L,"ahmed","barh","555", null)
        ));

        List<Operator> operators = operatorController.getOperators();

        assertEquals(2, operators.size());
    }

    @Test
    public void retrieveOperatorTest() {
        when(operatorService.retrieveOperator(1L)).thenReturn(new Operator(1L,"salah","ibrahim","123", null));

        Operator operator = operatorController.retrieveoperator(1L);

        assertEquals(1L, operator.getIdOperateur());
        assertEquals("salah", operator.getFname());
        assertEquals("ibrahim", operator.getLname());
        assertEquals("123", operator.getPassword());
    }

    @Test
    public void addOperatorTest() {
        Operator newOperator = new Operator(1L,"salah","ibrahim","123", null);

        when(operatorService.addOperator(newOperator)).thenReturn(newOperator);

        Operator addedOperator = operatorController.addOperator(newOperator);

        assertEquals(newOperator, addedOperator);
    }

    @Test
    public void removeOperatorTest() {
        operatorController.removeOperator(1L);
        verify(operatorService).deleteOperator(1L);
    }

    @Test
    public void modifyOperateurTest() {
        Operator operator = new Operator(1L,"salah","ibrahim","123", null);

        when(operatorService.updateOperator(operator)).thenReturn(operator);

        Operator modifiedOperator = operatorController.modifyOperateur(operator);

        assertEquals(operator, modifiedOperator);
    }
}

