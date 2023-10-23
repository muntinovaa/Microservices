package com.example.microservices.model;

import jakarta.persistence.*;

public class IP {
        private String ip;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }
}
