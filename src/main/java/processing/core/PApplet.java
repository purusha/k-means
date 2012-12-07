package processing.core;

public class PApplet {
	
	/*
	 * 	La libreria mesh.jar è stata creata per estendere la libreria di processing http://code.google.com/p/processing/
	 * 
	 * 	All'interno di una delle classi della mesh si fà un uso esplicito delle
	 * 	seguenti due funzioni della libreria di processing
	 * 	visto che non voglio dipendere da una libreria di 100Mb
	 *  
	 * 	ho copiato qua le due funzioni di dipendenza ... 
	 */
	
	static public int[] expand(int list[]) {
		return expand(list, list.length << 1);
	}

	static public int[] expand(int list[], int newSize) {
		int temp[] = new int[newSize];
		System.arraycopy(list, 0, temp, 0, Math.min(newSize, list.length));
		return temp;
	}
	
}
