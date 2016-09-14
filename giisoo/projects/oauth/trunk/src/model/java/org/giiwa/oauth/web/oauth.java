package org.giiwa.oauth.web;

import org.giiwa.core.bean.Helper.V;
import org.giiwa.core.bean.X;
import org.giiwa.core.json.JSON;
import org.giiwa.framework.bean.User;
import org.giiwa.framework.web.Model;
import org.giiwa.framework.web.Path;
import org.giiwa.oauth.bean.AppCode;
import org.giiwa.oauth.bean.AppToken;
import org.giiwa.oauth.bean.Appid;

/**
 * web api: /oauth
 * 
 * @author joe
 * 
 */
public class oauth extends Model {

  @Path()
  public void onGet() {
    String appid = this.getString("appid");
    String callback = this.getString("callback");
    Appid a = Appid.load(appid);
    if (a != null) {
      String cb = a.getCallback();
      if (X.isEmpty(cb) || callback.contains(cb)) {
        User u = this.getUser();
        if (u == null) {

          this.gotoLogin();
        } else {
          String code = AppCode
              .create(V.create("uid", u.getId()).set("appid", appid).set("sid", sid()).set("ip", this.getRemoteHost())
                  .set("scope", this.getString("scope")).set("expired", System.currentTimeMillis() + 7 * X.ADAY));
          this.redirect(callback + "?code=" + code);
        }
      } else {
        this.println("bad callback, callback=" + callback + ", required=" + cb);
      }
    } else {
      this.println("bad appid, appid=" + appid);
    }
  }

  @Path(path = "userinfo")
  public void userinfo() {
    String appid = this.getString("appid");
    String secret = this.getString("secret");

    JSON jo = JSON.create();
    Appid a = Appid.load(appid);
    if (a == null) {
      jo.put(X.STATE, 201);
      jo.put(X.MESSAGE, "bad appid");
    } else if (!X.isSame(secret, a.getSecret())) {
      jo.put(X.STATE, 201);
      jo.put(X.MESSAGE, "bad secret");
    } else {

      String token = this.getString("token");
      AppToken t = AppToken.load(token);

      if (t != null && t.getExpired() > System.currentTimeMillis()) {
        long uid = t.getUid();
        User u = User.loadById(uid);
        jo.put("userinfo", u);
        jo.put(X.STATE, 200);
      } else {
        jo.put(X.STATE, 201);
        jo.put(X.MESSAGE, "bad token, token=" + token);
      }
    }

    this.response(jo);
  }

  /**
   * using code to get token
   */
  @Path(path = "token")
  public void token() {
    JSON jo = JSON.create();
    String appid = this.getString("appid");
    String secret = this.getString("secret");

    Appid a = Appid.load(appid);
    if (a == null) {
      jo.put(X.STATE, 201);
      jo.put(X.MESSAGE, "bad appid");
    } else if (!X.isSame(secret, a.getSecret())) {
      jo.put(X.STATE, 201);
      jo.put(X.MESSAGE, "bad secret");
    } else {

      String code = this.getString("code");

      AppCode c = AppCode.load(code);
      if (c != null && c.getExpired() > System.currentTimeMillis()) {
        long expired = System.currentTimeMillis() + X.ADAY * 7;
        String t = AppToken.create(V.create("code", code).set("appid", appid).set("expired", expired)
            .set("uid", c.getUid()).set("scope", c.getScope()));
        jo.put("token", t);
        jo.put("expired", expired);
        jo.put("uid", c.getUid());
        jo.put(X.STATE, 200);
      } else {
        jo.put(X.STATE, 201);
        jo.put(X.MESSAGE, "bad code, code=" + code);
      }
    }
    this.response(jo);
  }

}
