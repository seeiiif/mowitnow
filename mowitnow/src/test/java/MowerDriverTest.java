import com.pse.mowitnow.business.Move;
import com.pse.mowitnow.business.MowerDriver;
import com.pse.mowitnow.business.Orientation;
import com.pse.mowitnow.entities.Grid;
import com.pse.mowitnow.entities.Mower;
import com.pse.mowitnow.entities.Position;
import com.pse.mowitnow.exceptions.InstructionsNotFoundException;
import com.pse.mowitnow.exceptions.MowerCoordinatesException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.pse.mowitnow.business.Move.FORWARD;
import static com.pse.mowitnow.business.Move.ROTATE_RIGHT;
import static com.pse.mowitnow.exceptions.ErrorInfo.MOWER_COORDINATES_EXCEPTION_OUT_OF_LAWN;
import static org.junit.jupiter.api.Assertions.*;


public class MowerDriverTest {

    static MowerDriver mowerDriver;
    static Mower mower1, mower2;
    static Grid grid;
    static Queue<Move> moves;

    @BeforeAll
    public static void setUp() {
        mowerDriver = new MowerDriver();
        grid = new Grid(new Position(5, 5));
        mower1 = new Mower(1, new Position(3, 3), Orientation.E);
        mower2 = new Mower(1, new Position(1, 2), Orientation.N);

        moves = mockMovesQueue();
    }

    private static Queue<Move> mockMovesQueue() {
        moves = new LinkedList<>();
        moves.add(FORWARD);
        moves.add(FORWARD);
        moves.add(ROTATE_RIGHT);
        moves.add(FORWARD);
        moves.add(FORWARD);
        moves.add(ROTATE_RIGHT);
        moves.add(FORWARD);
        moves.add(ROTATE_RIGHT);
        moves.add(ROTATE_RIGHT);
        moves.add(FORWARD);
        return moves;
    }

    @Test
    public void driveTest() throws IOException, URISyntaxException, InstructionsNotFoundException {
        List<Mower> terminatedMowers = mowerDriver.drive("instructions.txt");
        assertNotNull(terminatedMowers);
        assertEquals(2, terminatedMowers.size());
        Mower mowerA = terminatedMowers.get(0);
        Mower mowerB = terminatedMowers.get(1);
        assertEquals(1,mowerA.getPosition().getX());
        assertEquals(3, mowerA.getPosition().getY());
        assertEquals(Orientation.N, mowerA.getOrientation());
        assertEquals(5, mowerB.getPosition().getX());
        assertEquals(1, mowerB.getPosition().getY());
        assertEquals(Orientation.E, mowerB.getOrientation());
    }


    @Test
    public void should_return_mower_with_valid_position_and_orientation() {
        Mower mower = mowerDriver.loadMower("3 5 E", 2, grid);
        assertNotNull(mower);
        assertEquals(2, mower.getMowerId());
        assertEquals(3, mower.getPosition().getX());
        assertEquals(5, mower.getPosition().getY());
        assertEquals(Orientation.E, mower.getOrientation());
    }


    @Test
    public void should_throw_mower_coordinates_out_of_lawn_exception() {
        Exception exception = assertThrows(MowerCoordinatesException.class, () -> mowerDriver.loadMower("9 5 N", 1, grid));
        String expectedMessage = MOWER_COORDINATES_EXCEPTION_OUT_OF_LAWN.getMessage();
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void should_return_a_terminated_mower_with_valid_positions() {
        Mower terminatedMower = mowerDriver.startMower(moves, mower1);
        assertNotNull(terminatedMower);
        assertEquals(5, terminatedMower.getPosition().getX());
        assertEquals(1, terminatedMower.getPosition().getY());
        assertEquals(Orientation.E, terminatedMower.getOrientation());
    }


    @Test
    public void should_return_mower_in_the_same_position_and_orientation_when_moves_queue_is_empty() {
        Queue<Move> noMoves = new LinkedList<>();
        Mower terminatedMower = mowerDriver.startMower(noMoves, mower2);
        assertNotNull(terminatedMower);
        assertEquals(1, terminatedMower.getPosition().getX());
        assertEquals(2, terminatedMower.getPosition().getY());
        assertEquals(Orientation.N, terminatedMower.getOrientation());
    }
}
