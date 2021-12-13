package glexy.model;

import javax.persistence.Entity;

import com.lawencon.base.BaseEntity;

@Entity
public class File extends BaseEntity {

	private static final long serialVersionUID = 5892756716165668671L;

	private byte[] files;
	
	private String extension;

	public byte[] getFiles() {
		return files;
	}

	public void setFiles(byte[] files) {
		this.files = files;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	
}
