package models;

import play.db.ebean.*;
import play.db.ebean.Model.Finder;
import play.data.format.Formats.*;
import play.data.validation.Constraints.*;
import javax.persistence.*;
import play.i18n.*;

@Entity
public class User extends Model {
	
	@Id
	public Long id;

	@Required
	public String login;

	@Required
	public String password;

	public static Finder<Long, User> find = new Finder(Long.class, User.class);

	public String toString() {
		return Messages.get("models.user.toString", id, login);
	}
}