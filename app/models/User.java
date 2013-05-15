package models;

import play.db.ebean.*;
import play.db.ebean.Model.Finder;
import play.data.format.Formats.*;
import play.data.validation.Constraints.*;
import javax.persistence.*;
import play.i18n.*;
import play.libs.Crypto;

@Entity
public class User extends Model {
	
	@Id
	public Long id;

	@Column(unique=true)
	public String username;

	@Required
	@MinLength(value=5)
	public String password;

	@Required
	@Email
	public String email;

	public Account account;

	public User(Long id, String username, String password, String email) {
		this.id       = id;
		this.username = username;
		this.password = password;
		this.email    = email;
	}

	public static Finder<Long, User> find = new Finder(Long.class, User.class);

    public static User authenticate(String username, String password) {
    	//play.Logger.info(Crypto.sign(password));
        return 
        	find.where()
            	.eq("username", username)
            	.eq("password", Crypto.sign(password))
            	.findUnique();
    }
    
    public static void save(User user) {
    	user.password = Crypto.sign(user.password);
    	user.save();
    	Account.create(user);
    }

    public static void update(Long id, User user) {
    	user.password = Crypto.sign(user.password);
    	user.update(id);
    }

    public Account getAccount() {
    	if (account == null) {
    		account = Account.getByUser(this);
    	}
    	return account;
    }

	public String toString() {
		return Messages.get("models.user.toString", id, username);
	}
}