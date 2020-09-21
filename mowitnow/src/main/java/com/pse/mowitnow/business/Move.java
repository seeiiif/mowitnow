package com.pse.mowitnow.business;

import com.pse.mowitnow.entities.Mower;

public enum Move {
    ROTATE_RIGHT{
        @Override
        void run(Mower mower) {
            mower.getOrientation().rotateRight(mower);
        }
    },
    ROTATE_LEFT {
        @Override
        void run(Mower mower) {
            mower.getOrientation().rotateLeft(mower);
        }
    },
    FORWARD {
        @Override
        void run(Mower mower) {
            mower.getOrientation().moveForward(mower.getPosition());
        }
    };


    abstract void run(Mower mower);

}
