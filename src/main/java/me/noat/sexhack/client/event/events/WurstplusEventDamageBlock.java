package me.noat.sexhack.client.event.events;

import me.noat.sexhack.client.event.WurstplusEventCancellable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class WurstplusEventDamageBlock extends WurstplusEventCancellable {

    private final BlockPos BlockPos;
    private EnumFacing Direction;

    public WurstplusEventDamageBlock(BlockPos posBlock, EnumFacing directionFacing)
    {
        BlockPos = posBlock;
        setDirection(directionFacing);
    }

    public BlockPos getPos()
    {
        return BlockPos;
    }

    /**
     * @return the direction
     */
    public EnumFacing getDirection()
    {
        return Direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(EnumFacing direction)
    {
        Direction = direction;
    }
    
}