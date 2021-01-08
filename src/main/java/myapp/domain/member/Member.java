package myapp.domain.member;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class Member implements Serializable {

	private static final long serialVersionUID = -4576356301619108593L;

	@Id
	@GeneratedValue
	private Long id;

	private String name;
	private Integer age;

	@Enumerated(EnumType.STRING)
	private Grade grade;

	private Member(String name, Integer age, Grade grade) {
		this.name = name;
		this.age = age;
		this.grade = grade;
	}

	public static Member join(@NonNull String name, @NonNull Integer age) {
		return new Member(name, age, Grade.BRONZE);
	}

}

enum Grade {

	BRONZE

}
