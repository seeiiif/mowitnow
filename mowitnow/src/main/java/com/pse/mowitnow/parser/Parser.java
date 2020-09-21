package com.pse.mowitnow.parser;


import com.pse.mowitnow.business.Move;
import com.pse.mowitnow.business.Orientation;
import com.pse.mowitnow.entities.Grid;
import com.pse.mowitnow.entities.Mower;
import com.pse.mowitnow.entities.Position;
import com.pse.mowitnow.exceptions.InstructionsNotFoundException;
import com.pse.mowitnow.exceptions.LawnCoordinatesException;
import com.pse.mowitnow.exceptions.MoveException;
import com.pse.mowitnow.exceptions.MowerCoordinatesException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Stream;

import static com.pse.mowitnow.parser.Pattern.*;
import static com.pse.mowitnow.exceptions.ErrorInfo.*;

public final class Parser {

    private Parser() {
    }

    /**
     *
     * @param instructions instructions file name
     * @return Stream of string
     * @throws URISyntaxException
     * @throws IOException
     * @throws InstructionsNotFoundException
     */
    public static Stream<String> getInstrtuctionsStream(String instructions) throws URISyntaxException, IOException, InstructionsNotFoundException {
        Optional<URL> instructionsUrl = Optional.ofNullable(ClassLoader.getSystemResource(instructions));

        if (instructionsUrl.isEmpty()) {
            throw new InstructionsNotFoundException(INSTRUCTIONS_NOT_FOUND_EXCEPTION.getMessage());
        }

        return Files.lines(Paths.get(ClassLoader.getSystemResource(instructions).toURI()));
    }

    /**
     * Parse and Return Grid // lawn object with its coordinates
     * @param line contains data about the lawn'coordinates
     * @return Grid object with the lawn coordinates
     * @throws LawnCoordinatesException
     */
    public static Grid parseGrid(String line) throws LawnCoordinatesException {

        if (!(line.matches(GRID_PATTERN.getPattern()))) {
            throw new LawnCoordinatesException(LAWN_COORDINATES_EXCEPTION_FORMAT.getMessage());
        }

        String[] xy = line.split(SEPARATOR.getPattern());
        int x = Integer.valueOf(xy[0]);
        int y = Integer.valueOf(xy[1]);
        if (x <= 0 || y <= 0) {
            throw new LawnCoordinatesException(LAWN_COORDINATES_EXCEPTION_VALUES.getMessage());
        }

        return new Grid(new Position(Integer.valueOf(xy[0]), Integer.valueOf(xy[1])));
    }

    /**
     * Parse and Return Mower object with initial position and orientation
     * @param line contains data to parse about mower position and orientation
     * @param mowerId Id of the new mower
     * @return a new Mower object
     */

    public static Mower parseNewMower(String line, int mowerId) {
        if (!line.matches(MOWER_PATTERN.getPattern())) {
            throw new MowerCoordinatesException(MOWER_COORDINATES_EXCEPTION_FORMAT.getMessage());
        }
        String initState[] = line.split(SEPARATOR.getPattern());
        Position position = new Position(Integer.valueOf(initState[0]), Integer.valueOf(initState[1]));
        Orientation orientation = Orientation.valueOf(initState[2]);
        return new Mower(mowerId, position, orientation);
    }

    /**
     * Parse and return queue of Moves to be operated by a mower
     * @param line contains data to parse about instructions to be executed
     * @return Queue of Moves
     */
    public static Queue<Move> parseMoves(String line) {
        Queue<Move> instructions = new LinkedList<>();
        for (char instruction : line.toCharArray()) {
            switch (instruction) {
                case 'G':
                    instructions.add(Move.ROTATE_LEFT);
                    break;
                case 'D':
                    instructions.add(Move.ROTATE_RIGHT);
                    break;
                case 'A':
                    instructions.add(Move.FORWARD);
                    break;
                default:
                    throw new MoveException(MOVE_EXCEPTION_WRONG_MOVE.getMessage());

            }
        }
        return instructions;
    }
}
