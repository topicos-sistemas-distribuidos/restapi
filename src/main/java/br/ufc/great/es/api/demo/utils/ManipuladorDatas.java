package br.ufc.great.es.api.demo.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ManipuladorDatas {	
	/**
	 * Retorna a data e tempo corrente
	 * @param format formato da data exemplo "yyyy/MM/dd HH:mm:ss"
	 * @return String contento da data corrente exemplo 2016/11/16 12:08:43
	 */
	public static String getCurrentDataTime(String padrao) {
		DateFormat dateFormat = new SimpleDateFormat(padrao);
		Date date = new Date();
		
		return (dateFormat.format(date)); 
	}
	
	public static Date getCurrentDate() {
		Date date = new Date();
		return date;
	}
	
}

