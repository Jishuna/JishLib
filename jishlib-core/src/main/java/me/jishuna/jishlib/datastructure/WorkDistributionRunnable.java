package me.jishuna.jishlib.datastructure;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * A task that distributes actions overs multiple ticks.
 * <p>
 * Big thanks to 7smile7. <br>
 * <a href=
 * "https://www.spigotmc.org/threads/guide-on-workload-distribution-or-how-to-handle-heavy-splittable-tasks.409003/">
 * See his thread here</a>
 */
public class WorkDistributionRunnable<T> implements Runnable {

    protected final Consumer<T> action;
    protected final Predicate<T> escapeCondition;
    protected final List<LinkedList<T>> valueMatrix;
    protected final int distributionSize;
    protected int currentPosition = 0;

    /**
     * Crates a new TypedDistributionTask with the given arguments.
     *
     * @param action           the action to execute
     * @param escapeCondition  the condition for an action to be removed
     * @param distributionSize the number of groups to split actions into
     */
    public WorkDistributionRunnable(Consumer<T> action, Predicate<T> escapeCondition, int distributionSize) {
        this.distributionSize = distributionSize;
        this.action = action;
        this.escapeCondition = escapeCondition;
        this.valueMatrix = new ArrayList<>(distributionSize);

        for (int i = 0; i < distributionSize; i++) {
            this.valueMatrix.add(new LinkedList<>());
        }
    }

    /**
     * Adds a given value to this task.
     *
     * @param value the value to add
     */
    public void addValue(T value) {
        List<T> smallest = this.valueMatrix.get(0);
        if (!smallest.isEmpty()) {
            for (int index = 0; index < this.distributionSize; index++) {
                List<T> next = this.valueMatrix.get(index);
                if (next.size() < smallest.size()) {
                    smallest = next;
                }
            }
        }
        smallest.add(value);
    }

    @Override
    public void run() {
        this.valueMatrix.get(this.currentPosition).removeIf(this::checkThenExecute);
        this.incrementPosition();
    }

    protected boolean checkThenExecute(T value) {
        if (this.escapeCondition.test(value)) {
            return true;
        }

        this.action.accept(value);
        return false;
    }

    protected void incrementPosition() {
        if (++this.currentPosition == this.distributionSize) {
            this.currentPosition = 0;
        }
    }
}
