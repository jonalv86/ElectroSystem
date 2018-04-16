package helpers;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Validador {
	static Pattern numeros = Pattern.compile("\\d");
	static Pattern decimales = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?");
	static Pattern letras = Pattern.compile("[a-zA-Z]");
	static Pattern abcArgentino = Pattern.compile("[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ]");

	public static boolean esNumero(char c) {
		String s = "";
		Matcher m = numeros.matcher(s += c);
		return m.find();
	}
	
	public static boolean esDouble(char c) {
		String s = "";
		Matcher m = decimales.matcher(s += c);
		return m.find();
	}

	public static boolean esLetra(char c) {
		String s = "";
		Matcher m = letras.matcher(s += c);
		return m.find();
	}

	public static boolean esLetraDeAbcArgentino(char c) {
		String s = "";
		Matcher m = abcArgentino.matcher(s += c);
		return m.find();
	}

	// Validadores nuevos para el TPFinal de proyprof. //

	public static boolean caracterValido(char c) {
		return esLetra(c) || c == ' ' || c == 'ñ' || c == 'Ñ';
	}

	public static boolean caracterMailValido(char c) {
		return esLetra(c) || esNumero(c) || c == '_' || c == '.' || c == '@' || c == '-';
	}

	public static boolean mailValido(String s) {
		int arrobas = 0;
		int posicionArroba = 0;
		boolean bandera = false;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '@') {
				arrobas++;
				posicionArroba = i;
			}
		}
		if (posicionArroba!=0 &&
			arrobas == 1 &&
			s.charAt(posicionArroba + 1) != '.' &&
			s.charAt(s.length() - 1) != '.') {
			for (int i = posicionArroba; i < s.length(); i++) {
				if (s.charAt(i) == '.')
					if (s.charAt(i-1) != '.')
						bandera = true;
					else
						return false;
			}
		}
		return bandera;
	}

	public static boolean calleValida(char c) {
		return caracterValido(c) || esNumero(c);
	}

	public static String eliminarEspacios(String s) {
		s = s.replaceAll(" +", " ");
		return s.trim();
	}

	public static String primeraLetraMayus(String s) {
		s = s.toLowerCase();
		char[] normalizada = s.toCharArray();
		normalizada[0] = Character.toUpperCase(normalizada[0]);
		if (s.length() > 1) {
			for (int i = 0; i < s.length() - 2; i++) {
				if (normalizada[i] == ' ') {
					normalizada[i + 1] = Character.toUpperCase(normalizada[i + 1]);
				}
			}
		}
		return new String(normalizada);
	}

	public static boolean caracterConEspacio(char c) {
		return esLetraDeAbcArgentino(c) || c == ' ';
	}

	public static boolean caracterConEspacioNumero(char c) {
		return esLetraDeAbcArgentino(c) || c == ' ' || esNumero(c);
	}

	public static boolean caracterTelefonoValido(char c) {
		return esNumero(c) || c == '-' || c == '(' || c == ')' || c == '+';
	}

	public static boolean caracterCodigoValido(char c) {
		return esLetra(c) || esNumero(c) || c == '-';
	}

	public static String validarCampo(String s) {
		String valida = primeraLetraMayus(s);
		return eliminarEspacios(valida);
	}

	public static String validarCampoCodigo(String s) {
		return s.toUpperCase();
	}

	public static String contactenarStrings(String... strings) {
		String stringFinal = "";
		for (String string : strings) {
			stringFinal += " " + (string != null ? string : "");
		}

		return stringFinal;
	}

	public static boolean caracterUserPass(char c) {
		return esNumero(c) || esLetra(c) || c=='-' || c=='.' || c=='_';
	}

	public static String getCadenaAlfanumAleatoria (int longitud) {
		String cadenaAleatoria = "";
		long milis = new java.util.GregorianCalendar().getTimeInMillis();
		Random r = new Random(milis);
		int i = 0;
		while ( i < longitud){
			char c = (char)r.nextInt(255);
			if ( (c >= '0' && c <='9') || (c >='A' && c <='Z') ) {
				cadenaAleatoria += c;
				i ++;
			}
		}
		return cadenaAleatoria;
	}

	public static String armarPrecioValido(String s) {
		char[] sinPuntos = s.toCharArray();
		s = "";
		boolean ceros = false;
		for (char c : sinPuntos) {
			if (c!='.')
				s +=c;
		}
		if (s.length()==0)
			return "0.0"+s;
		if (s.length()==1)
			return "0."+s;
		if (s.length()==2)
			return "0."+s;
		char[] precioSinPunto = s.toCharArray();
		String precioConPunto = "";
		ceros = false;
		int j=0;
		if (precioSinPunto[0]=='0')
			j=1;
		for (int i=j; i<precioSinPunto.length; i++) {
			if (i-precioSinPunto.length+1== 0)
				precioConPunto += ".";
			precioConPunto += precioSinPunto[i];
		}
		return precioConPunto;
	}
}
