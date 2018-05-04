package com.cursomc.resources.exception;

import java.io.Serializable;

public class StandardError implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer status;		//status HTTP do erro
	private String msg;			//mensagem de erro
	private Long timeStamp;		//momento do erro
	private String excecao;	
	
	public StandardError(Integer status, String msg, Long timeStamp, String excecao) {
		this.status = status;
		this.msg = msg;
		this.timeStamp = timeStamp;
		this.excecao = excecao;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getExcecao() {
		return excecao;
	}

	public void setExcecao(String excecao) {
		this.excecao = excecao;
	}	
}
