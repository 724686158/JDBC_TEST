package heavy_load_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class RunnableDemo implements Runnable {
    private Thread t;
    private String threadName;

    RunnableDemo(String name) {
        threadName = name;
        System.out.println("Creating thread:" +  threadName );
    }

    public void run() {
        System.out.println("[RUN]Running " +  threadName );
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://47.98.124.149:3306/heavy_load_test","root","mi");
            PreparedStatement ps1 = null;
            for(int i = 0; i < 5000; i++){
                conn.setAutoCommit(false); //JDBC中默认是true，我们改成false，然后在后面手动提交
                ps1 = conn.prepareStatement("insert into tb1 (thread_name, run_turns, batch_id) values(?, ?, ?);");
                for(int j = 0; j < 5; j++){
                    ps1.setObject(1, threadName);
                    ps1.setObject(2, (i + 1));
                    ps1.setObject(3, (j + 1));
                    ps1.addBatch();
                }

                ps1.executeBatch();
                conn.commit();//提交事务
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("[END]Thread " +  threadName + " exiting.");
    }

    public void start () {
        System.out.println("[STARTR]Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
}