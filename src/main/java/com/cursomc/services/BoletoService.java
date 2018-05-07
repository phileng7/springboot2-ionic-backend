package com.cursomc.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoComBoleto pgto,  Date instanteDoPedido) {
		//Aqui na realidade seria uma chamada de um Webservice que retorna os dados do boleto gerado
		LocalDate localDate = instanteDoPedido.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		localDate.plusWeeks(1);		
		Date dataVencimento = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		pgto.setDataVencimento(dataVencimento);
	}
}
