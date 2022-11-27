package com.company;

public class Visit {

    private String gydytojas;
    private String pacientoVardas;
    private String pacientoPavarde;
    private String data;
    private String laikas;
    private String vizitoIšvados;

    public Visit (String gydytojas, String pacientoVardas, String pacientoPavarde, String data, String laikas, String vizitoIšvados) {
        this.gydytojas = gydytojas;
        this.pacientoVardas = pacientoVardas;
        this.pacientoPavarde = pacientoPavarde;
        this.data = data;
        this.laikas = laikas;
        this.vizitoIšvados = vizitoIšvados;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "gydytojas='" + gydytojas + '\'' +
                ", pacientoVardas='" + pacientoVardas + '\'' +
                ", pacientoPavarde='" + pacientoPavarde + '\'' +
                ", data='" + data + '\'' +
                ", laikas='" + laikas + '\'' +
                ", vizitoIšvados='" + vizitoIšvados + '\'' +
                '}';
    }

    public String getGydytojas() {
        return gydytojas;
    }

    public void setGydytojas(String gydytojas) {
        this.gydytojas = gydytojas;
    }

    public String getPacientoVardas() {
        return pacientoVardas;
    }

    public void setPacientoVardas(String pacientoVardas) {
        this.pacientoVardas = pacientoVardas;
    }

    public String getPacientoPavarde() {
        return pacientoPavarde;
    }

    public void setPacientoPavarde(String pacientoPavarde) {
        this.pacientoPavarde = pacientoPavarde;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLaikas() {
        return laikas;
    }

    public void setLaikas(String laikas) {
        this.laikas = laikas;
    }

    public String getVizitoIšvados() {
        return vizitoIšvados;
    }

    public void setVizitoIšvados(String vizitoIšvados) {
        this.vizitoIšvados = vizitoIšvados;
    }
}
