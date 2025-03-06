package io.github.matrixidot.magiCraft.api2;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class MGCComponent {
    protected final Class<?>[] inputTypes;
    protected final Class<?>[] outputTypes;
    protected final Map<Predicate<Object[]>, MGCComponent> children = new HashMap<>();

    public MGCComponent(Class<?>[] inputTypes, Class<?>[] outputTypes) {
        this.inputTypes = inputTypes;
        this.outputTypes = outputTypes;
    }

    public boolean validateInputs(Object... inputs) {
        if (inputs.length != inputTypes.length) return false;
        for (int i = 0; i < inputs.length; i++) {
            if (inputs[i] == null || !inputTypes[i].isInstance(inputs[i])) return false;
        }
        return true;
    }

    public Class<?>[] getInputTypes() {
        return inputTypes;
    }

    public Class<?>[] getOutputTypes() {
        return outputTypes;
    }

    public void addChild(Predicate<Object[]> condition, MGCComponent child) {
        children.put(condition, child);
    }

    public void next(Object... childInputs) {
        for (Map.Entry<Predicate<Object[]>, MGCComponent> entry : children.entrySet()) {
            if (entry.getKey().test(childInputs)) {
                entry.getValue().run(childInputs);
                return;
            }
        }
    }

    public abstract Object[] run(Object... inputs);
}
