package cn.hbu.cs.sqlservertest;

import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBUtil
{
    private static Connection getSQLConnection(String ip, String user, String pwd, String db)
    {
        Connection con = null;
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:jtds:sqlserver://" + ip + ":1433/" + db + ";charset=utf8", user, pwd);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return con;
    }

    public static String QuerySQL()
    {
        String result = "";
        try
        {
            Log.d("David","Connect to SqlServer");
            Connection conn = getSQLConnection("10.187.82.211", "sa", "sql", "student");
            String sql = "select * from Users";
            Statement stmt = conn.createStatement();//
            Log.d("David","exectueQuery");
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                String s1 = rs.getString("UserName");
                String s2 = rs.getString("Password");
                result += "用户名："+s1 + "  密码：" + s2 + "\n";
                Log.d("David",result);
            }
            rs.close();
            stmt.close();
            conn.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            result += "查询数据异常!" + e.getMessage();
        }
        return result;
    }

    public static void main(String[] args)
    {
        QuerySQL();
    }
}