package com.themysterys.mcciutils.util.config;

public class ConfigInstance {

    public boolean enableDiscordStatus;
    public RPCustomDetails customDetails;

    public ConfigInstance() {
        enableDiscordStatus = true;
        customDetails = RPCustomDetails.IP;
    }

    public enum RPCustomDetails {

        IP("ServerIP"),
        USERNAME("Username"),
        MODVERSION("Mod Version");
        private final String option;

        RPCustomDetails(String option) {
            this.option = option;
        }

        public String getOption() {
            return option;
        }
    }

}