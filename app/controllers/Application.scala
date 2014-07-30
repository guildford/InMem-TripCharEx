package controllers

import examples.SparkMLLibUtility
import models.caches.Client
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

object Application extends Controller {

  def index = Action {
    //    Ok(views.html.index("Your new application is ready."))
    Future {
      SparkMLLibUtility.SparkMLLibExample
    }
    Ok(views.html.index(""))
  }

  def count = Action {
    Future {
      Client.PlayMain
    }
    Ok(views.html.index(""))
  }

}