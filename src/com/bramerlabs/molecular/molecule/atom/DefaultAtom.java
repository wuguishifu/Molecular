package com.bramerlabs.molecular.molecule.atom;

import com.bramerlabs.engine.math.Vector3f;

public class DefaultAtom extends Atom {

    // default radius
    final static float ATOMIC_RADIUS = 1.f;

    public DefaultAtom(Vector3f position, float radius, Vector3f color) {
        super(position, radius, color);
    }

    @Override
    public float getAtomicRadius() {
        return ATOMIC_RADIUS;
    }

}
