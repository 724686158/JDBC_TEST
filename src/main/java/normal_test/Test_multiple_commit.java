package normal_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * 事务的基本用法
 *
 */
public class Test_multiple_commit
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

            ps1.setObject(1, "9");
            ps1.setObject(2, "mzc9");
            ps1.setObject(3, "999");
            ps1.execute();
            conn.commit();

            ps2 = conn.prepareStatement("delete from tmp_test where id=9;");//?是占位符
            ps2.execute();
            conn.commit();

            ps2 = conn.prepareStatement("insert into test.e set m = 4.2341, c = now(3), comment = 'I am a creature of light.';");//?是占位符
            ps2.execute();
            conn.commit();

            ps2 = conn.prepareStatement("delete from test.e where m = 4.2341;");//?是占位符
            ps2.execute();
            conn.commit();

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