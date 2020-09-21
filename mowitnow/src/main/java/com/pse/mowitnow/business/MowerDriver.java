package com.pse.mowitnow.business;

import com.pse.mowitnow.parser.Parser;
import com.pse.mowitnow.entities.Grid;
import com.pse.mowitnow.entities.Mower;
import com.pse.mowitnow.entities.Position;
import com.pse.mowitnow.exceptions.InstructionsNotFoundException;
import com.pse.mowitnow.exceptions.MowerCoordinatesException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

import static com.pse.mowitnow.exceptions.ErrorInfo.MOWER_COORDINATES_EXCEPTION_OUT_OF_LAWN;

public class MowerDriver {

    private Grid grid;
    private Mower mower;

    Logger logger = Logger.getLogger(MowerDriver.class);

    /**
     * @param instructions file name
     * @return List of terminated mowers with new position and orientation
     * @throws IOException
     * @throws URISyntaxException
     * @throws InstructionsNotFoundException
     */

    public List<Mower> drive(String instructions) throws IOException, URISyntaxException, InstructionsNotFoundException {
        AtomicInteger lineNb = new AtomicInteger(0);
        AtomicInteger mowerId = new AtomicInteger(1);
        List<Mower> terminatedMowers = new ArrayList<>();

        Parser.getInstrtuctionsStream(instructions).forEach(line -> {
                    if (lineNb.intValue() == 0) {
                        this.grid = Parser.parseGrid(line);

                    } else if (lineNb.intValue() % 2 == 0) {
                        Mower terminatedMower = startMower(Parser.parseMoves(line), this.mower);
                        terminatedMowers.add(terminatedMower);

                    } else {
                        this.mower = loadMower(line, mowerId.intValue(), this.grid);
                        mowerId.getAndIncrement();
                    }

                    lineNb.incrementAndGet();
                }
        );

        terminatedMowers.stream().forEach(logger::info);
        return terminatedMowers;
    }


    /**
     * @param line    contains data to parse about actual mower coordinates and orientation
     * @param mowerId Id of the mower that will be loaded
     * @param grid    The Lawn on which the mower will operate
     * @return loaded mower after position controls
     * @throws MowerCoordinatesException to notify that mower is out of lawn
     */
    public Mower loadMower(String line, int mowerId, Grid grid) {
        Mower mower = Parser.parseNewMower(line, mowerId);

        if (isOutOfLawn(mower.getPosition(), grid.getMaxPosition())) {
            throw new MowerCoordinatesException(MOWER_COORDINATES_EXCEPTION_OUT_OF_LAWN.getMessage());
        }
        return mower;
    }

    /**
     * @param moves a queue of moves
     * @param mower a mower on which a queue of moves will be performed
     * @return terminated mower
     */
    public Mower startMower(Queue<Move> moves, Mower mower) {
        while (!moves.isEmpty()) {
            moves.poll().run(mower);

            if (isOutOfLawn(mower.getPosition(), grid.getMaxPosition())) {
                throw new MowerCoordinatesException(MOWER_COORDINATES_EXCEPTION_OUT_OF_LAWN.getMessage());
            }
        }
        return mower;
    }


    /**
     *
     * @param mowerPos actual mower position
     * @param maxPos maximum position in the lawn
     * @return true if mower is still on the lawn, false if not
     */
    public boolean isOutOfLawn(Position mowerPos, Position maxPos) {
        return mowerPos.getX() > maxPos.getX() || mowerPos.getY() > maxPos.getY();
    }

}
