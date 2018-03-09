package com.blue.projbyz.modules;

import com.blue.projbyz.util.NBTUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

import javax.annotation.Nullable;

public class PowerModule extends EnergyStorage implements Module {

    private static final String NAME = "[POWER_MODULE]";
    private static final String NBT_CAPACITY = "capacity";
    private static final String NBT_ENERGY = "energy";
    private static final String NBT_MAX_IN = "max_in";
    private static final String NBT_MAX_OUT = "max_out";

    public PowerModule(int capacity) {
        super(capacity);
    }

    public PowerModule(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public PowerModule(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public PowerModule(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    @Override
    public int hashCode() {
        int result = energy;
        result = 31 * result + maxReceive;
        result = 31 * result + maxExtract;
        result = 31 * result + capacity;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PowerModule)) return false;

        PowerModule that = (PowerModule) o;

        if (energy != that.energy) return false;
        if (maxReceive != that.maxReceive) return false;
        if (maxExtract != that.maxExtract) return false;
        return capacity == that.capacity;
    }

    @Override
    public String toString() {
        return "PowerModule{" +
                "energy=" + energy +
                ", maxReceive=" + maxReceive +
                ", maxExtract=" + maxExtract +
                ", capacity=" + capacity +
                '}';
    }

    public boolean addEnergy(int amount) {
        if (energy + amount > capacity) return false;
        energy += amount;
        return true;
    }

    public boolean drainEnergy(int amount) {
        if (energy < amount) return false;
        energy -= amount;
        return true;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger(NBT_ENERGY, energy);
        nbt.setInteger(NBT_MAX_IN, maxReceive);
        nbt.setInteger(NBT_MAX_OUT, maxExtract);
        nbt.setInteger(NBT_CAPACITY, capacity);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        energy = NBTUtils.getInt(nbt, NBT_ENERGY);
        maxReceive = NBTUtils.getInt(nbt, NBT_MAX_IN);
        maxExtract = NBTUtils.getInt(nbt, NBT_MAX_OUT);
        capacity = NBTUtils.getInt(nbt, NBT_CAPACITY);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return CapabilityEnergy.ENERGY.equals(capability);
    }


    @Override
    @Nullable
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return hasCapability(capability, facing) ? CapabilityEnergy.ENERGY.cast(this) : null;
    }

    @Override
    public String getName() {
        return NAME;
    }


}
