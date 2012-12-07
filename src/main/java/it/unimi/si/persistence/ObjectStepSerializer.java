package it.unimi.si.persistence;

import it.unimi.si.util.ProgramConstant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class ObjectStepSerializer implements ProgramConstant {
	private File file = new File(STORAGE);

	public ObjectStepSerializer(String filename) {
		if (filename == null || filename.length() < 0) {
			throw new RuntimeException("filename arguments must be not null !!?");
		}
		
		this.file = new File(STORAGE + filename);
	}

	public void write(Step td) throws Exception {
		final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
		
		oos.writeObject(td);
		oos.close();		
	}
	
	public Step read() throws Exception {
        final ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));        
        final Step td = (Step) ois.readObject();
        
        ois.close();
        
        return td;
	}
}
