package controllers

import play.api._
import play.api.mvc._
import utils.SparkMLLibUtility
import scala.concurrent.ExecutionContext.Implicits._

import scala.concurrent.Future

object Application extends Controller {

  def index = Action {
//    Ok(views.html.index("Your new application is ready."))
    Future{SparkMLLibUtility.SparkMLLibExample}
    Ok(views.html.index(""))
  }

}