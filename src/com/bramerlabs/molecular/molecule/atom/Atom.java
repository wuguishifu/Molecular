package com.bramerlabs.molecular.molecule.atom;

import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.objects.shapes.Sphere;
import com.bramerlabs.molecular.molecule.atom.data_compilers.AtomicDataCompiler;

public class Atom {

    // identity data
    private int atomicNumber; // the atomic number of this atom - default Hydrogen
    private String atomicAbbrName = "H"; // the abbreviated name - default Hydrogen
    private int charge = 0; // the charge of the atom - default neutral
    private int numNeutrons = 0; // the amount of neutrons this atom has - default 0

    // locational data
    private Vector3f position; // the position of the atom
    private float radius; // the radius of the atom

    // rendering
    private Vector3f color; // the color of this atom
    private Sphere sphere; // the sphere used for rendering the atom

    // selection variables
    private boolean isSelected = false; // if this atom is selected or not
    private Sphere selectionSphere; // the sphere used for rendering the selection box
    private Vector3f selectionColor = new Vector3f(0.5f, 0.5f, 0.f); // the color of the selection sphere - default yellow

    // direction of the atom - default +y direction
    private Vector3f direction = new Vector3f(0, 1, 0);

    /**
     * default constructor
     * @param position - the position of this atom
     * @param atomicNumber - the atomic number of this atom
     */
    public Atom(Vector3f position, int atomicNumber) {
        this.position = position;
        this.atomicNumber = atomicNumber;

        this.radius = AtomicDataCompiler.getVDWRadius(atomicNumber);
        this.color = AtomicDataCompiler.getCPKColor(atomicNumber);

        makeSphere();
        makeSelectionSphere();
    }

    /**
     * default constructor
     * @param position - the position of this atom
     * @param atomicNumber - the atomic number of this atom
     * @param connectedAtom - the atom this atom is bonded to
     */
    public Atom(Vector3f position, int atomicNumber, Atom connectedAtom) {
        this.position = position;
        this.atomicNumber = atomicNumber;

        // the direction of the atom
        this.direction = Vector3f.subtract(position, connectedAtom.getPosition());

        this.radius = AtomicDataCompiler.getVDWRadius(atomicNumber);
        this.color = AtomicDataCompiler.getCPKColor(atomicNumber);

        makeSphere();
        makeSelectionSphere();
    }

    /**
     * sets the direction of this atom
     * @param dir - the direction
     */
    public void setDirection(Vector3f dir) {
        this.direction = dir;
    }

    /**
     * getter method
     * @return - the atomic radius of this atom
     */
    public float getAtomicRadius() {
        return this.radius;
    }

    /**
     * getter method
     * @return - the atomic number of this atom
     */
    public int getAtomicNumber() {
        return this.atomicNumber;
    }

    /**
     * getter method
     * @return - the charge of this atom
     */
    public int getCharge() {
        return this.charge;
    }

    /**
     * getter method
     * @return - the number of neutrons in this atom
     */
    public int getNumNeutrons() {
        return this.numNeutrons;
    }

    /**
     * getter method
     * @return - the abbreviated name of this atom
     */
    public String getAtomicAbbrName() {
        return this.atomicAbbrName;
    }

    /**
     * sets the atomic number of this atom
     * @param atomicNumber - the atomic number
     */
    public void setAtomicNumber(int atomicNumber) {
        this.atomicNumber = atomicNumber;
        this.atomicAbbrName = AtomicDataCompiler.getAtomAbbrName(atomicNumber);
    }

    /**
     * sets the charge of this atom
     * @param charge - the charge
     */
    public void setCharge(int charge) {
        this.charge = charge;
    }

    /**
     * sets the number of neutrons in this atom
     * @param neutrons - the number of neutrons
     */
    public void setNumNeutrons(int neutrons) {
        this.numNeutrons = neutrons;
    }

    /**
     * toggles the selection
     */
    public void toggleSelected() {
        this.isSelected = !isSelected;
    }

    /**
     * sets if this atom is selected or not
     * @param b - true or false
     */
    public void setSelected(boolean b) {
        this.isSelected = b;
    }

    /**
     * getter method
     * @return - a boolean representing if this atom is selected or not
     */
    public boolean isSelected() {
        return this.isSelected;
    }

    /**
     * makes the sphere used for rendering this atom
     */
    private void makeSphere() {
        sphere = Sphere.makeSphere(position, color, radius);
        sphere.createMesh();
    }

    /**
     * makes the sphere used for rendering the selection box of this atom
     */
    private void makeSelectionSphere() {
        selectionSphere = Sphere.makeSphere(position, selectionColor, radius + 0.5f);
        selectionSphere.createMesh();
    }

    /**
     * getter method
     * @return - the sphere used to render this atom
     */
    public Sphere getSphere() {
        return sphere;
    }

    /**
     * getter method
     * @return - the sphere used to render the selection box of this atom
     */
    public Sphere getSelectionSphere() {
        return this.selectionSphere;
    }

    /**
     * getter method
     * @return - the radius of this atom
     */
    public float getRadius() {
        return this.radius;
    }

    /**
     * getter method
     * @return - the position of this atom
     */
    public Vector3f getPosition() {
        return this.position;
    }

    /**
     * getter method
     * @return - the color of the atom
     */
    public Vector3f getColor() {
        return this.color;
    }

    /**
     * atomic numbers
     */

    public static int HYDROGEN = 1;
    public static int HELIUM = 2;
    public static int LITHIUM = 3;
    public static int BERYLLIUM =  4;
    public static int BORON = 5;
    public static int CARBON = 6;
    public static int NITROGEN = 7;
    public static int OXYGEN = 8;
    public static int FLUORINE = 9;
    public static int NEON = 10;
    public static int SODIUM = 11;
    public static int MAGNESIUM = 12;
    public static int ALUMINUM = 13;
    public static int SILICON = 14;
    public static int PHOSPHORUS = 15;
    public static int SULFUR = 16;
    public static int CHLORINE = 17;
    public static int ARGON = 18;
    public static int POTASSIUM = 19;
    public static int CALCIUM = 20;
    public static int SCANDIUM = 21;
    public static int TITANIUM = 22;
    public static int VANADIUM = 23;
    public static int CHROMIUM = 24;
    public static int MANGANESE = 25;
    public static int IRON = 26;
    public static int COBALT = 27;
    public static int NICKEL = 28;
    public static int COPPER = 29;
    public static int ZINC = 30;
    public static int GALLIUM = 31;
    public static int GERMANIUM = 32;
    public static int ARSENIC = 33;
    public static int SELENIUM = 34;
    public static int BROMINE = 35;
    public static int KRYPTON = 36;
    public static int RUBIDIUM = 37;
    public static int STRONTIUM = 38;
    public static int YTTRIUM = 39;
    public static int ZIRCONIUM = 40;
    public static int NIOBIUM = 41;
    public static int MOLYBDENUM = 42;
    public static int TECHNETIUM = 43;
    public static int RUTHENIUM = 44;
    public static int RHODIUM = 45;
    public static int PALLADIUM = 46;
    public static int SILVER = 47;
    public static int CADMIUM = 48;
    public static int INDIUM = 49;
    public static int TIN = 50;
    public static int ANTIMONY = 51;
    public static int TELLURIUM = 52;
    public static int IODINE = 53;
    public static int XENON = 54;
    public static int CESIUM = 55;
    public static int BARIUM = 56;
    public static int LANTHANUM = 57;
    public static int CERIUM = 58;
    public static int PRASEODYMIUM = 59;
    public static int NEODYMIUM = 60;
    public static int PROMETHIUM = 61;
    public static int SAMARIUM = 62;
    public static int EUROPIUM = 63;
    public static int GADOLINIUM = 64;
    public static int TERBIUM = 65;
    public static int DYSPROSIUM = 66;
    public static int HOLMIUM = 67;
    public static int ERBIUM = 68;
    public static int THULIUM = 69;
    public static int YTTERBIUM = 70;
    public static int LUTETIUM = 71;
    public static int HAFNIUM = 72;
    public static int TANTALUM = 73;
    public static int TUNGSTEN = 74;
    public static int RHENIUM = 75;
    public static int OSMIUM = 76;
    public static int IRIDIUM = 77;
    public static int PLATINUM = 78;
    public static int GOLD = 79;
    public static int MERCURY = 80;
    public static int THALLIUM = 81;
    public static int LEAD = 82;
    public static int BISMUTH = 83;
    public static int POLONIUM = 84;
    public static int ASTATINE = 85;
    public static int RADON = 86;
    public static int FRANCIUM = 87;
    public static int RADIUM = 88;
    public static int ACTINIUM = 89;
    public static int THORIUM = 90;
    public static int PROTACTINIUM = 91;
    public static int URANIUM = 92;
    public static int NEPTUNIUM = 93;
    public static int PLUTONIUM = 94;
    public static int AMERICIUM = 95;
    public static int CURIUM = 96;
    public static int BERKELIUM = 97;
    public static int CALIFORNIUM = 98;
    public static int EINSTEINIUM = 99;
    public static int FERMIUM = 100;
    public static int MENDELEVIUM = 101;
    public static int NOBELIUM = 102;
    public static int LAWRENCIUM = 103;
    public static int RUTHERFORDIUM = 104;
    public static int DUBNIUM = 105;
    public static int SEABORGIUM = 106;
    public static int BOHRIUM = 107;
    public static int HASSIUM = 108;
    public static int MEITNERIUM = 109;
    public static int DARMSTADTIUM = 110;
    public static int ROENTGENIUM = 111;
    public static int COPERNICIUM = 112;
    public static int NIHONIUM = 113;
    public static int FLEROVIUM = 114;
    public static int MOSCOVIUM = 115;
    public static int LIVERMORIUM = 116;
    public static int TENNESSINE = 117;
    public static int OGANESSON = 118;

}