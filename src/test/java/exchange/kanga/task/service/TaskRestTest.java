package exchange.kanga.task.service;

import exchange.kanga.task.data.Rank;
import exchange.kanga.task.impl.Calculation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class TaskRestTest {

    private Calculation calculation;
    private TaskRest taskRest;

    @BeforeEach
    public void setup() {
        calculation = mock(Calculation.class);
        taskRest = new TaskRest();
        taskRest.calculation = calculation;
    }

    @Test
    public void testCalculateRank_ShouldReturnOk() {
        ResponseEntity<String> response = taskRest.calculateRank();

        verify(calculation, times(1)).calcRank();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("done", response.getBody());
    }

    @Test
    public void testShowRank_WhenRankExists_ShouldReturnOk() {
        Rank rank = new Rank();
        when(calculation.getRank()).thenReturn(rank);

        ResponseEntity<Rank> response = taskRest.showRank();

        verify(calculation, times(1)).getRank();
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(rank, response.getBody());
    }

    @Test
    public void testShowRank_WhenRankIsNull_ShouldReturn404() {
        when(calculation.getRank()).thenReturn(null);

        ResponseEntity<Rank> response = taskRest.showRank();
        verify(calculation, times(1)).getRank();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}