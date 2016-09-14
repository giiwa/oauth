package org.giiwa.oauth.bean;

import java.sql.SQLException;

import org.giiwa.core.bean.Bean;
import org.giiwa.core.bean.Beans;
import org.giiwa.core.bean.Column;
import org.giiwa.core.bean.Helper;
import org.giiwa.core.bean.Helper.V;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.core.bean.Table;
import org.giiwa.core.bean.UID;

@Table(name = "gi_appcode")
public class AppCode extends Bean {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Column(name = "code")
  String                    code;

  @Column(name = "appid")
  String                    appid;

  @Column(name = "expired")
  long                      expired;

  @Column(name = "scope")
  String                    scope;

  @Column(name = "sid")
  String                    sid;

  @Column(name = "ip")
  String                    ip;

  @Column(name = "uid")
  long                      uid;

  public String getCode() {
    return code;
  }

  public long getExpired() {
    return expired;
  }

  public String getScope() {
    return scope;
  }

  public String getSid() {
    return sid;
  }

  public String getIp() {
    return ip;
  }

  public long getUid() {
    return uid;
  }

  public static AppCode load(String code) {
    return Helper.load(W.create("code", code), AppCode.class);
  }

  public static boolean exists(String code) throws SQLException {
    return Helper.exists(W.create("code", code), AppCode.class);
  }

  public static String create(V v) {
    try {
      String code = UID.id(v.toString(), System.currentTimeMillis());
      while (exists(code)) {
        code = UID.id(v.toString(), System.currentTimeMillis(), UID.random());
      }
      Helper.insert(v.set("code", code), AppCode.class);
      return code;
    } catch (Exception e) {
      log.error(v.toString(), e);
    }
    return null;
  }

  public static Beans<AppCode> load(W q, int s, int n) {
    return Helper.load(q, s, n, AppCode.class);
  }

  public static void delete(String code) {
    Helper.delete(W.create("code", code), AppCode.class);
  }
}
