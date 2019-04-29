package normal_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * 事务的基本用法
 *
 */
public class Test_time
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

            ps1 = conn.prepareStatement("insert into test_datetime set dtcol='1996-01-15 00:00:00';");//?是占位符

            ps1.execute();

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