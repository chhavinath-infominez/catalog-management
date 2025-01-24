package com.infominez.catalog.master.utils;

public class EnumUtils {

    public enum TenantStatus {
        ACTIVE("ACTIVE"),
        INACTIVE("INACTIVE");

        public final String value;

        TenantStatus(String value) {
            this.value = value;
        }
    }

    public enum AdminStatus {
        ACTIVE("ACTIVE"),
        INACTIVE("INACTIVE");

        public final String value;

        AdminStatus(String value) {
            this.value = value;
        }
    }

    public enum Role {
        SUPER_ADMIN("SUPER_ADMIN"),
        ADMIN("ADMIN");

        public final String value;

        Role(String value) {
            this.value = value;
        }
    }
}
