package com.kfxlabs.smartsociety.module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

    public class FuelLogPost {
        @SerializedName("fuel_log")
        @Expose
        private List<FuelLogDetails> fuelLog;

        public void setFuelLog(List<FuelLogDetails> fuelLog) {
            this.fuelLog = fuelLog;
        }

        public List<FuelLogDetails> getFuelLog() {
            return fuelLog;
        }
    }

