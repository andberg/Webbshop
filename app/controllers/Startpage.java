package controllers;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.startpage;

public class Startpage extends Controller {

	@Transactional
	public static Result index(){
		return ok(startpage.render()); 
	}
}
