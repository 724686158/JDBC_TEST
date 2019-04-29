package normal_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * 事务的基本用法
 *
 */
public class Test
{
    public static void main(String[] args)
    {
        Connection conn = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        try
        {
            //加载驱动类
            conn = DriverManager.getConnection("jdbc:mysql://47.98.124.149:3306/jd100","root","mi");

            conn.setAutoCommit(false); //JDBC中默认是true，我们改成false，然后在后面手动提交

            ps1 = conn.prepareStatement("insert into tmp_test values(?,?,?);");//?是占位符

            ps1.setObject(1, "10");
            ps1.setObject(2, "mzc10");
            ps1.setObject(3, "101010");
            ps1.execute();

            try
            {
                Thread.sleep(10000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            ps2 = conn.prepareStatement("delete from tmp_test where id=10;");//?是占位符
            ps2.execute();


            conn.commit();//提交事务
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(ps1!=null)
                {
                    ps1.close();
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try
            {
                if(conn!=null)
                {
                    conn.close();
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
}