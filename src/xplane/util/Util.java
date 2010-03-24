package xplane.util;

public class Util {
	public static Integer[] toIntArray(String data) {
		String[] s = data.split(",");
		Integer[] aux = new Integer[s.length];

		for (int i = 0; i < aux.length; i++) {
			aux[i] = Integer.parseInt(s[i]);
		}
		return aux;
	}
}
