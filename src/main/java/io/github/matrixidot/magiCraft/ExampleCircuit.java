package io.github.matrixidot.magiCraft;

import io.github.matrixidot.magiCraft.api.SpellNode;
import io.github.matrixidot.magiCraft.api.actions.ExplosionNode;
import io.github.matrixidot.magiCraft.api.actions.LightningStrikeNode;
import io.github.matrixidot.magiCraft.api.actions.TeleportNode;
import io.github.matrixidot.magiCraft.api.branches.LaunchProjectileNode;
import io.github.matrixidot.magiCraft.api.control.ForNode;
import io.github.matrixidot.magiCraft.api.events.DamageTakenFromEntityBranch;
import io.github.matrixidot.magiCraft.api.events.PlayerInteractBranch;
import io.github.matrixidot.magiCraft.api.events.ProjectileHitBranch;

public class ExampleCircuit {
    public static SpellNode createExampleCircuit() {
        // Build the following circuit:
        // Initial cast: LaunchProjectileNode
        //   - Branch A (on ProjectileHitEvent): ExplosionNode then LightningStrikeNode.
        //   - Branch B (on PlayerInteractEvent): Nested cast launching a projectile whose hit triggers TeleportNode.
        return new LaunchProjectileNode() {{
            addBranch(new ProjectileHitBranch(new ExplosionNode(false).setNext(new LightningStrikeNode(false))));
            addBranch(new PlayerInteractBranch(new LaunchProjectileNode() {{
                addBranch(new ProjectileHitBranch(new TeleportNode().setNext(new LightningStrikeNode(false))));
            }}));
        }};
    }

    public static SpellNode createSecondExampleCircuit() {
        return new ForNode(200) {{
            addBranch(new DamageTakenFromEntityBranch(new LightningStrikeNode(false)));
        }};
    }
}
