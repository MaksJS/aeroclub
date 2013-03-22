package models;

import java.util.*;
import play.db.ebean.*;
import play.db.ebean.Model.Finder;
import play.data.format.Formats.*;
import play.data.validation.Constraints.*;
import javax.persistence.*;
import play.i18n.*;
import com.avaje.ebean.*;

@Entity
public class PlaneType extends Model {

	@Id
	public Long id;

	@OneToMany
	public List<Plane> planes;

	@Required
	public String description;

	@Required
	public Double fee1;

	@Required
	public Double fee2;

	@Required
	public double fee3;

	@Required
	public int fee_time1;

	@Required
	public int fee_time2;

	@Required
	public int fee_time3;


	public static Finder<Long, PlaneType> find = new Finder(Long.class, PlaneType.class);

	public static Page<PlaneType> page(int page, int pageSize) {
		return
			find.where()
				.findPagingList(pageSize)
				.getPage(page);
	}

	public String toString() {
		return Messages.get("models.planetype.toString", id, description);
	}

	// Generates a list of options for a html select element
	public static Map<String,String> options() {
		LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
		for (PlaneType planetypes : PlaneType.find.findList()) {
			options.put(planetypes.id.toString(), planetypes.description.toString());
		}
		return options;
	}
}