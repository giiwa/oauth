package org.giiwa.oauth.web;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.giiwa.core.bean.Helper;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.core.bean.X;
import org.giiwa.core.task.Task;
import org.giiwa.framework.web.IListener;
import org.giiwa.framework.web.Module;
import org.giiwa.oauth.bean.AppCode;
import org.giiwa.oauth.bean.AppToken;

public class OauthListener implements IListener {

  static Log log = LogFactory.getLog(OauthListener.class);

  @Override
  public void onStart(Configuration conf, Module m) {
    // TODO Auto-generated method stub
    log.info("oauth is starting ...");

    new CleanupTask().schedule(X.AMINUTE);
  }

  @Override
  public void onStop() {
    // TODO Auto-generated method stub

  }

  @Override
  public void uninstall(Configuration conf, Module m) {
    // TODO Auto-generated method stub

  }

  @Override
  public void upgrade(Configuration conf, Module m) {
    // TODO Auto-generated method stub

  }

  private static class CleanupTask extends Task {

    @Override
    public void onExecute() {
      Helper.delete(W.create().and("expired", System.currentTimeMillis(), W.OP_LT), AppCode.class);
      Helper.delete(W.create().and("expired", System.currentTimeMillis(), W.OP_LT), AppToken.class);
    }

    @Override
    public void onFinish() {
      this.schedule(X.AHOUR);
    }

  }
}
