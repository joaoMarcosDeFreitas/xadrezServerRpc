package com.xadrez.models;

public class Partida {
    private String idPartida;
    private String turno;
	private String estadoDasala;
    
	public Partida(String idPartida, String turno, String estadoDaSala) {
		this.idPartida = idPartida;
		this.turno = turno;
		this.estadoDasala = estadoDaSala;
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