package com.example.microservices.model;

import jakarta.persistence.*;

public class IP {
        private String ip;

        // Getter and setter
        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }
}
