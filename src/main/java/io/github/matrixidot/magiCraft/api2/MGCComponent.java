package io.github.matrixidot.magiCraft.api2;

/**
 * Defines an "action" that can be run in a spell.
 * Defines its own input and output types.
 * This was the best I could come up with lol.
 */
public abstract class MGCComponent {
    private final Class<?>[] inputTypes;
    private final Class<?>[] outputTypes;

    public MGCComponent(Class<?>[] inputTypes, Class<?>[] outputTypes) {
        this.inputTypes = inputTypes;
        this.outputTypes = outputTypes;
    }

    /**
     * Returns if this component can be run based on what inputs it will get.
     * @param inputs The inputs to check agains the predefined types in-order of this component.
     * @return True if the number, types and order of the inputs match, false otherwise.
     */
    public boolean validateInputs(Object... inputs) {
        if (inputTypes.length != inputs.length) return false;
        for (int i = 0; i < inputTypes.length; i++) {
            if (inputs[i] == null) return false;
            if (!inputTypes[i].isInstance(inputs[i])) return false;
        }
        return true;
    }

    /**
     * Performs an action given inputs.
     * This method should return null if validateInputs returns false.
     * @param inputs the predefined input types in-order of this component.
     * @return the predefined output types in-order of this component.
     */
    public abstract Object[] run(Object[] inputs);

    public Class<?>[] getOutputTypes() {
        return outputTypes;
    }
}
