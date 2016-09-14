package org.giiwa.oauth.web.admin;

import org.giiwa.core.bean.Beans;
import org.giiwa.core.bean.Helper;
import org.giiwa.core.bean.X;
import org.giiwa.core.json.JSON;
import org.giiwa.core.bean.Helper.V;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.oauth.bean.AppCode;
import org.giiwa.oauth.bean.AppToken;
import org.giiwa.oauth.bean.Appid;
import org.giiwa.framework.web.Model;
import org.giiwa.framework.web.Path;

public class appid extends Model {

  @Path(path = "token/delete", login = true, access = "access.config.admin")
  public void token_delete() {
    String token = this.getString("token");
    AppToken.delete(token);
    JSON jo = new JSON();
    jo.put(X.STATE, 200);
    this.response(jo);
  }

  @Path(path = "token", login = true, access = "access.config.admin")
  public void token() {
    int s = this.getInt("s");
    int n = this.getInt("n", 20, "number.per.page");

    W q = W.create();
    String appid = this.getString("appid");
    if (!X.isEmpty(appid)) {
      q.and("appid", appid);
      this.set("appid", appid);
    }
    String code = this.getString("code");
    if (!X.isEmpty(code)) {
      q.and("code", code);
      this.set("code", code);
    }

    Beans<AppToken> bs = AppToken.load(q, s, n);
    bs.setTotal((int) Helper.count(q, AppToken.class));
    this.set(bs, s, n);

    this.show("/admin/apptoken.index.html");
  }

  @Path(path = "code/delete", login = true, access = "access.config.admin")
  public void code_delete() {
    String code = this.getString("code");
    AppCode.delete(code);
    JSON jo = new JSON();
    jo.put(X.STATE, 200);
    this.response(jo);
  }

  @Path(path = "code", login = true, access = "access.config.admin")
  public void code() {
    int s = this.getInt("s");
    int n = this.getInt("n", 20, "number.per.page");

    W q = W.create();
    String appid = this.getString("appid");
    if (!X.isEmpty(appid)) {
      q.and("appid", appid);
      this.set("appid", appid);
    }

    Beans<AppCode> bs = AppCode.load(q, s, n);
    bs.setTotal((int) Helper.count(q, AppCode.class));
    this.set(bs, s, n);

    this.show("/admin/appcode.index.html");
  }

  @Path(login = true, access = "access.config.admin")
  public void onGet() {
    int s = this.getInt("s");
    int n = this.getInt("n", 20, "number.per.page");

    W q = W.create();

    Beans<Appid> bs = Appid.load(q, s, n);
    bs.setTotal((int) Helper.count(q, Appid.class));
    this.set(bs, s, n);

    this.show("/admin/appid.index.html");
  }

  @Path(path = "delete", login = true, access = "access.config.admin")
  public void delete() {
    String id = this.getString("appid");
    Appid.delete(id);
    JSON jo = new JSON();
    jo.put(X.STATE, 200);
    this.response(jo);
  }

  @Path(path = "create", login = true, access = "access.config.admin")
  public void create() {
    if (method.isPost()) {
      JSON jo = this.getJSON();
      V v = V.create().copy(jo, "name", "callback");
      String appid = Appid.create(v);

      this.set(X.MESSAGE, lang.get("create.success"));
      onGet();
      return;
    }

    this.show("/admin/appid.create.html");
  }

}
