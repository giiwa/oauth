package org.giiwa.oauth.bean;

import java.sql.SQLException;

import org.giiwa.core.bean.Bean;
import org.giiwa.core.bean.Beans;
import org.giiwa.core.bean.Column;
import org.giiwa.core.bean.Helper;
import org.giiwa.core.bean.Table;
import org.giiwa.core.bean.UID;
import org.giiwa.core.bean.Helper.V;
import org.giiwa.core.bean.Helper.W;

@Table(name = "gi_apptoken")
public class AppToken extends Bean {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Column(name = "token")
  String                    token;

  @Column(name = "appid")
  String                    appid;

  @Column(name = "code")
  String                    code;

  @Column(name = "expired")
  long                      expired;

  @Column(name = "uid")
  long                      uid;

  @Column(name = "scope")
  String                    scope;

  public String getToken() {
    return token;
  }

  public String getCode() {
    return code;
  }

  public long getExpired() {
    return expired;
  }

  public long getUid() {
    return uid;
  }

  private AppCode code_obj;

  public AppCode getCode_obj() {
    if (code_obj == null) {
      code_obj = AppCode.load(code);
    }
    return code_obj;
  }

  public static boolean exists(String token) throws SQLException {
    return Helper.exists(W.create("token", token), AppToken.class);
  }

  public static String create(V v) {
    try {
      String id = UID.id(v.toString(), System.currentTimeMillis());
      while (exists(id)) {
        id = UID.id(v.toString(), System.currentTimeMillis(), UID.random());
      }
      Helper.insert(v.set("token", id), AppToken.class);
      return id;
    } catch (Exception e) {
      log.error(v.toString(), e);
    }
    return null;
  }

  public static AppToken load(String token) {
    return Helper.load(W.create("token", token), AppToken.class);
  }

  public static void delete(String token) {
    Helper.delete(W.create("token", token), AppToken.class);
  }

  public static Beans<AppToken> load(W q, int s, int n) {
    return Helper.load(q, s, n, AppToken.class);
  }

}
