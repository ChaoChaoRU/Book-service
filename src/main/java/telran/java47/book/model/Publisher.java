package telran.java47.book.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@EqualsAndHashCode(of = "publisherName")
public class Publisher implements Serializable {

	private static final long serialVersionUID = 6409009882180918905L;
	
	@Id
	String publisherName;

	@Override
	public String toString() {
		return publisherName;
	}
	
	
}
