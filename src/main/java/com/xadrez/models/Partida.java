package com.xadrez.models;

public class Partida {
    private String idPartida;
    private String turno;
    
	public Partida(String idPartida, String turno) {
		this.idPartida = idPartida;
		this.turno = turno;
	}

	public String getIdPartida() {
		return idPartida;
	}

	public void setIdPartida(String idPartida) {
		this.idPartida = idPartida;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}
}
