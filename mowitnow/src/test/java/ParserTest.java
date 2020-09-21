import com.pse.mowitnow.business.Move;
import com.pse.mowitnow.business.Orientation;
import com.pse.mowitnow.entities.Grid;
import com.pse.mowitnow.entities.Mower;
import com.pse.mowitnow.exceptions.InstructionsNotFoundException;
import com.pse.mowitnow.exceptions.LawnCoordinatesException;
import com.pse.mowitnow.exceptions.MoveException;
import com.pse.mowitnow.exceptions.MowerCoordinatesException;
import com.pse.mowitnow.parser.Parser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

import static com.pse.mowitnow.exceptions.ErrorInfo.*;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {


    @Test
    public void should_return_a_stream_of_instructions() throws IOException, URISyntaxException, InstructionsNotFoundException {
        Stream<String> streamInst = Parser.getInstrtuctionsStream("instructions.txt");
        assertNotNull(streamInst);
    }

    @Test
    public void should_throw_instructions_not_found_exception() {
        Exception exception = assertThrows(InstructionsNotFoundException.class, () -> Parser.getInstrtuctionsStream("notfound.txt"));
        String expectedMessage = INSTRUCTIONS_NOT_FOUND_EXCEPTION.getMessage();
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void should_return_grid_positive_lawn_coordinates() throws LawnCoordinatesException {
        Grid grid = Parser.parseGrid("5 5");
        assert (grid.getMaxPosition().getX() > 0 && grid.getMaxPosition().getY() > 0);
    }

    @Test
    public void should_throw_lawn_coordinate_exception_with_format_msg() {
        Exception exception = assertThrows(LawnCoordinatesException.class, () -> Parser.parseGrid("9**8"));
        String expectedMessage = LAWN_COORDINATES_EXCEPTION_FORMAT.getMessage();
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void should_throw_lawn_coordinate_exception_with_value_msg() {
        Exception exception = assertThrows(LawnCoordinatesException.class, () -> Parser.parseGrid("0 0"));
        String expectedMessage = LAWN_COORDINATES_EXCEPTION_VALUES.getMessage();
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void should_return_mower_with_valid_position_and_orientation() {
        Mower mower = Parser.parseNewMower("1 2 N", 1);
        assertEquals(1, mower.getPosition().getX());
        assertEquals(2, mower.getPosition().getY());
        assertEquals(Orientation.N, mower.getOrientation());
    }

    @Test
    public void should_throw_mower_coordinate_exception_with_format_message() {
        Exception exception = assertThrows(MowerCoordinatesException.class, () -> Parser.parseNewMower("wrong", 1));
        String expectedMessage = MOWER_COORDINATES_EXCEPTION_FORMAT.getMessage();
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void should_return_valid_queue_of_moves() {
        Queue<Move> moves = Parser.parseMoves("GAGA");
        assertNotNull(moves);
        assertEquals(4, moves.size());

        LinkedList<Move> expectedMoves = new LinkedList<>();
        expectedMoves.add(Move.ROTATE_LEFT);
        expectedMoves.add(Move.FORWARD);
        expectedMoves.add(Move.ROTATE_LEFT);
        expectedMoves.add(Move.FORWARD);

        assertEquals(expectedMoves, moves);
    }


    @Test
    public void should_return_move_exception(){
        Exception exception =   assertThrows(MoveException.class,() -> Parser.parseMoves("GA#wrong#GA") );
        String expectedMessage = MOVE_EXCEPTION_WRONG_MOVE.getMessage();
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}






