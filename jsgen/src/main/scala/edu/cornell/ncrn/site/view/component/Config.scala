package edu.cornell.ncrn.site.view.component

import edu.cornell.ncrn.site.view.routing.HostConfig
import mhtml._

import scala.xml.Node

object Config {
  def apply(): Component[Unit] = {
    val view: Node = <div>
      <p>
        {HostConfig.currentApiUri.map { curUri =>
        s"Current API URI: ${curUri.toString}"
      }}
      </p>
      {HostConfig.apiUriApp.app._1}
    </div>

    Component.applyLazy(view, Rx(()))
  }



}
