package com.glos.api.authservice.dto;

public class ChangeRequest {
        private String OLD;
        private String NEW;

        public ChangeRequest() {
        }

        public ChangeRequest(String OLD, String NEW) {
            this.OLD = OLD;
            this.NEW = NEW;
        }

    public String getNEW() {
        return NEW;
    }

    public void setNEW(String NEW) {
        this.NEW = NEW;
    }

    public String getOLD() {
        return OLD;
    }

    public void setOLD(String OLD) {
        this.OLD = OLD;
    }
}