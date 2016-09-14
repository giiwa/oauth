package org.giiwa.oauth.bean;

import org.giiwa.core.bean.Bean;
import org.giiwa.core.bean.Beans;
import org.giiwa.core.bean.Table;
import org.giiwa.core.bean.Helper;
import org.giiwa.core.bean.Helper.V;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.core.bean.Column;
import org.giiwa.core.bean.UID;
import org.giiwa.core.bean.X;

/**
 * Demo bean
 * 
 * @author joe
 * 
 */
@Table(name = "gi_appid")
public class Appid extends Bean {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Column(name = "appid")
  String                    appid;

  @Column(name = "secret")
  String                    secret;

  @Column(name = "uid")
  long                      uid;

  @Column(name = "callback")
  String                    callback;

  public String getCallback() {
    return callback;
  }

  public String getAppid() {
    return appid;
  }

  public String getSecret() {
    return secret;
  }

  public long getUid() {
    return uid;
  }

  // ------------

  public static String create(V v) {
    /**
     * generate a unique id in distribute system
     */
    String id = UID.random(10);
    try {
      while (exists(id)) {
        id = UID.random(10);
      }
      Helper.insert(v.set(X.ID, id).set("appid", id).set("secret", UID.random(20)), Appid.class);
      return id;
    } catch (Exception e1) {
      log.error(e1.getMessage(), e1);
    }
    return null;
  }

  public static boolean exists(String id) {
    try {
      return Helper.exists(id, Appid.class);
    } catch (Exception e1) {
      log.error(e1.getMessage(), e1);
    }
    return false;
  }

  public static int update(String id, V v) {
    return Helper.update(id, v, Appid.class);
  }

  public static Beans<Appid> load(W q, int s, int n) {
    return Helper.load(q.sort("appid", 1), s, n, Appid.class);
  }

  public static Appid load(String appid) {
    return Helper.load(W.create("appid", appid), Appid.class);
  }

  public static void delete(String appid) {
    Helper.delete(W.create("appid", appid), Appid.class);
  }

}
