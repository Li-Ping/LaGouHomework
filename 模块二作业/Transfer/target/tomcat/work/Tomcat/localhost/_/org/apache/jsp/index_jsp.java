/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2020-01-04 04:00:19 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!doctype html>\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("    <title>è½¬è´¦æ±æ¬¾</title>\n");
      out.write("\n");
      out.write("    <script type=\"text/javascript\" src=\"js/jquery-3.4.1.min.js\"></script>\n");
      out.write("\n");
      out.write("\n");
      out.write("    <style type=\"text/css\">\n");
      out.write("        body {\n");
      out.write("            background-color:#00b38a;\n");
      out.write("            text-align:center;\n");
      out.write("        }\n");
      out.write("\n");
      out.write("        .lp-login {\n");
      out.write("            position:absolute;\n");
      out.write("            width:500px;\n");
      out.write("            height:300px;\n");
      out.write("            top:50%;\n");
      out.write("            left:50%;\n");
      out.write("            margin-top:-250px;\n");
      out.write("            margin-left:-250px;\n");
      out.write("            background: #fff;\n");
      out.write("            border-radius: 4px;\n");
      out.write("            box-shadow: 0 0 10px #12a591;\n");
      out.write("            padding: 57px 50px 35px;\n");
      out.write("            box-sizing: border-box\n");
      out.write("        }\n");
      out.write("\n");
      out.write("\n");
      out.write("        .lp-login .submitBtn {\n");
      out.write("            display:block;\n");
      out.write("            text-decoration:none;\n");
      out.write("            height: 48px;\n");
      out.write("            width: 150px;\n");
      out.write("            line-height: 48px;\n");
      out.write("            font-size: 16px;\n");
      out.write("            color: #fff;\n");
      out.write("            text-align: center;\n");
      out.write("            background-image: -webkit-gradient(linear, left top, right top, from(#09cb9d), to(#02b389));\n");
      out.write("            background-image: linear-gradient(90deg, #09cb9d, #02b389);\n");
      out.write("            border-radius: 3px\n");
      out.write("        }\n");
      out.write("\n");
      out.write("\n");
      out.write("        input[type='text'] {\n");
      out.write("            height:30px;\n");
      out.write("            width:250px;\n");
      out.write("        }\n");
      out.write("\n");
      out.write("        span {\n");
      out.write("            font-style: normal;\n");
      out.write("            font-variant-ligatures: normal;\n");
      out.write("            font-variant-caps: normal;\n");
      out.write("            font-variant-numeric: normal;\n");
      out.write("            font-variant-east-asian: normal;\n");
      out.write("            font-weight: normal;\n");
      out.write("            font-stretch: normal;\n");
      out.write("            font-size: 14px;\n");
      out.write("            line-height: 22px;\n");
      out.write("            font-family: \"Hiragino Sans GB\", \"Microsoft Yahei\", SimSun, Arial, \"Helvetica Neue\", Helvetica;\n");
      out.write("        }\n");
      out.write("\n");
      out.write("    </style>\n");
      out.write("    <script type=\"text/javascript\">\n");
      out.write("        $(function(){\n");
      out.write("            $(\".submitBtn\").bind(\"click\",function(){\n");
      out.write("                var fromAccount = $(\"#fromAccount\").val();\n");
      out.write("                var toAccount = $(\"#toAccount\").val();\n");
      out.write("                var money = $(\"#money\").val();\n");
      out.write("\n");
      out.write("                if(money == null || $.trim(money).length == 0){\n");
      out.write("                    alert(\"sorry,å¿é¡»è¾å¥è½¬è´¦éé¢~\");\n");
      out.write("                    return;\n");
      out.write("                }\n");
      out.write("\n");
      out.write("                $.ajax({\n");
      out.write("                    url:'/transferServlet',\n");
      out.write("                    type:'POST',    //GET\n");
      out.write("                    async:false,    //æfalse,æ¯å¦å¼æ­¥\n");
      out.write("                    data:{\n");
      out.write("                        fromCardNo:fromAccount.split(' ')[1],\n");
      out.write("                        toCardNo:toAccount.split(' ')[1],\n");
      out.write("                        money:money\n");
      out.write("                    },\n");
      out.write("                    timeout:5000,    //è¶æ¶æ¶é´\n");
      out.write("                    dataType:'json', //è¿åçæ°æ®æ ¼å¼ï¼json/xml/html/script/jsonp/text\n");
      out.write("                    success:function(data){\n");
      out.write("                        if(\"200\" == data.status){\n");
      out.write("                            alert(\"è½¬è´¦æå~~~\");\n");
      out.write("                        }else{\n");
      out.write("                            alert(\"è½¬è´¦å¤±è´¥~~~,message:\" + data.message);\n");
      out.write("                        }\n");
      out.write("                    }\n");
      out.write("                })\n");
      out.write("            })\n");
      out.write("        })\n");
      out.write("\n");
      out.write("        //æ£æ¥è¾å¥å¼æ¯å¦ä¸ºæ´æ°\n");
      out.write("        function checkFormat(obj){\n");
      out.write("            var reg = /^[0-9]+[0-9]*]*$/;\n");
      out.write("            if($.trim($(obj).val()).length>0){\n");
      out.write("                if(!reg.test($(obj).val())){\n");
      out.write("                    alert(\"è¾å¥æ ¼å¼éè¯¯ï¼è¯·è¾æ´æ°ï¼\");\n");
      out.write("                    $(obj).val(\"\");\n");
      out.write("                }else{\n");
      out.write("                    $(obj).val(parseInt($(obj).val()));\n");
      out.write("                }\n");
      out.write("            }\n");
      out.write("        }\n");
      out.write("    </script>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("\n");
      out.write("\n");
      out.write("<form>\n");
      out.write("    <table class=\"lp-login\">\n");
      out.write("        <tr>\n");
      out.write("            <td align=\"right\"><span>æ¶æ¬¾è´¦æ·</span></td>\n");
      out.write("            <td align=\"center\">\n");
      out.write("                <input type=\"text\" id=\"toAccount\" value=\"æåå 9876543212334567\" disabled></input>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        <tr>\n");
      out.write("            <td align=\"right\"><span>ä»æ¬¾è´¦æ·</span></td>\n");
      out.write("            <td align=\"center\">\n");
      out.write("                <input type=\"text\" id=\"fromAccount\" value=\"ç« æ­¦æ 9765543213444566\" disabled></input>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        <tr>\n");
      out.write("            <td align=\"right\"><span>è½¬è´¦éé¢</span></td>\n");
      out.write("            <td align=\"center\">\n");
      out.write("                <input type=\"text\" id=\"money\" onblur=\"checkFormat(this)\"></input>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        <tr align=\"center\">\n");
      out.write("            <td colspan=\"2\">\n");
      out.write("                <a href=\"javasrcipt:void(0)\" class=\"submitBtn\"><span>è½¬ åº</span></a>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("    </table>\n");
      out.write("</form>\n");
      out.write("\n");
      out.write("</body>\n");
      out.write("</html>\n");
      out.write("\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
