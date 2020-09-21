package com.pse.mowitnow.business;

import com.pse.mowitnow.entities.Mower;
import com.pse.mowitnow.entities.Position;

public enum Orientation {
    N {
        @Override
        void moveForward(Position position) {
            position.setY(position.getY() + 1);
        }

        @Override
        void rotateRight(Mower mower) {
            mower.setOrientation(E);
        }

        @Override
        void rotateLeft(Mower mower) {
            mower.setOrientation(W);
        }
    },

    E {
        @Override
        void moveForward(Position position) {
            position.setX(position.getX() + 1);
        }

        @Override
        void rotateRight(Mower mower) {
            mower.setOrientation(S);
        }

        @Override
        void rotateLeft(Mower mower) {
            mower.setOrientation(N);
        }
    },
    W {
        @Override
        void moveForward(Position position) {
            position.setX(position.getX() - 1);
        }

        @Override
        void rotateRight(Mower mower) {
            mower.setOrientation(N);
        }

        @Override
        void rotateLeft(Mower mower) {
            mower.setOrientation(S);
        }
    },

    S {
        @Override
        void moveForward(Position position) {
            position.setY(position.getY() - 1);
        }

        @Override
        void rotateRight(Mower mower) {
            mower.setOrientation(W);
        }

        @Override
        void rotateLeft(Mower mower) {
            mower.setOrientation(E);
        }
    };


    abstract void moveForward(Position position);

    abstract void rotateRight(Mower mower);

    abstract void rotateLeft(Mower mower);

}
