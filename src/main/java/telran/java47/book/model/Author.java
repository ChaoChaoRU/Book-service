package telran.java47.book.model;

import java.io.Serializable;
import java.time.LocalDate;

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
@EqualsAndHashCode(of = "name")
public class Author implements Serializable {

	private static final long serialVersionUID = -1958354963944315511L;

	@Id
	String name;
	
	LocalDate birthDate;
}
