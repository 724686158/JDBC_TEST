package normal_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * 事务的基本用法
 *
 */
public class Test_rollback
{
    public static void main(String[] args)
    {
        Connection conn = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;

        try
        {
            //加载驱动类
            conn = DriverManager.getConnection("jdbc:mysql://47.98.124.149:3306/jd100","root","mi");

            conn.setAutoCommit(false); //JDBC中默认是true，我们改成false，然后在后面手动提交

            ps1 = conn.prepareStatement("insert into tmp_test values(?,?,?);");//?是占位符

            ps1.setObject(1, "11");
            ps1.setObject(2, "mzc11");
            ps1.setObject(3, "111111");
            ps1.execute();


            try
            {
                Thread.sleep(3000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            ps2 = conn.prepareStatement("delete from tmp_test where id=11;");//?是占位符
            ps2.execute();


            ps3 = conn.prepareStatement("update test.e set m = 5.424, c = now(3) where id = 1;");//?是占位符
            ps3.execute();

            conn.rollback();//回滚事务
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